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
    private final int serverPort;
    private final int leecherPort;
    private BitSet bitfield;
    private SocketChannel channel;
    //private final String ip;

    Peer(byte[] peerID, int serverPort, int leecherPort){
        this.id = peerID;
        this.serverPort = serverPort;
        this.leecherPort = leecherPort;
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
    public int getServerPort(){return serverPort;}
    public int getLeecherPort() {return leecherPort;}
    public byte[] getId(){return id;}
}
