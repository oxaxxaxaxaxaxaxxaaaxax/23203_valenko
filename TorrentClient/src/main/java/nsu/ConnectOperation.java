package nsu;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    END_CONNECT(8);

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

public class ConnectOperation {
    private RandomAccessFile localFile;
    private final int lengthName = 19;
    private final int lengthMessage = lengthName + 49;
    private final TorrentPeers torrentPeers;
    private BitSet downloadedPieces;
    //private BitSet neededPieces;////?????
    private Map<Integer, Piece> notDownloadedPieces = new ConcurrentHashMap<>();
    private final int countPieces;
    private final int partSize = 16* 1024;
    private final long pieceSize;
    TorrentData metadata;

    ConnectOperation(TorrentPeers torrentPeers, TorrentData metadata) throws IOException{
        this.torrentPeers = torrentPeers;
        this.metadata = metadata;
        downloadedPieces = metadata.getDownloadedBytes();
        localFile = new RandomAccessFile(metadata.getFile(),"rw");
        countPieces = downloadedPieces.size();
        pieceSize = metadata.getPieceSize();
        setNeedPieces(downloadedPieces,(int)(pieceSize+partSize - 1)/partSize,notDownloadedPieces);//должно делиться нацело
    }

    public void setNeedPieces(BitSet downloadedPieces,int countParts, Map<Integer, Piece> pieces){
        for(int i=0;i< countPieces;i++){
            if(!downloadedPieces.get(i)){
                pieces.put(i,new Piece(countParts,i));
            }
        }
    }

    public ByteBuffer getHandshake(byte[] infoHash, InetSocketAddress peerAddres) {
        ByteBuffer handShake = ByteBuffer.allocate(lengthMessage);
        byte firstByte = (byte) lengthName;
        handShake.put(firstByte);
        String name = "BitTorrent protocol";
        handShake.put(name.getBytes());
        for (int i = 0; i < 8; i++) {
            handShake.put((byte) 0);
        }
        handShake.put(infoHash);
        byte[] peerId = torrentPeers.getPeerID(peerAddres.getPort());
        handShake.put(peerId);
        return handShake;
    }


    public byte[] BitToByte(BitSet bitset) {
        int bitLength = bitset.length();
        int length = (bitLength + 7) / 8;
        byte[] bytes = new byte[length];
        for (int i = 0; i < bitLength; i++) {
            if (bitset.get(i)) {
                bytes[i / 8] |= (byte) 1 << (7 - i % 8);
            }
        }
        return bytes;
    }

    public BitSet ByteToBit(byte[] buffer, int countPieces) {
        BitSet bitset = new BitSet(countPieces);
        for (int i = 0; i < countPieces; i++) {
            if ((buffer[i / 8] & (byte) (1 << (7 - i % 8))) != 0) {
                bitset.set(i);
            }
        }
        return bitset;
    }

    public ByteBuffer getBitfield() {
        byte[] bitfieldsByte = BitToByte(downloadedPieces);
        ByteBuffer buff = ByteBuffer.allocate(4 + bitfieldsByte.length + 1);
        buff.putInt(bitfieldsByte.length + 1);
        buff.put((byte) 5);
        buff.put(bitfieldsByte);
        return buff;
    }

    public ByteBuffer getChocke() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 0);
        return buff;
    }

    public ByteBuffer getUnchocke() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 1);
        return buff;
    }

    public ByteBuffer getInterested() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 2);
        return buff;
    }

    public ByteBuffer getNotInterested() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 3);
        return buff;
    }

    public ByteBuffer getRequest(int idx, int offset, int blockLength) {
        ByteBuffer buff = ByteBuffer.allocate(13 + 4);
        buff.putInt(13);
        buff.put((byte) 6);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.putInt(blockLength);
        return buff;
    }

    public ByteBuffer getPiece(int idx, int offset, byte[] data) {
        ByteBuffer buff = ByteBuffer.allocate(9 + data.length);
        buff.putInt(data.length + 1);
        buff.put((byte) 7);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.put(data);
        return buff;
    }

    public ByteBuffer getHave(int idx) {
        ByteBuffer buff = ByteBuffer.allocate(9);
        buff.putInt(5);
        buff.put((byte) 4);
        buff.putInt(idx);
        return buff;
    }

    public boolean hasNeededPieces(BitSet peerPieces) {
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
                if(peer.getPort() == peerPort){
                    peer.setPeerBitfield(peerPieces);
                }
            }
        }catch (IOException e){}
    }

    public Id handleBitfield(ByteBuffer buffer, SocketChannel channel) {
        int length = buffer.getInt();
        buffer.get();
        byte[] buff = new byte[length - 1];
        buffer.get(buff);
        BitSet peerPieces = ByteToBit(buff, countPieces);
        setPeerBitfield(peerPieces,channel);
        if (hasNeededPieces(peerPieces)) {
            return Id.INTERESTED;
        }
        return Id.NOT_INTERESTED;
    }

    public int getNotDownloadedPart(Map<Integer,Piece> pieces, int idx){
        Piece piece = pieces.get(idx);
        return piece.getFreePart();
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
                int offset = getNotDownloadedPart(notDownloadedPieces,pieceIndex);//с учетом байтового смещения
                return getRequest(pieceIndex,offset,partSize);
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
            return getPiece(index,offset,sendData);
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
                    peerChannel.write(getInterested());
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
                peerChannel.write(getHave(index));
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
            Piece piece = notDownloadedPieces.get(index);
            piece.addLoadedPart(offset /partSize, block);//на всякий случай
            if(piece.checkIsCompletedPiece()){
               if(metadata.compareSHAHashWithTorrent(index,piece.getPieceFile())){
                   downloadedPieces.set(index);
                   notDownloadedPieces.remove(index);
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
        try{
            switch(messageId){
                case Id.CHOKE:
                case Id.UNCHOKE: channel.write(getUnchocke());
                    break;
                case Id.INTERESTED: channel.write(getInterested());
                    break;
                case Id.NOT_INTERESTED:
                case Id.HAVE:
                    break;
                case Id.BITFIELD:
                case Id.REQUEST: channel.write(requestPiece(channel));
                    break;
                case Id.PIECE: channel.write(sendPiece(buffer));
                    break;
                default:
            }
        } catch (IOException e) {}

    }

    public Id handlePeerMessage(ByteBuffer buffer,SocketChannel channel) {
        //может проверк на то что это не хэндшейк....
        byte id = buffer.get(5);
//        buffer.getInt();
//        byte id = buffer.get();//настроить
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
