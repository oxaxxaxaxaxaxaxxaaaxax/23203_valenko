package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileFunc {
    private final Logger logger = LogManager.getLogger(FileFunc.class);
    public void fillZeroEnd(String path,String piecesLength) {
        File file = new File(path);
        int pieceLength = Integer.parseInt(piecesLength);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {
            logger.trace("fillZeroEnd");
            logger.trace("path "+path);
            long length = channel.size();
            int countPieces = (int)(length + pieceLength -1)/pieceLength;
            long half = countPieces / 2;
            logger.trace("countPieces " +countPieces);

            // Пишем нули в нужный диапазон
            ByteBuffer zeroBuf = ByteBuffer.allocate((int)pieceLength);
            channel.position(half*pieceLength);
            long remaining = (countPieces - half)*pieceLength;
            logger.trace("remaining zero "+ remaining/32768);

            while (remaining > 0) {
                zeroBuf.clear();
                for (int i = 0; i < zeroBuf.capacity(); i++) {
                    zeroBuf.put((byte) 0);
                }
                zeroBuf.flip();
                int writeLen = (int)pieceLength;
                logger.trace("writelen "+writeLen);
                zeroBuf.limit(writeLen);
                channel.write(zeroBuf);
                remaining -= writeLen;
            }
        } catch (IOException e) {
            logger.error("IOexception" + e.getMessage());
        }
    }

    public void fillZeroStart (String path,String piecesLength){
        File file = new File(path);
        //int countPieces = Integer.parseInt(countPiece);
        int pieceLength = Integer.parseInt(piecesLength);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {
            logger.trace("fillZeroStart");
            logger.trace("path "+path);
            long length = channel.size();
            int countPieces = (int)(length + pieceLength -1)/pieceLength;
            long half = countPieces / 2;
            logger.trace("countPieces " +countPieces);
            ByteBuffer zeroBuf = ByteBuffer.allocate((int)pieceLength);
            channel.position(0);
            long remaining = half * pieceLength;
            logger.trace("remaining zero "+ remaining/32768);

            while (remaining > 0) {
                zeroBuf.clear();
                for (int i = 0; i < zeroBuf.capacity(); i++) {
                    zeroBuf.put((byte) 0);
                }
                zeroBuf.flip();
                int writeLen = (int)pieceLength;
                logger.trace("writelen "+writeLen);
                zeroBuf.limit(writeLen);
                channel.write(zeroBuf);
                remaining -= writeLen;
            }
        } catch (IOException e) {
            logger.error("IOexception" + e.getMessage());
        }
    }
    public void fillZeroAll (String path,String piecesLength){
        File file = new File(path);
        //int countPieces = Integer.parseInt(countPiece);
        int pieceLength = Integer.parseInt(piecesLength);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {
            logger.trace("fillZeroAll");
            logger.trace("path "+path);
            long length = channel.size();
            int countPieces = (int)(length + pieceLength -1)/pieceLength;
            logger.trace("countPieces " +countPieces);
            ByteBuffer zeroBuf = ByteBuffer.allocate((int)pieceLength);
            channel.position(0);
            long remaining = (long) countPieces * pieceLength;
            logger.trace("remaining zero "+ remaining/32768);

            while (remaining > 0) {
                zeroBuf.clear();
                for (int i = 0; i < zeroBuf.capacity(); i++) {
                    zeroBuf.put((byte) 0);
                }
                zeroBuf.flip();
                int writeLen = (int)pieceLength;
                logger.trace("writelen "+writeLen);
                zeroBuf.limit(writeLen);
                channel.write(zeroBuf);
                remaining -= writeLen;
            }
        } catch (IOException e) {
            logger.error("IOexception" + e.getMessage());
        }
    }
}

