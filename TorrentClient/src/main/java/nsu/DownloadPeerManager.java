package nsu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.nio.channels.SocketChannel;
import java.util.Set;

//класс закачки
//public class DownloadPeerManager implements Runnable{
//    private TorrentData torrentData;
//    private BitSet requestedBytes;
//    private byte[] infoHash;
//   // private ConnectOperation op = new ConnectOperation();
//    private List<Peer> peers;
//    private final int PORT;
//    private final String hostname = "127.0.0.1";
//    private Selector selector;
//    private static final int BUFFER_SIZE =1024;
//    DownloadPeerManager(TorrentData metadata, List<Peer> peers,String numberClient){
//        torrentData = metadata;
//        requestedBytes = metadata.getRequestedBytes();
//        this.peers = peers;
//        PORT = peers.get(Integer.parseInt(numberClient)).getPort();
//        infoHash = metadata.getInfoHash();
//    }
//    @Override
//    public void run(){
//        connectWithPeers();
//        System.out.println("End of registration");
//        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
//        while(true){
//            //selector.select();
//            System.out.println("Connected with peer");
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iter = selectedKeys.iterator();
//            while(iter.hasNext()){
//                SelectionKey key = iter.next();
//                if(key.isConnectable()){
//                    SocketChannel peerChannel = (SocketChannel) key.channel();
//                    ByteBuffer habdshake = op.getHandshake(infoHash,);
//                    //peerChannel.write(habdshake);
//                    //peerChannel.register(selector,SelectionKey.OP_READ);
//                }
//                if(key.isReadable()){
//                    SocketChannel peerChannel = (SocketChannel) key.channel();
//                    //int read = peerChannel.read(buffer);
//                    //if(read ==-1){
//                        //peerChannel.close();
//                    }else{
//                        //String message = new String(buffer.array(), 0, read).trim();
//                        //System.out.println("Received: " +message);
//                        op.handlePeerMessage(buffer);
//                    }
//                }
//            }
//        }
//    }
//    public void registerPeer(int port){
//        try(SocketChannel chanel = SocketChannel.open()){
//            chanel.configureBlocking(false);
//            chanel.connect(new InetSocketAddress(hostname,port));
//            chanel.register(selector, SelectionKey.OP_CONNECT);
//        }catch(IOException e){}
//    }
//
//    public void connectWithPeers(){
//        //selector = Selector.open();
//        for(int i=0;i<peers.size();i++){
//            int currentPort =peers.get(i).getPort();
//            if(currentPort == PORT){
//                continue;
//            }
//            registerPeer(currentPort);
//        }
//    }
//}
