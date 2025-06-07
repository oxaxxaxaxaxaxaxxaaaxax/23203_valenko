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
    private SocketChannel channel =null;
    private final Logger logger = LogManager.getLogger(Peer.class);
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
        synchronized (peerBitfield){
            bitfield = peerBitfield;
        }
    }
    public void setLoadedPiece(int index){
        logger.trace("setting piece in index "+index);
//        if(bitfield.get(index)){
//            logger.trace("this piece already loaded");
//        }
        synchronized (bitfield){
            bitfield.set(index);
        }
        logger.trace("set successful");
    }
    public SocketChannel getChannel(){return channel;}
    public void setChannel(SocketChannel channel){this.channel = channel;}
    public boolean hasBindChannel() {
        if (channel == null) {
            logger.trace("channel not bind");
            return false;
        }
        logger.trace("channel bind");
        return true;
    }
    public BitSet getBitfield() {
        synchronized (bitfield) {
            return bitfield;
        }
    }
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
