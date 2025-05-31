package nsu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PeerManager{
    private TorrentData torrentData;
    private BitSet downloadsBytes;
    private final byte[] infoHash;
    private ConnectOperation op ;
    private final TorrentPeers torrentPeers;
    private final int serverPORT;//порт пира запустившего программу
    private final int leecherPORT;
    private final String hostname = "127.0.0.1";
    private Selector selector;
    private static final int BUFFER_SIZE = 1024;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Logger logger = LogManager.getLogger(PeerManager.class);

    PeerManager(TorrentData metadata, String numberClient, TorrentPeers torrentPeers) {
        torrentData = metadata;
        downloadsBytes = metadata.getDownloadedBytes();
        this.torrentPeers = torrentPeers;
        logger.trace("torent Peers: "+ torrentPeers.getPeers().size());
        op = new ConnectOperation(torrentPeers,metadata);
        //this.peers = peers;
        serverPORT = torrentPeers.getClientServerPort();
        leecherPORT = torrentPeers.getClientLeecherPort();
        infoHash = metadata.getInfoHash();
    }

    public void connectToLoad() throws IOException{
        logger.trace("start connectToLoad method");
        connectWithPeers();
        handlePeers();
    }

    public void handlePeers() throws IOException {
        logger.trace("start handle peers");
        ServerSocketChannel serverChanel = ServerSocketChannel.open();
        serverChanel.configureBlocking(false);
        serverChanel.bind(new InetSocketAddress(serverPORT));
        serverChanel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (true) {
            System.out.println("Waiting for clients");
            selector.select();
            //logger.trace("Accepted client!");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    logger.trace("accept peer");
                    try {
                        ServerSocketChannel srv = (ServerSocketChannel) key.channel();
                        SocketChannel client = srv.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("accepted peer" + client.getRemoteAddress());
                    }catch(IOException e){
                        logger.trace("accept exception: "+ e.getMessage());
                    }
                }
                if (key.isConnectable()) {//подключение завершилось
                    SocketChannel customChannel = (SocketChannel) key.channel();
                    try{
                        if(customChannel.finishConnect()){
                            logger.trace("connect to peer");
                        }else{
                            logger.trace("bad connect to peer");
                        }
                    }catch(Exception e){
                        logger.trace("connect exception: "+ e.getMessage());
                        key.cancel();
                        customChannel.close();
                        break;
                    }
                    InetSocketAddress peerAddres = (InetSocketAddress)customChannel.getRemoteAddress();
                    logger.trace("peer address: " + peerAddres);
                    ByteBuffer handshake = op.createInitialHandshake(peerAddres);
                    customChannel.write(handshake);
                    logger.trace("write handshake");
                    customChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    logger.trace("read peer message!");
                    SocketChannel client = (SocketChannel) key.channel();
                    readBuffer.clear();
                    int read = client.read(readBuffer);
                    if (read == -1) {
                        client.close();
                    } else {
                        String message = new String(readBuffer.array(), 0, read).trim();
                        System.out.println("Received: " + message);
                        readBuffer.flip();
                        byte[] copyBuffer = new byte[readBuffer.remaining()];
                        readBuffer.get(copyBuffer);
                        ByteBuffer buffer = ByteBuffer.wrap(copyBuffer);
                        executor.submit(()->{
                            Id sendMessageID = op.handlePeerMessage(buffer,client);
                            op.sendMessage(sendMessageID, client,buffer);
                        });
                    }
                }
                iter.remove();
            }
        }
    }

    public void connectToPeer(int port) {
        SocketChannel channel;
        try {
            channel = SocketChannel.open();
            logger.trace("my leecher port " + leecherPORT);
            channel.bind(new InetSocketAddress(leecherPORT));
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(hostname, port));
            channel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            //???
        }
    }

    public void connectWithPeers() throws IOException {
        selector = Selector.open();
        List<Peer> peers = torrentPeers.getPeers();
        for (int i = 0; i < peers.size(); i++) {
            int currentServerPort = peers.get(i).getServerPort();
            logger.trace("another server port " + currentServerPort);
            if (currentServerPort == serverPORT) {
                continue;
            }
            connectToPeer(currentServerPort);
        }
    }
}


