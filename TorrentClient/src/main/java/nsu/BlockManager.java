package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockManager {
    private final Logger logger = LogManager.getLogger(BlockManager.class);
    private final int COUNT_BLOCKS ;
    private final int countPiecesInBlock = 400;
    long countPieces;
    int blockSize;
    int countPartsInPiece;
    private int currentBlockIndex =0;
    Map<Integer, Piece> pieces = new ConcurrentHashMap<>();
    private final BitSet downloadedPieces;
    private final Object blockLock = new Object();
    TorrentPeers torrentPeers;
    BlockManager(long countPieces, long length, long pieceSize,BitSet downloadedPieces,int countPartsInPiece,TorrentPeers peers){
        logger.trace("length: "+ length);
        logger.trace("pieceSize" + pieceSize);
        logger.trace("count pieces" + countPieces);
        this.countPieces = countPieces;
        torrentPeers =peers;
        this.COUNT_BLOCKS =(int)(countPieces +countPiecesInBlock-1) /countPiecesInBlock;
        logger.trace("count blocks" + COUNT_BLOCKS);
        logger.trace("countPiecesInBlock" + countPiecesInBlock);
        blockSize = (int)(length/COUNT_BLOCKS);
        logger.trace("blockSize "+blockSize);
        this.downloadedPieces = downloadedPieces;
        this.countPartsInPiece = countPartsInPiece;
        logger.trace("countPartsInPiece "+countPartsInPiece);
        setNeedPiecesInBlock();
    }
    public void setNeedPiecesInBlock(){
        synchronized (blockLock){
            logger.trace("index" + currentBlockIndex);
            if(currentBlockIndex >= COUNT_BLOCKS){
                logger.debug("reach the limit1");
                return;
            }
            if(isCompleteBlock(currentBlockIndex)){
                logger.trace("Block is completed");
                return;
            }
            int start = currentBlockIndex*countPiecesInBlock;
            int end = Math.min(start+countPiecesInBlock,(int)countPieces);
            for(int i=start;i<end;i++){
//            if(i >= countPieces){
//                return;
//            }
                synchronized (downloadedPieces){
                    if(!downloadedPieces.get(i)){
                        logger.trace("set piece!!!");
                        pieces.put(i-start,new Piece(countPartsInPiece,i-start));
                        logger.trace(i-start);
                    }else{
                        logger.trace("piece is correct");
                    }
                }
            }
        }
    }
    public void clearBlock(){
        synchronized (blockLock){
            pieces.clear();

        }
    }

    public boolean isCompletedPeersBlock(){
        synchronized (blockLock){
            int countPeer = torrentPeers.getCountPeers();
            List<Peer> peers = torrentPeers.getPeers();
            for(int i=0;i<countPeer;i++){
                Peer peer = peers.get(i);
                logger.trace("peer: "+peer.getServerPort());
                BitSet peerPieces = peer.getBitfieldCopy();
                for(int j=0;j<countPieces;j++){
                    System.out.print(peerPieces.get(j));
                }
                int counter = 0;
                int start = currentBlockIndex*countPiecesInBlock;
                int end = Math.min(start+countPiecesInBlock,(int)countPieces);
                for(int j=start;j<end;j++){
                    if(!peerPieces.get(j)){
                        logger.trace("j: "+j);
                        counter++;
                        return false;
                    }
                }
//            logger.trace("counter");
//            if(counter > 20){
//                return false;
//            }
            }
            return true;
        }
    }

    public void updateBlock(){
        synchronized (blockLock){
            if(!isCompletedPeersBlock()){
                logger.trace("block's are not completed");
                return;
            }
            currentBlockIndex++;
            logger.trace("index in update" + currentBlockIndex);
            if(currentBlockIndex>=COUNT_BLOCKS){
                logger.debug("reach the limit");
                return;
            }
            clearBlock();
            setNeedPiecesInBlock();
        }
    }
    public boolean isCompleteBlock(int index){
        synchronized (blockLock){
            if(index != currentBlockIndex){
                logger.info("failed index");
                return false;
            }
            int start = currentBlockIndex*countPiecesInBlock;
            int end = Math.min(start+countPiecesInBlock,(int)countPieces);
            synchronized (downloadedPieces){
                for(int i=start;i<end;i++){
                    if(!downloadedPieces.get(i)){
                        logger.trace("not downloaded part");
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public void removePiece(int idx){
        synchronized (blockLock){
            int blockIndex = idx/countPiecesInBlock;
            int localIndex = idx%countPiecesInBlock;
            if(blockIndex == currentBlockIndex){
                pieces.remove(localIndex);
                logger.trace("piece is removed");
                if(isCompleteBlock(currentBlockIndex)){
                    updateBlock();
                }
                return;
            }else{
                if((blockIndex == 1+ currentBlockIndex) && isCompleteBlock(currentBlockIndex)){
                    logger.trace("load new block");
                    updateBlock();
                    return;
                }else{
                    logger.trace("index didn't match");
                    return;
                }
            }
        }

    }

    public Piece getPiece(int idx){
        synchronized (blockLock){
            int blockIndex = idx/countPiecesInBlock;
            if(blockIndex != currentBlockIndex){
                logger.trace("index didn't match");
                return null;
            }
            int index = idx%countPiecesInBlock;
            return pieces.get(index);
        }
    }

    public int getNotDownloadedPart( int idx){//idx - глобальный
        synchronized (blockLock){
            int blockIndex = idx/countPiecesInBlock;
            logger.trace("block index "+blockIndex);
            logger.trace("currentBlockIndex" + currentBlockIndex);
            if(blockIndex != currentBlockIndex){
                if((blockIndex == 1+ currentBlockIndex) && (isCompleteBlock(currentBlockIndex))){
                    logger.trace("load new block");
                    updateBlock();
                }else{
                    logger.trace("index didn't match");
                    return -1;////////////////////////
                    //return 0;
                }
            }
            if(blockIndex != currentBlockIndex){
                logger.trace("not updating ");
                return -1;
            }
            logger.trace("find piece index "+idx);
            if(idx ==0){
                logger.trace("idx in getNotDownloadedPart 0!" +idx);
                int freePart =pieces.get(0).getFreePart();
                if(freePart== -1){
                    synchronized (downloadedPieces){
                        downloadedPieces.set(0);
                    }
                    return -1;
                }else{
                    return freePart;
                }
            }
            logger.trace("idx in getNotDownloadedPart " +idx);
            int index = idx%countPiecesInBlock;
            logger.trace("index load "+index);
            int freePart =pieces.get(index).getFreePart();
            if(freePart == -1){
                synchronized (downloadedPieces){
                    downloadedPieces.set(idx);
                }
                return -1;
            }else{
                return freePart;
            }
        }
    }

    public boolean isRequestInBlock(int idx){
        int blockIndex = idx/countPiecesInBlock;
        logger.trace("block index "+blockIndex);
        logger.trace("currentBlockIndex" + currentBlockIndex);
        if(blockIndex != currentBlockIndex) {
            if ((blockIndex == 1 + currentBlockIndex) && (isCompleteBlock(currentBlockIndex))) {
                logger.trace("load new block");
                updateBlock();
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}

