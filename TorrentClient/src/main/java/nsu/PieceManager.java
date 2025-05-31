package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PieceManager {
    private final Logger logger = LogManager.getLogger(ConnectOperation.class);
    private final int COUNT_PARTS = 265757;
    int countPieces;
    PieceManager(int countPieces){
        logger.trace(countPieces);
        logger.trace(countPieces/COUNT_PARTS);
        this.countPieces = countPieces/COUNT_PARTS;
    }
    public void setNeedPieces(BitSet downloadedPieces, int countParts, Map<Integer, Piece> pieces){

//        for(int i=0;i< countPieces;i++){
//            if(!downloadedPieces.get(i)){
//                logger.trace(countPieces/COUNT_PARTS);
//                logger.trace("set piece!!!");
//                pieces.put(i,new Piece(countParts,i));
//            }
//        }
    }
}
//1910128
//1955971520
//64*5*23
//7360
//265757
