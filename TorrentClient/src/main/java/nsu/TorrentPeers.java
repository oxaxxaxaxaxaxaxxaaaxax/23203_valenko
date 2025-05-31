package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TorrentPeers {
    private List<Peer> peers = new ArrayList<>();
    private static List<Integer> serverPorts = new ArrayList<>();
    private static List<Integer> leecherPorts = new ArrayList<>();
    private final int countPeers;
    private final int serverPort;
    private final int leecherPort;
    Logger logger = LogManager.getLogger(TorrentPeers.class);

    static{
        serverPorts.add(4537);
        serverPorts.add(2486);
        serverPorts.add(6000);
        leecherPorts.add(5567);
        leecherPorts.add(4888);
        leecherPorts.add(6722);
    }

    TorrentPeers(int countPeers,String numberClient){
        this.countPeers = countPeers -1;
        genPeers();
        serverPort = serverPorts.get(Integer.parseInt(numberClient));
        logger.debug("my server port " + serverPort);
        leecherPort = leecherPorts.get(Integer.parseInt(numberClient));
        logger.debug("my leecher port" + leecherPort);
        peers.remove(Integer.parseInt(numberClient));
        serverPorts.remove(Integer.parseInt(numberClient));
        leecherPorts.remove(Integer.parseInt(numberClient));
    }
    public int getClientServerPort(){return serverPort;}
    public int getClientLeecherPort(){return leecherPort;}
    public List<Peer> getPeers(){
        return peers;
    }
    public int getCountPeers(){return countPeers;}
    public void genPeers(){
        for(int i=0;i<countPeers;i++){
            peers.add(new Peer(genPeerID(),serverPorts.get(i), leecherPorts.get(i)));//////////
        }
    }

    public byte[] genPeerID(){
        Random rand = new Random();
        byte[] peerId = new byte[20];
        rand.nextBytes(peerId);
        return peerId;
    }

    public int getServerPort(int numberPeer){
        return serverPorts.get(numberPeer);
    }

    public byte[] getPeerID(int port){//тут порт от канала(которым подклюились)
        for(int i=0;i<countPeers;i++){
            Peer peer =peers.get(i);
            if(peer.getLeecherPort() == port){
                return peer.getId();
            }
        }
        return peers.get(0).getId();//по хорошему исключение
    }
}
