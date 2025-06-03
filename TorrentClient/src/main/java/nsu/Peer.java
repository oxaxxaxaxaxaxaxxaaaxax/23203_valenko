package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Peer {
    private byte[] id;
    private final int serverPort;
    //private final int leecherPort1;
    //private final int leecherPort2;
    private List<Integer> leecherPorts = new ArrayList<>();
    private BitSet bitfield;
    private SocketChannel channel;
    private final Logger logger = LogManager.getLogger(Handler.class);
    private Handler handler= new Handler();
    //private final String ip;

//    Peer(byte[] peerID, int serverPort, int leecherPort1, int leecherPort2){
//        this.id = peerID;
//        this.serverPort = serverPort;
//        //this.leecherPort1 = leecherPort1;
//        //this.leecherPort2 = leecherPort2;
//    }
    Peer(byte[] peerID, int serverPort, int leecherPort1, int leecherPort2){
        this.id = peerID;
        this.serverPort = serverPort;
        logger.debug("id:" + handler.bytesToHex(peerID) + "server port" + serverPort);
        leecherPorts.add(leecherPort1);
        leecherPorts.add(leecherPort2);
        //this.leecherPort1 = leecherPort1;
        //this.leecherPort2 = leecherPort2;
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
    //public int getLeecherPort1() {return leecherPort1;}
    //public int getLeecherPort2() {return leecherPort2;}
    public int getLeecherPort(int number) {
        return leecherPorts.get(number);
    }
    public List<Integer> getLeecherPorts(){ return leecherPorts;}
    public byte[] getId(){return id;}
}
