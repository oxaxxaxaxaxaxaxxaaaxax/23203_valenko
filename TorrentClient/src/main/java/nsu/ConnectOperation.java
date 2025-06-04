package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

enum Id{
    CHOKE(0),
    UNCHOKE(1),
    INTERESTED(2),
    NOT_INTERESTED(3),
    HAVE(4),
    BITFIELD(5),
    REQUEST(6),
    PIECE(7),
    END_CONNECT(8),
    NOT_HANDSHAKE(9);

    private final int id;
    static Map<Integer,Id> ids = new HashMap<>();
    static{
        for(Id id: Id.values()){
            ids.put(id.getId(),id);
        }
    }
    Id(int id){
        this.id = id;
    }
    public static int ByteToInt(byte idByte){
        return idByte & 0xFF;
    }
    public static Id IntToId(int id){
        return ids.get(id);
    }
    public int getId(){return id;}
}

enum HandShake{
    INITIAL_HANDSHAKE,
    COMPLETE_HANDSHAKE,
    WAIT_HANDSHAKE,
}

public class ConnectOperation {
    private RandomAccessFile localFile;
    private final TorrentPeers torrentPeers;
    private BitSet downloadedPieces;
    private Handler handler= new Handler();
    BlockManager blockManager;
    private Map<Integer, Piece> notDownloadedPieces = new ConcurrentHashMap<>();
    private final long countPieces;
    private final int partSize = 16* 1024;
    private final long pieceSize;
    private final int MESSAGE_ID_INDEX = 4;
    private TorrentData metadata;
    private Map<String, HandShake> peerHandshakes= new ConcurrentHashMap<>();
    private final Logger logger = LogManager.getLogger(ConnectOperation.class);

    ConnectOperation(TorrentPeers torrentPeers, TorrentData metadata) {
        this.torrentPeers = torrentPeers;
        this.metadata = metadata;
        downloadedPieces = metadata.getDownloadedBytes();
        try{
            localFile = new RandomAccessFile(metadata.getFile(),"rw");
        } catch (FileNotFoundException e) {
            logger.trace("failed create file " + e.getMessage());
        }
        //countPieces = downloadedPieces.size();
        countPieces = metadata.getCountPieces();
        pieceSize = metadata.getPieceSize();
        blockManager = new BlockManager(countPieces, metadata.getLength(),pieceSize,downloadedPieces,(int)(pieceSize+partSize - 1)/partSize);
        //blockManager.setNeedPiecesInBlock(0);//должно делиться нацело
        fillHandshakes(torrentPeers);
    }

    public void fillHandshakes(TorrentPeers torrentPeers){
        List<Peer> peers = torrentPeers.getPeers();
        int peerCount = torrentPeers.getCountPeers();
        logger.trace("count torrent peers "+ peerCount + " " + peers.size());
        for(int i=0;i< peerCount;i++){
            logger.trace("current peer: "+ (i+1));
            Peer peer = peers.get(i);
            peerHandshakes.put(handler.bytesToHex(handler.getSHAHashForPort(peer.getServerPort())),HandShake.INITIAL_HANDSHAKE);
            logger.trace("current port" + peer.getServerPort());
            logger.trace("hash: " + handler.bytesToHex(handler.getSHAHashForPort(peer.getServerPort())));
            logger.trace("handshake: " + peerHandshakes.get(handler.bytesToHex(handler.getSHAHashForPort(peer.getServerPort()))));
        }
    }

//    public void setNeedPieces(BitSet downloadedPieces,int countParts, Map<Integer, Piece> pieces){
//        for(int i=0;i< countPieces;i++){
//            if(!downloadedPieces.get(i)){
//                logger.trace("set piece");
//                pieces.put(i,new Piece(countParts,i));
//            }
//        }
//    }

    public void setWaitHandshake(InetSocketAddress peerAddres){
        peerHandshakes.put(handler.bytesToHex(handler.getSHAHashForPort(peerAddres.getPort())),HandShake.WAIT_HANDSHAKE);
    }

    public ByteBuffer createHandshake(InetSocketAddress peerAddres){
        logger.debug("create handshake");
        byte[] infoHash = metadata.getInfoHash();
        return handler.getHandshake(infoHash,peerAddres,torrentPeers);
    }

    public ByteBuffer createInitialHandshake(InetSocketAddress peerAddres){
        logger.debug("create initial handshake");
        setWaitHandshake(peerAddres);
        return createHandshake(peerAddres);
    }

    public ByteBuffer createBitfield(){
        logger.trace("create bitfield");
        return handler.getBitfield(downloadedPieces);
    }

    public boolean hasNeededPieces(BitSet peerPieces) {
//        System.out.println("peer bitfield:");
//        for(int i=0;i< countPieces;i++){
//            System.out.print(peerPieces.get(i));
//        }
//        System.out.println("my bitfield:");
//        for(int i=0;i< countPieces;i++){
//            System.out.print(downloadedPieces.get(i));
//        }
        BitSet neededPieces = (BitSet) peerPieces.clone();
        neededPieces.andNot(downloadedPieces);
        return !neededPieces.isEmpty();
    }

    public void setPeerBitfield(BitSet peerPieces, SocketChannel channel){
        try{
            InetSocketAddress addr = (InetSocketAddress) channel.getRemoteAddress();
            int peerPort = addr.getPort();
            int countPeer = torrentPeers.getCountPeers();
            List<Peer> peers = torrentPeers.getPeers();
            for(int i=0;i<countPeer;i++){
                Peer peer = peers.get(i);
                if(Arrays.equals(torrentPeers.getPeerID(peerPort),peer.getId())){
                    logger.trace("find peer!! "+ peerPort);
                    peer.setPeerBitfield(peerPieces);
                }
//                if(peer.getLeecherPort() == peerPort){/// /////
//                    peer.setPeerBitfield(peerPieces);
//                }//НАПИСАТЬ УДАЛИЛА ЧТОБЫ СКОМПИЛИЛОСЬ
            }
        }catch (IOException e){}
    }

    public Id handleBitfield(ByteBuffer buffer, SocketChannel channel) {
        logger.trace("handle peer's bitfield");
        logger.trace("position:" + buffer.position());
        int length = buffer.getInt();
        logger.trace("length:" + length);
        buffer.get();
        if(length == 1){
            return Id.NOT_INTERESTED;
        }
        logger.trace("length >1");
        byte[] buff = new byte[length - 1];
        buffer.get(buff);
        logger.trace("position:" + buffer.position());
        buffer.flip();
        logger.trace("position:" + buffer.position());
        BitSet peerPieces = handler.ByteToBit(buff, (int)countPieces);
        setPeerBitfield(peerPieces,channel);
        if (hasNeededPieces(peerPieces)) {
            return Id.INTERESTED;
        }
        return Id.NOT_INTERESTED;
    }

    public ByteBuffer requestPiece(SocketChannel channel){
        BitSet peerPieces;
        List<Peer> peers = torrentPeers.getPeers();
        int countPeer = torrentPeers.getCountPeers();
        for(int i=0;i<countPeer;i++) {
            Peer peer = peers.get(i);
            if(peer.getChannel() == channel){
                peerPieces = peer.getBitfield();
                BitSet neededPieces = (BitSet) peerPieces.clone();
                neededPieces.andNot(downloadedPieces);
                int pieceIndex = neededPieces.nextSetBit(0);
                int offset = blockManager.getNotDownloadedPart(pieceIndex);//с учетом байтового смещения(глобальный индекс)
                return handler.getRequest(pieceIndex,offset,partSize);
            }
        }
        return null;//?????
    }

    public Id handleRequest(ByteBuffer buffer){
        int lengthMessage = buffer.getInt();
        int id = buffer.get();
        int index = buffer.getInt();
//        int offset = buffer.getInt();
//        int length = buffer.getInt();
        if(!downloadedPieces.get(index)){
            return Id.END_CONNECT;
        }
        return Id.PIECE;
    }

    public ByteBuffer sendPiece(ByteBuffer buffer) {
        try {
            buffer.rewind();
            buffer.getInt();
            buffer.get();
            int index = buffer.getInt();
            int offset = buffer.getInt();
            int length = buffer.getInt();
            long filePosition = index * pieceSize + offset;
            localFile.seek(filePosition);
            byte[] sendData = new byte[length];
            localFile.readFully(sendData);
            return handler.getPiece(index,offset,sendData);
        } catch (IOException e) {return null;}
    }

    public void askAllPeer(){
        List<Peer> peers = torrentPeers.getPeers();
        int countPeer = torrentPeers.getCountPeers();
        for(int i=0;i<countPeer;i++){
            Peer peer =peers.get(i);
            if(hasNeededPieces(peer.getBitfield())){
                SocketChannel peerChannel =  peer.getChannel();
                try{
                    peerChannel.write(handler.getInterested());
                } catch (IOException e) {}

            }
        }
        //ни у кого не нашли куски
    }

    public void sendHaveMessage(int index){
        List<Peer> peers = torrentPeers.getPeers();
        int countPeer = torrentPeers.getCountPeers();
        for(int i=0;i<countPeer;i++){
            SocketChannel peerChannel =peers.get(i).getChannel();
            try{
                peerChannel.write(handler.getHave(index));
            } catch (IOException e) {}
        }
    }

    public Id handlePiece(ByteBuffer buffer){
        try{
            buffer.getInt();
            buffer.get();
            int index = buffer.getInt();
            int offset = buffer.getInt();
            int length = buffer.remaining();
            byte[] block = new byte[length];
            buffer.get(block);
            long filePosition = index*pieceSize+offset;
            localFile.seek(filePosition);
            localFile.write(block);
            //Piece piece = notDownloadedPieces.get(index);
            Piece piece = blockManager.getPiece(index);
            piece.addLoadedPart(offset /partSize, block);//на всякий случай
            if(piece.checkIsCompletedPiece()){
                ByteBuffer buff =piece.getPieceFile();
                buff.flip();
                byte[] array= new byte[piece.getSize()];
                buff.get(array);
               //if(metadata.compareSHAHashWithTorrent(index,piece.getPieceFile())){
                if(metadata.compareSHAHashWithTorrent(index,array)){
                   downloadedPieces.set(index);
                   //notDownloadedPieces.remove(index);
                    blockManager.removePiece(index);
                   sendHaveMessage(index);
                   return Id.END_CONNECT;
               }
               piece.clearPiece();
               askAllPeer();
               return Id.END_CONNECT;
            }
            return Id.INTERESTED;
        }catch (IOException e) {return Id.END_CONNECT;}
    }

    public Id handleHave(ByteBuffer buffer,SocketChannel channel){
        buffer.getInt();
        buffer.get();
        int index =buffer.getInt();
        List<Peer> peers = torrentPeers.getPeers();
        int countPeer = torrentPeers.getCountPeers();
        for(int i=0;i<countPeer;i++) {
            Peer peer = peers.get(i);
            if(peer.getChannel() == channel){
                peer.setLoadedPiece(index);
                if(hasNeededPieces(peer.getBitfield())){
                    return Id.INTERESTED;
                }
            }
        }
        return Id.END_CONNECT;
    }

    public void sendMessage(Id messageId, SocketChannel channel,ByteBuffer buffer) {
        logger.trace("send message!!!!!111");
        try{
            switch(messageId){
                case Id.END_CONNECT:
                    logger.trace("end connect");
                    break;
                case Id.CHOKE:
                    logger.trace("send choke");
                    channel.write(handler.getChocke());
                    break;
                case Id.UNCHOKE:
                    logger.trace("send unchoke");
                    channel.write(handler.getUnchocke());
                    break;
                case Id.INTERESTED:
                    logger.trace("send interested");
                    channel.write(handler.getInterested());
                    break;
                case Id.NOT_INTERESTED:
                    logger.trace(" send not interested");
                    channel.write(handler.getNotInterested());
                    break;
                case Id.HAVE:
                    break;
                case Id.BITFIELD: channel.write(createBitfield());
                case Id.REQUEST: channel.write(requestPiece(channel));
                    break;
                case Id.PIECE: channel.write(sendPiece(buffer));
                    break;
            }
        } catch (IOException e) {
            logger.trace("IOEexception "+ e.getMessage());
        }
    }

    public synchronized Id handleHandshake(ByteBuffer buffer,SocketChannel channel,int peerPort){
        String peerID = handler.bytesToHex(torrentPeers.getPeerID(peerPort));
        HandShake handshakeState = peerHandshakes.get(peerID);
        logger.debug("Handshake state2: " + handshakeState);
        switch (handshakeState){
            case HandShake.COMPLETE_HANDSHAKE:
                logger.debug("complete handshake with: " + peerPort);
                return Id.NOT_HANDSHAKE;
            case HandShake.INITIAL_HANDSHAKE:
                logger.debug("initial handshake with: " + peerPort);
                if(!handler.isCorrectHandshake(buffer,metadata.getInfoHash())){
                    logger.debug("is not correct handshake!");
                    return Id.END_CONNECT;
                }
                peerHandshakes.put(peerID,HandShake.COMPLETE_HANDSHAKE);
                logger.debug("is correct handshake!");
                try{
                    logger.trace("write handshake");
                    channel.write(createHandshake((InetSocketAddress)channel.getRemoteAddress()));
                } catch (IOException e) {
                    logger.trace("exception " + e.getMessage());
                }
                return Id.BITFIELD;
            case HandShake.WAIT_HANDSHAKE:
                logger.debug("I wait handshake with: " + peerPort);
                if(handler.isCorrectHandshake(buffer,metadata.getInfoHash())){
                    peerHandshakes.put(peerID,HandShake.COMPLETE_HANDSHAKE);
                    logger.debug("is correct handshake!");
                    return Id.END_CONNECT;
                }
                logger.debug("is not correct handshake!");
                return Id.END_CONNECT;
            default: return Id.END_CONNECT;
        }
    }

    public Id handlePeerMessage(ByteBuffer buffer,SocketChannel channel) {
        logger.trace("handle message!!!!!111");
        int peerPort;
        try{
            InetSocketAddress peerAddress = (InetSocketAddress) channel.getRemoteAddress();
            peerPort = peerAddress.getPort();
            logger.trace("peer port"+ peerPort);
        } catch (IOException e) {
            logger.trace("address exception" + e.getMessage());
            return Id.END_CONNECT;}
        logger.trace("position:" + buffer.position());
        String peerID = handler.bytesToHex(torrentPeers.getPeerID(peerPort));
        HandShake handshakeState = peerHandshakes.get(peerID);
        logger.debug("Handshake state: " + handshakeState);
        if(handshakeState != HandShake.COMPLETE_HANDSHAKE){
            switch(handleHandshake(buffer,channel,peerPort)){
                case Id.NOT_HANDSHAKE: break;
                case Id.BITFIELD: return Id.BITFIELD;
                default: return Id.END_CONNECT;
            }
        }
        logger.trace("position before handling:" + buffer.position());
        logger.trace("size before handling:" + buffer.limit());
        int id = buffer.get(MESSAGE_ID_INDEX);
        logger.trace("id message:" + id);
        Id messageId = Id.IntToId(id);
        switch (messageId) {
            case Id.CHOKE: return Id.END_CONNECT;
            case Id.UNCHOKE: return Id.REQUEST;
            case Id.INTERESTED: return Id.UNCHOKE;
            case Id.NOT_INTERESTED: return Id.CHOKE;
            case Id.HAVE: return handleHave(buffer,channel);
            case Id.BITFIELD: return handleBitfield(buffer,channel);
            case Id.REQUEST: return handleRequest(buffer);
            case Id.PIECE: return handlePiece(buffer);
            default: return Id.END_CONNECT;
        }
    }
}
