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

//public class DistributeManager implements Runnable{
//    TorrentData torrentData;
//    BitSet requestedBytes;
//    List<Peer> peers;
//    //private ConnectOperation op = new ConnectOperation();
//    private final int PORT;
//    private static final int BUFFER_SIZE =1024;
//    DistributeManager(TorrentData metadata, List<Peer> peers, String numberClient){
//        torrentData = metadata;
//        requestedBytes = metadata.getRequestedBytes();
//        this.peers = peers;
//        PORT = peers.get(Integer.parseInt(numberClient)).getPort();
//    }
//    @Override
//    public void run(){
//        try{
//            handleConnection();
//        }catch(IOException e){}
//
//    }
//
//    public void handleConnection() throws IOException {
//        Selector selector = Selector.open();
//        ServerSocketChannel serverChanel = ServerSocketChannel.open();
//        serverChanel.configureBlocking(false);
//        serverChanel.bind(new InetSocketAddress(PORT));
//        serverChanel.register(selector, SelectionKey.OP_ACCEPT);
//        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
//        while(true){
//            System.out.println("Waiting for clients");
//            selector.select();
//            System.out.println("Accepted client!");
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iter = selectedKeys.iterator();
//            while(iter.hasNext()){
//                SelectionKey key = iter.next();
//                if(key.isAcceptable()){
//                    try(ServerSocketChannel srv = (ServerSocketChannel)key.channel();){
//                        SocketChannel client = srv.accept();
//                        client.configureBlocking(false);
//                        client.register(selector, SelectionKey.OP_READ);
//                        System.out.println("Accepted client" + client.getRemoteAddress());
//                    }
//                }
//                if(key.isReadable()){
//                    SocketChannel client = (SocketChannel)key.channel();
//                    buffer.clear();
//                    int read = client.read(buffer);
//                    if(read==-1){
//                        client.close();
//                    }
//                    else{
//                        String message = new String(buffer.array(), 0, read).trim();
//                        System.out.println("Received: " +message);
//                       // op.handlePeerMessage(buffer);
//                    }
//                }
//                iter.remove();
//            }
//        }
//    }
//}
