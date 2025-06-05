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
    int countBlocks;
    int blockSize;
    int countPartsInPiece;
    private int currentBlockIndex =0;
    Map<Integer, Piece> pieces = new ConcurrentHashMap<>();
    BitSet downloadedPieces;
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
        //countPiecesInBlock = (int)(blockSize/pieceSize);
        //logger.trace("countPiecesInBlock "+countPiecesInBlock);
        this.downloadedPieces = downloadedPieces;
        this.countPartsInPiece = countPartsInPiece;
        logger.trace("countPartsInPiece "+countPartsInPiece);
        setNeedPiecesInBlock();
    }
    public void setNeedPiecesInBlock(){
        logger.trace("index" + currentBlockIndex);
        if(isCompleteBlock(currentBlockIndex)){
            logger.trace("Block is completed");
            updateBlock();
        }
        if(currentBlockIndex >= COUNT_BLOCKS){
            logger.debug("reach the limit1");
            return;
        }
        for(int i=0;i< countPiecesInBlock;i++){
            if(!downloadedPieces.get(currentBlockIndex*countPiecesInBlock +i)){
                logger.trace("set piece!!!");
                pieces.put(i,new Piece(countPartsInPiece,i));
            }
            if(pieces.isEmpty()){
                logger.trace("empty map");
                updateBlock();
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
        for(int i=0;i<countPiecesInBlock;i++){
            if(!downloadedPieces.get(i)){
                return false;
            }
        }
        return true;
    }

    public void removePiece(int idx){
        int blockIndex = idx/countPiecesInBlock;
        if(blockIndex != currentBlockIndex){
            logger.trace("index didn't match");
            return;
        }
        int index = idx%countPiecesInBlock;
        pieces.remove(index);
        logger.trace("piece is removed");
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
        if(blockIndex != currentBlockIndex){
            logger.trace("index didn't match");
            return 0;
        }
        logger.trace("find piece index "+idx);
        if(idx ==0){
            return pieces.get(0).getFreePart();
        }
        int index = idx%countPiecesInBlock;
        return pieces.get(index).getFreePart();
    }

    public boolean isEmpty(){
        return pieces.isEmpty();
    }

}
//1910128
//1955971520
//64*5*23
//7360
//265757
