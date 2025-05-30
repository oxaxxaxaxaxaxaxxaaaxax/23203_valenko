package nsu;

import java.util.Arrays;
import java.util.BitSet;

public class Piece {
    private final int index;
    private final int countParts;
    private final int partSize = 16* 1024;
    private BitSet parts;
    private byte[] pieceFile;
    Piece(int countParts, int index){
        this.index = index;
        this.countParts = countParts;
        pieceFile = new byte[countParts*partSize];
        parts = new BitSet(countParts);
    }
    public int getFreePart(){
        for(int i=0;i< countParts;i++){
            if(!parts.get(i)){
                return i*partSize;
            }
        }
        return 0;///////
    }
    public void addLoadedPart(int idx, byte[] loadedPart){
        parts.set(idx);
        System.arraycopy(loadedPart,0,pieceFile,idx*partSize,partSize);
    }
    public boolean checkIsCompletedPiece(){
        return !parts.isEmpty();
    }
    public byte[] getPieceFile(){return pieceFile;}
    public void clearPiece(){
        Arrays.fill(pieceFile,(byte)0);
    }

}
