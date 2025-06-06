package nsu;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CutFile {
    public static void main(String[] args) {
        FileFunc f  = new FileFunc();
        if (Integer.parseInt(args[1]) == 1) {
            f.fillZeroEnd(args[0]);
        } else if (Integer.parseInt(args[1]) == 0){
            f.fillZeroStart(args[0]);
        }
    }
}
