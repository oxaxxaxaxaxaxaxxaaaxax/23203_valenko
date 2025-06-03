package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TorrentPeers {
    private List<Peer> peers = new ArrayList<>();
    private static List<Integer> serverPorts = new ArrayList<>();
    private static List<Integer> leecherPorts = new ArrayList<>();
    private int countPeers;
    private final int serverPort;
    private final List<Integer> leechers;
    //private final int leecherPort1;
    //private final int leecherPort2;
    private Map<Integer, byte[]> idList = new ConcurrentHashMap<>();
    Logger logger = LogManager.getLogger(TorrentPeers.class);
    private final Handler handler = new Handler();

    static{
        serverPorts.add(4537);
        serverPorts.add(2486);
        serverPorts.add(6000);
        leecherPorts.add(5567);
        leecherPorts.add(4888);
        leecherPorts.add(6722);
        leecherPorts.add(4000);
        leecherPorts.add(4455);
        leecherPorts.add(3999);
    }

    TorrentPeers(int countPeers,String numberClient){
        this.countPeers = countPeers;
        logger.trace("count peers:"+countPeers);
        fillPortIdMap();
        genPeers();
        serverPort = serverPorts.get(Integer.parseInt(numberClient));
        leechers = peers.get(Integer.parseInt(numberClient)).getLeecherPorts();
        logger.debug("my server port " + serverPort);
        //leecherPort1 = leecherPorts.get(2* Integer.parseInt(numberClient));
        //leecherPort2 = leecherPorts.get(1+ 2*Integer.parseInt(numberClient));
        //logger.debug("my leecher port1" + leecherPort1);
        //logger.debug("my leecher port2" + leecherPort2);
        peers.remove(Integer.parseInt(numberClient));
        this.countPeers --;
        logger.trace("count peers:"+this.countPeers);
        //serverPorts.remove(Integer.parseInt(numberClient));
        //leecherPorts.remove(Integer.parseInt(numberClient));
        //serverPorts.remove(serverPort);
        //leecherPorts.remove(leecherPort1);
        //leecherPorts.remove(leecherPort2);
    }

    public void fillPortIdMap(){
        for(int i=0;i<countPeers;i++){
            idList.put(serverPorts.get(i), genPeerID(serverPorts.get(i)));
            idList.put(leecherPorts.get(i*2),genPeerID(serverPorts.get(i)));
            idList.put(leecherPorts.get(i*2+1),genPeerID(serverPorts.get(i)));
            logger.debug("peer ID: " + handler.bytesToHex(idList.get(serverPorts.get(i))) + " server port: " + serverPorts.get(i));
            logger.debug("peer ID: " + handler.bytesToHex(idList.get(leecherPorts.get(i*2))) + " ports: " + leecherPorts.get(i*2));
            logger.debug("peer ID: " + handler.bytesToHex(idList.get(leecherPorts.get(i*2+1))) + " ports: " + leecherPorts.get(i*2+1));
        }

    }

    public List<Integer> getLeechers(){ return leechers;}

    public int getClientServerPort(){return serverPort;}
    //public int getClientLeecherPort1(){return leecherPort1;}
    //public int getClientLeecherPort2(){return leecherPort2;}
    public List<Peer> getPeers(){
        return peers;
    }
    public int getCountPeers(){return countPeers;}
//    public void genPeers(){
//        for(int i=0;i<countPeers;i++){
//            peers.add(new Peer(genPeerID(),serverPorts.get(i), leecherPorts.get(2*i), leecherPorts.get(2*i+1)));//////////
//        }
//    }

    public void genPeers(){
        for(int i=0;i<countPeers;i++){
            peers.add(new Peer((idList.get(serverPorts.get(i))),serverPorts.get(i),leecherPorts.get(i*2),leecherPorts.get(i*2+1)));//////////
        }
    }

    public byte[] genPeerID(int port){
        byte[] hash = handler.getSHAHashForPort(port);
        String hexHash = handler.bytesToHex(hash);
        logger.trace("port: "+ port + " hash: " + hexHash);
        return hash;
    }

    public int getServerPort(int numberPeer){
        return serverPorts.get(numberPeer);
    }

    public byte[] getPeerID(int port){
        return idList.get(port);
    }
//
//    public byte[] getPeerID(int port){//тут порт от канала(которым подклюились)
//        for(int i=0;i<countPeers;i++){
//            Peer peer =peers.get(i);
////            if((peer.getLeecherPort1() == port)||(peer.getLeecherPort2() == port)){
////                logger.trace("leecher port! "+ port);
////                return peer.getId();
////            }
//            if(peer.getServerPort() == port){
//                logger.trace("server port! "+ port);
//                return  peer.getId();
//            }
//        }
//        logger.trace("not find");
//        return peers.get(0).getId();//по хорошему исключение
//    }
}
