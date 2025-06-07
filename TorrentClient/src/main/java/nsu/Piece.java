package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class Piece {
    private final int index;
    private final int countParts;
    private final int partSize = 16* 1024;
    private BitSet parts;
    //private byte[] pieceFile;
    private ByteBuffer pieceFile;
    Logger logger = LogManager.getLogger(Piece.class);
    Piece(int countParts, int index){
        this.index = index;
        this.countParts = countParts;
        //pieceFile = new byte[countParts*partSize];
        pieceFile = ByteBuffer.allocateDirect(countParts*partSize);
        parts = new BitSet(countParts);
    }
    public int getFreePart(){
        logger.trace("in get free part");
        synchronized (parts){
            for(int i=0;i< countParts;i++){
                if(!parts.get(i)){
                    logger.trace("find free part "+ i);
                    return i*partSize;
                }
            }
        }
        logger.trace("not free part!!");
        return -1;
    }
//    public void addLoadedPart(int idx, byte[] loadedPart){
//        parts.set(idx);
//        System.arraycopy(loadedPart,0,pieceFile,idx*partSize,partSize);
//    }

    public void addLoadedPart(int idx, byte[] loadedPart) {
        synchronized (parts){
            if (parts.get(idx)) {
                return;
            }
        }
        int offset = idx * partSize;
        synchronized (pieceFile){
            pieceFile.position(offset);
            pieceFile.put(loadedPart);
        }
        synchronized (parts){
            parts.set(idx);
        }
    }

//    public boolean checkIsCompletedPiece(){
//        return !parts.isEmpty();
//    }

    public boolean checkIsCompletedPiece() {
        synchronized (parts){
            return parts.cardinality() == countParts;
        }
    }

    //public byte[] getPieceFile(){return pieceFile;}

    public ByteBuffer getPieceFile() {
        synchronized (pieceFile){
            return pieceFile.duplicate(); // безопасная копия для работы
        }
    }

    public int getSize(){return countParts*partSize;}

//    public void clearPiece(){
//        Arrays.fill(pieceFile,(byte)0);
//    }

    public void clearPiece() {
        synchronized (pieceFile){
            pieceFile.clear(); // position=0, limit=capacity
        }
        synchronized (parts){
            parts.clear();     // сбрасываем все биты
        }
        logger.trace("piece is clear");
    }

}
