package nsu;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TorrentClient {
    private static final int peerCount = 3;
    public static void main(String[] args)throws java.io.IOException, java.security.NoSuchAlgorithmException{
        Logger logger = LogManager.getLogger(TorrentClient.class);
        logger.info("Start");
        logger.trace("put number client starting to 0....");
        TorrentData metadata;
        String numberClient;
        if(args.length==3){
            logger.trace("length =3");
            metadata = new TorrentData(args[0],args[1], peerCount);
            numberClient = args[2];
            System.out.println(numberClient);
        }else if(args.length==2){
            logger.trace("length =2");
            metadata = new TorrentData(args[0],peerCount);
            numberClient = args[1];
            System.out.println(numberClient);
        }else{
            logger.trace("put .torrent file");
            return;
        }
        TorrentPeers torrentPeers = new TorrentPeers(peerCount,numberClient);
        logger.trace("torent Peers: "+ torrentPeers.getPeers().size());
        metadata.parseTorrentFile();
        //metadata.fillBitField();
        metadata.createCard();
        logger.trace("peer manager start");
        PeerManager manager = new PeerManager(metadata,numberClient,torrentPeers);
        manager.connectToLoad();
        //Thread downloadData = new Thread(new DownloadPeerManager(metadata, peers,args[2]));
        //Thread distributeData = new Thread(new DistributeManager(metadata, peers, args[2]));
        //downloadData.start();
        //distributeData.start();
    }

}
