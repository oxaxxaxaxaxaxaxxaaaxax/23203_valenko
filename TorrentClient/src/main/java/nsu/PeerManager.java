package nsu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private List<Integer> leecherPorts;
    //private final int leecherPORT1;
    //private final int leecherPORT2;
    private final String hostname = "127.0.0.1";
    private Selector selector;
    private static final int BUFFER_SIZE = 17000;///////////////
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final Logger logger = LogManager.getLogger(PeerManager.class);
    private Map<SocketChannel, ConnectionContext> contexts = new ConcurrentHashMap<>();

    PeerManager(TorrentData metadata, String numberClient, TorrentPeers torrentPeers) {
        torrentData = metadata;
        downloadsBytes = metadata.getDownloadedBytes();
        this.torrentPeers = torrentPeers;
        logger.trace("torent Peers: "+ torrentPeers.getPeers().size());
        try {
            selector = Selector.open();
        } catch (IOException e) {
            logger.trace("IOexception "+e.getMessage());
            throw new RuntimeException(e);
        }
        op = new ConnectOperation(torrentPeers,metadata,contexts,selector);
        //this.peers = peers;
        serverPORT = torrentPeers.getClientServerPort();
        leecherPorts = torrentPeers.getLeechers();
        //leecherPORT1 = torrentPeers.getClientLeecherPort1();
        //leecherPORT2 = torrentPeers.getClientLeecherPort2();
        infoHash = metadata.getInfoHash();
    }

    public void connectToLoad() throws IOException{
        logger.trace("start connectToLoad method");
        serverSetUp();
        connectWithPeers();
        handlePeers();
    }

    public void serverSetUp() throws IOException{
        logger.trace("start handle peers");
        ServerSocketChannel serverChanel = ServerSocketChannel.open();
        //serverChanel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
        serverChanel.socket().setReuseAddress(true);
        serverChanel.configureBlocking(false);
        serverChanel.bind(new InetSocketAddress(serverPORT));
        serverChanel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void handlePeers(){
        //ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (true) {
            System.out.println("Waiting for clients");
            try{
                selector.select();
            } catch (IOException e) {
                throw new RuntimeException("bad selector");
            }
            //logger.trace("Accepted client!");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if(!key.isValid()){
                    continue;
                }
                if (key.isAcceptable()) {
                    logger.trace("accept peer");
                    try {
                        ServerSocketChannel srv = (ServerSocketChannel) key.channel();
                        SocketChannel client = srv.accept();
                        client.configureBlocking(false);
                        //ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);
                        ConnectionContext ctx = new ConnectionContext(client,BUFFER_SIZE);
                        //client.register(selector, SelectionKey.OP_READ, buff);
                        client.register(selector, SelectionKey.OP_READ, ctx);
                        contexts.put(client,ctx);
                        System.out.println("accepted peer" + client.getRemoteAddress());
                    }catch(IOException e){
                        logger.trace("accept exception: "+ e.getMessage());
                    }
                }
                if (key.isConnectable()) {//подключение завершилось
                    SocketChannel customChannel = (SocketChannel) key.channel();
                    ConnectionContext ctx = (ConnectionContext) key.attachment();
                    try{
                        if(customChannel.finishConnect()){
                            logger.trace("connect to peer");
                        }else{
                            logger.trace("bad connect to peer");
                        }
                    }catch(IOException e){
                        logger.trace("connect exception: "+ e.getMessage());
                        //InetSocketAddress clientAddr =(InetSocketAddress)customChannel.getRemoteAddress();
                        //InetSocketAddress serverAddr = (InetSocketAddress)customChannel.getRemoteAddress();
                        contexts.remove(customChannel);
                        logger.trace("counter "+ctx.getConnectionCounter());
                        if(ctx.getConnectionCounter() <2){
                            try{
                                logger.trace("connect again");
                                ctx.incrConnectionCounter();
                                logger.trace("before address");
                                logger.trace("after address");
                                try{
                                    customChannel.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                key.cancel();
                                logger.trace("before threads");
                                Thread.sleep(5000);
                                connectWithPeers();

                            } catch (InterruptedException ex) {
                                logger.trace("interrupt! "+e.getMessage());
                                throw new RuntimeException(ex);
                            }
                            break;
                        }
                    }
                    try{
                        InetSocketAddress peerAddres = (InetSocketAddress)customChannel.getRemoteAddress();
                        logger.trace("peer address: " + peerAddres);
                        ByteBuffer handshake = op.createInitialHandshake(peerAddres);
                        ctx.addToQueue(handshake,selector);
                        key.interestOps(SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                        //customChannel.write(handshake);
                        logger.trace("write handshake");
                        //ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);
                        //customChannel.register(selector, SelectionKey.OP_READ,ctx);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (key.isReadable()) {
                    try{
                        logger.trace("read peer message!");
                        //SocketChannel client = (SocketChannel) key.channel();
                        ConnectionContext ctx = (ConnectionContext)key.attachment();
                        SocketChannel client = ctx.getChannel();
                        ByteBuffer readBuffer = ctx.getReadBuffer();
                        if(!client.isOpen()){
                            client.close();
                            key.cancel();
                            contexts.remove(client);
                        }
                        //ByteBuffer readBuffer = (ByteBuffer) key.attachment();
                        readBuffer.clear();
                        int read = client.read(readBuffer);
                        if (read == -1) {
                            client.close();
                            key.cancel();
                            contexts.remove(client);
                        } else {
                            //String message = new String(readBuffer.array(), 0, read).trim();
                            //System.out.println("Received: " + message);
                            logger.trace("position: " + readBuffer.position());
                            readBuffer.flip();
                            logger.trace("position: " + readBuffer.position());
                            byte[] copyBuffer = new byte[readBuffer.remaining()];
                            readBuffer.get(copyBuffer);
                            ByteBuffer buffer = ByteBuffer.wrap(copyBuffer);
                            executor.submit(() -> {
                                Id sendMessageID = op.handlePeerMessage(buffer, client);
                                op.sendMessage(sendMessageID, client, buffer);
                            });
                        }
                    }catch (IOException e){
                        ConnectionContext ctx = (ConnectionContext)key.attachment();
                        SocketChannel client = ctx.getChannel();
                        logger.info("peer is unavailable");
                        try{
                            client.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        key.cancel();
                        contexts.remove(client);
                    }
                }
                if(!key.isValid()){
                    continue;
                }
                if(key.isWritable()){
                    try{
                        ConnectionContext ctx = (ConnectionContext) key.attachment();
                        SocketChannel client = ctx.getChannel();
                        Queue<ByteBuffer> queue = ctx.getWriteQueue();
                        while (true){
                            ByteBuffer buffer = queue.peek();
                            if(buffer == null){
                                key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                                break;
                            }
                            client.write(buffer);
                            if(buffer.hasRemaining()){
                                logger.trace("message send not full");
                            }
                            queue.poll();//возможно проверить что все записалось
                        }
                    }catch (IOException e){
                        logger.info("peer is unavailable");
                        ConnectionContext ctx = (ConnectionContext) key.attachment();
                        SocketChannel client = ctx.getChannel();
                        try{
                            client.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        key.cancel();
                        contexts.remove(client);
                    }

                }
            }
        }
    }

    public void connectToPeer(int serverPort, int leecherPort) {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            logger.trace("my leecher port " + leecherPort);
            channel.socket().setReuseAddress(true);
            channel.bind(new InetSocketAddress(leecherPort));
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(hostname, serverPort));
            ConnectionContext ctx = new ConnectionContext(channel, BUFFER_SIZE);
            contexts.put(channel,ctx);
            logger.trace("leecher address:"+ channel.getLocalAddress());
            channel.register(selector, SelectionKey.OP_CONNECT,ctx);
        } catch (IOException e) {
            logger.trace("fail connection!!!!!");
            logger.trace("connection failed "+ e.getMessage());
            if(channel!=null){
                try{
                    channel.close();
                } catch (IOException ex) {
                    logger.trace("EXC "+e.getMessage());
                }

            }
            contexts.remove(channel);
        }
    }

    public void connectWithPeers() {
        logger.trace("selector1 "+ selector);
        logger.trace("selector3 "+ op.getSelector());
        List<Peer> peers = torrentPeers.getPeers();
        for (int i = 0; i < peers.size(); i++) {
            int currentServerPort = peers.get(i).getServerPort();
            logger.trace("another server port " + currentServerPort);
            if (currentServerPort == serverPORT) {
                continue;
            }
            int currentLeechPort = leecherPorts.get(i);
            connectToPeer(currentServerPort,currentLeechPort);
        }
    }
}


