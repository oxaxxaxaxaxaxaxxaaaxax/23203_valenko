package nsu;


import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleSelectorServer {
    //Peer peer1 = new Peer("127.0.0.1", 5467);
    //Peer peer2 = new Peer("127.0.0.1", 5777);
    //Peer peer3 = new Peer("127.0.0.1", 5554);
    private static final int PORT = 9999;
    private static final int BUFFER_SIZE =1024;


    public static byte[] getSHAHash(ByteBuffer message) throws  java.security.NoSuchAlgorithmException{
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update(message);
        return sha1.digest();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }

    public static void parseTorrentFile()  throws IOException,java.security.NoSuchAlgorithmException {
        Bencode bencode = new Bencode(StandardCharsets.UTF_8);//парсим как байтмассив а не строку
        File torrentFile = new File("/home/oksana/Downloads/Captain.America.Brave.New.World.2025.2xDUB.BDRi_552311_82.torrent");
        byte[] dataArray = new byte[(int)torrentFile.length()];
        try(FileInputStream input = new FileInputStream(torrentFile)){
            input.read(dataArray);
        }
        Map<String, Object> torrentData = (Map<String, Object>)bencode.decode(dataArray, Type.DICTIONARY);
        @SuppressWarnings("unchecked")
        Map<String, Object> info = (Map<String, Object>)torrentData.get("info");
        long length = (Long)info.get("length");
        String name = (String)info.get("name");
        long pieceLength = (Long)info.get("piece length");
        String strPiece = (String)info.get("pieces");
        byte[] pieces = strPiece.getBytes(StandardCharsets.UTF_8);
        byte[] infoHash = getSHAHash(ByteBuffer.wrap(bencode.encode(info)));
        System.out.println(length);
        System.out.println(name);
        System.out.println(pieceLength);
        //System.out.println(Arrays.toString(pieces));
        System.out.println(bytesToHex(infoHash));
    }


    public static void main(String[] args) throws IOException, java.security.NoSuchAlgorithmException {
        parseTorrentFile();
        Selector selector = Selector.open();
        ServerSocketChannel serverChanel = ServerSocketChannel.open();
        serverChanel.configureBlocking(false);
        serverChanel.bind(new InetSocketAddress(PORT));
        serverChanel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while(true){
            System.out.println("Waiting for clients");
            selector.select();
            System.out.println("Accepted client!");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                if(key.isAcceptable()){
                    ServerSocketChannel srv = (ServerSocketChannel)key.channel();
                    SocketChannel client = srv.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Accepted client" + client.getRemoteAddress());
                }
                if(key.isReadable()){
                    SocketChannel client = (SocketChannel)key.channel();
                    buffer.clear();
                    int read = client.read(buffer);
                    if(read==-1){
                        client.close();
                    }
                    else{
                        String message = new String(buffer.array(), 0, read).trim();
                        System.out.println("Received: " +message);
                    }
                }
                iter.remove();
            }
        }
    }
}