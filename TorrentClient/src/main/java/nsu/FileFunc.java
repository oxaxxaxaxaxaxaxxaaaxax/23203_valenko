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
    public void fillZeroEnd(String path) {
        File file = new File(path);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {
            logger.trace("fillZeroEnd");
            logger.trace("path "+path);
            long length = channel.size();
            long half = length / 2;

            // Пишем нули в нужный диапазон
            ByteBuffer zeroBuf = ByteBuffer.allocate(8 * 1024);
            channel.position(half);
            long remaining = length - half;

            while (remaining > 0) {
                zeroBuf.clear();
                int writeLen = (int) Math.min(zeroBuf.capacity(), remaining);
                zeroBuf.limit(writeLen);
                channel.write(zeroBuf);
                remaining -= writeLen;
            }
        } catch (IOException e) {
            logger.error("IOexception" + e.getMessage());
        }
    }

    public void fillZeroStart (String path){
        File file = new File(path);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {
            logger.trace("fillZeroStart");
            logger.trace("path "+path);
            long length = channel.size();
            long half = length / 2;
            ByteBuffer zeroBuf = ByteBuffer.allocate(8 * 1024);
            channel.position(0);
            long remaining = half;

            while (remaining > 0) {
                zeroBuf.clear();
                int writeLen = (int) Math.min(zeroBuf.capacity(), remaining);
                zeroBuf.limit(writeLen);
                channel.write(zeroBuf);
                remaining -= writeLen;
            }
        } catch (IOException e) {
            logger.error("IOexception" + e.getMessage());
        }
    }
}

