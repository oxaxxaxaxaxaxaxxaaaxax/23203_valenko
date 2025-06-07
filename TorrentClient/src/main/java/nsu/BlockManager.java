package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
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
    BlockManager(long countPieces, long length, long pieceSize,BitSet downloadedPieces,int countPartsInPiece){
        logger.trace("length: "+ length);
        logger.trace("pieceSize" + pieceSize);
        logger.trace("count pieces" + countPieces);
        this.countPieces = countPieces;
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
    public void clearBlock(){
        pieces.clear();
    }
    public void updateBlock(){
        currentBlockIndex++;
        logger.trace("index in update" + currentBlockIndex);
        if(currentBlockIndex>=COUNT_BLOCKS){
            logger.debug("reach the limit");
            return;
        }
        clearBlock();
        setNeedPiecesInBlock();
    }
    public boolean isCompleteBlock(int index){
        if(index != currentBlockIndex){
            logger.info("failed index");
            return false;
        }
        int start = currentBlockIndex*countPiecesInBlock;
        int end = Math.min(start+countPiecesInBlock,(int)countPieces);
        synchronized (downloadedPieces){
            for(int i=start;i<end;i++){
                if(!downloadedPieces.get(i)){
                    return false;
                }
            }
        }
        return true;
    }

    public void removePiece(int idx){
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

    public Piece getPiece(int idx){
        int blockIndex = idx/countPiecesInBlock;
        if(blockIndex != currentBlockIndex){
            logger.trace("index didn't match");
            return null;
        }
        int index = idx%countPiecesInBlock;
        return pieces.get(index);
    }

    public int getNotDownloadedPart( int idx){//idx - глобальный
        int blockIndex = idx/countPiecesInBlock;
        logger.trace("block index "+blockIndex);
        logger.trace("currentBlockIndex" + currentBlockIndex);
        if(blockIndex != currentBlockIndex){
            if((blockIndex == 1+ currentBlockIndex) && (isCompleteBlock(currentBlockIndex))){
                logger.trace("load new block");
                updateBlock();
            }else{
                logger.trace("index didn't match");
                throw new RuntimeException("INDEX DIDNT MATCH");
                //return 0;
            }
        }
        logger.trace("find piece index "+idx);
        if(idx ==0){
            logger.trace("idx in getNotDownloadedPart " +idx);
            return pieces.get(0).getFreePart();
        }
        logger.trace("idx in getNotDownloadedPart " +idx);
        int index = idx%countPiecesInBlock;
        return pieces.get(index).getFreePart();
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

