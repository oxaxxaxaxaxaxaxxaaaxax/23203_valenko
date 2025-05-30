package nsu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class Peer {
    private byte[] id;
    private final int port;
    private BitSet bitfield;
    private SocketChannel channel;
    //private final String ip;

    Peer(byte[] peerID, int port){
        this.id = peerID;
        this.port = port;
    }
    public void setPeerBitfield(BitSet peerBitfield){
        bitfield = peerBitfield;
    }
    public void setLoadedPiece(int index){
        bitfield.set(index);
    }
    public SocketChannel getChannel(){return channel;}
    public BitSet getBitfield(){return bitfield;}
    public void setId(byte[] id){
        this.id = id;
    }
    public int getPort(){return port;}
    public byte[] getId(){return id;}
}
