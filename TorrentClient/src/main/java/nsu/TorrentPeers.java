package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TorrentPeers {
    private List<Peer> peers = new ArrayList<>();
    private static List<Integer> peerPorts = new ArrayList<>();
    private final int countPeers;
    private final int port;
    Logger logger = LogManager.getLogger(TorrentPeers.class);

    static{
        peerPorts.add(4537);
        peerPorts.add(2486);
        peerPorts.add(6000);
    }

    TorrentPeers(int countPeers,String numberClient){
        countPeers-=1;
        this.countPeers = countPeers;
        genPeers();
        port = peerPorts.get(Integer.parseInt(numberClient));
        logger.debug("my port " + port);
        peers.remove(Integer.parseInt(numberClient));
        peerPorts.remove(Integer.parseInt(numberClient));
    }
    public int getClientPort(){return port;}
    public List<Peer> getPeers(){
        return peers;
    }
    public int getCountPeers(){return countPeers;}
    public void genPeers(){
        for(int i=0;i<countPeers;i++){
            peers.add(new Peer(genPeerID(),peerPorts.get(i)));
        }
    }

    public byte[] genPeerID(){
        Random rand = new Random();
        byte[] peerId = new byte[20];
        rand.nextBytes(peerId);
        return peerId;
    }

    public int getPort(int numberPeer){
        return peerPorts.get(numberPeer);
    }

    public byte[] getPeerID(int port){
        for(int i=0;i<countPeers;i++){
            Peer peer =peers.get(i);
            if(peer.getPort() == port){
                return peer.getId();
            }
        }
        return peers.get(0).getId();//по хорошему исключение
    }
}
