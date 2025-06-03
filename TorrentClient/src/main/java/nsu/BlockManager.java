package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockManager {
    private final Logger logger = LogManager.getLogger(ConnectOperation.class);
    private final int COUNT_BLOCKS = 1000;
    long countPieces;
    int countBlocks;
    int countPiecesInBlock;
    int blockSize;
    int countPartsInPiece;
    private int currentBlockIndex =0;
    Map<Integer, Piece> pieces = new ConcurrentHashMap<>();
    BitSet downloadedPieces;
    BlockManager(long countPieces, long length, long pieceSize,BitSet downloadedPieces,int countPartsInPiece){
        logger.trace("length: "+ length);
        logger.trace("pieceSize" + pieceSize);
        logger.trace("count pieces" + countPieces);
        logger.trace("count blocks" + COUNT_BLOCKS);
        logger.trace("countPiecesInBlock" + (countPieces/COUNT_BLOCKS));
        this.countPieces = countPieces;
        this.countPiecesInBlock =(int)countPieces/COUNT_BLOCKS;
        blockSize = (int)(length/COUNT_BLOCKS);
        logger.trace("blockSize "+blockSize);
        countPiecesInBlock = (int)(blockSize/pieceSize);
        logger.trace("countPiecesInBlock "+countPiecesInBlock);
        this.downloadedPieces = downloadedPieces;
        this.countPartsInPiece = countPartsInPiece;
        logger.trace("countPartsInPiece "+countPartsInPiece);
        setNeedPiecesInBlock(currentBlockIndex);
    }
    public void setNeedPiecesInBlock(int index){
        if(index >= COUNT_BLOCKS){
            logger.debug("reach the limit");
            return;
        }
        if(isCompleteBlock(index)){
            updateBlock();//!!!!!!!!!
        }
        logger.trace("index" + currentBlockIndex);
        for(int i=0;i< countPiecesInBlock;i++){
            if(!downloadedPieces.get(index*countPiecesInBlock +i)){
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
        for(int i=0;i<countPiecesInBlock;i++){
            pieces.get(i).clearPiece();
        }
        pieces.clear();
    }
    public void updateBlock(){
        currentBlockIndex++;
        if(currentBlockIndex>=COUNT_BLOCKS){
            logger.debug("reach the limit");
            return;
        }
        clearBlock();
        setNeedPiecesInBlock(currentBlockIndex);
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
        int index = idx%countPiecesInBlock;
        Piece piece = pieces.get(index);
        return piece.getFreePart();
    }

}
//1910128
//1955971520
//64*5*23
//7360
//265757
