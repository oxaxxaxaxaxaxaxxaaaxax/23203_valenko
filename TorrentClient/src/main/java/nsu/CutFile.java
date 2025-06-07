package nsu;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CutFile {
    public static void main(String[] args) {
        FileFunc f  = new FileFunc();
        if (Integer.parseInt(args[1]) == 1) {
            f.fillZeroEnd(args[0],args[2]);
        } else if (Integer.parseInt(args[1]) == 0){
            f.fillZeroStart(args[0],args[2]);
        }else if(Integer.parseInt(args[1]) == 2){
            f.fillZeroAll(args[0],args[2]);
        }
    }
}
