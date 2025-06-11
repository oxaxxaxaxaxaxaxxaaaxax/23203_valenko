package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;

public class Handler {
    private final int lengthProtocol = 19;
    private final int lengthMessage = lengthProtocol + 49;
    private final String nameProtocol = "BitTorrent protocol";
    //int countPieces;
    private final Logger logger = LogManager.getLogger(Handler.class);

    public byte[] BitToByte(BitSet bitset) {
        logger.trace("in bit to byte");
        int bitLength = bitset.length();
        logger.trace("length bit: "+bitLength);
        int length = (bitLength + 7) / 8;
        logger.trace("length byte: "+length);
        byte[] bytes = new byte[length];
        for (int i = 0; i < bitLength; i++) {
            if (bitset.get(i)) {
                logger.trace("i 1: " + i);
                bytes[i / 8] |= (byte) 1 << (7 - i % 8);
            }
        }
        return bytes;
    }

    public BitSet ByteToBit(byte[] buff, int countPieces) {
        logger.trace("in byte to bit");
//        logger.trace("countPieces "+countPieces);
//        if (countPieces < 0 || countPieces > buff.length * 8) {
//            logger.trace("buffer length "+buff.length * 8);
//            logger.trace("failed limit");
//        }
        int lengthMessage = buff.length;
        logger.trace("lengthMessage "+lengthMessage);
        if(buff.length * 8 < countPieces){
            lengthMessage+= ((countPieces+8-1)/8) - buff.length;
            logger.trace("lengthMessage2 "+lengthMessage);
        }
        byte[] buffer = new byte[lengthMessage];
        System.arraycopy(buff,0,buffer,0,buff.length);
        BitSet bitset = new BitSet(countPieces);
        try{
            for (int i = 0; i < countPieces; i++){
                if ((buffer[i / 8] & (byte) (1 << (7 - i % 8))) != 0) {
                    logger.trace("i: "+i);
                    bitset.set(i);
                }
                logger.trace("i: "+i);
            }
        }catch(Exception e){
            logger.trace("exception "+e.getMessage());
        }
        logger.trace("end byte to bit");
        return bitset;
    }

//    public void setCountPieces(int countPieces){
//        this.countPieces = countPieces;
//    }

    public byte[] getSHAHashForPort(int port){
        return getSHAHash(ByteBuffer.wrap(String.valueOf(port).getBytes()));
    }

    public byte[] getSHAHash(ByteBuffer message){
        try{
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(message);
            return sha1.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.trace("exception:"+ e.getMessage());
            return null;
        }
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean isCorrectHandshake(ByteBuffer buffer, byte[] infoHash){
        logger.debug("check correct handshake");
        logger.trace(buffer);
//        if(buffer.remaining() != 68){
//            return false;
//        }
        byte length = buffer.get();
        if(length != lengthProtocol){
            return false;
        }
        byte[] protocolByte = new byte[lengthProtocol];
        buffer.get(protocolByte);
        String protocolString = new String(protocolByte);
        if(!nameProtocol.equals(protocolString)){
            return false;
        }
        buffer.position(buffer.position() +8);
        byte[] localInfoHash = new byte[20];
        buffer.get(localInfoHash);
        if(!Arrays.equals(infoHash,localInfoHash)){
            return false;
        }
        byte[] peerID = new byte[20];
        buffer.get(peerID);
        logger.trace("remaining "+buffer.remaining());
        return true;
    }

    public static String bufferToString(ByteBuffer buffer) {
        ByteBuffer duplicate = buffer.duplicate();
        duplicate.flip();
        return StandardCharsets.UTF_8.decode(duplicate).toString();
    }

    public ByteBuffer getHandshake(byte[] infoHash, InetSocketAddress peerAddres, TorrentPeers torrentPeers) {
        ByteBuffer handShake = ByteBuffer.allocate(lengthMessage);
        byte firstByte = (byte) lengthProtocol;
        handShake.put(firstByte);
        String name = nameProtocol;
        handShake.put(name.getBytes());
        for (int i = 0; i < 8; i++) {
            handShake.put((byte) 0);
        }
        handShake.put(infoHash);
        logger.trace("remote address: "+ peerAddres);
        byte[] peerId = torrentPeers.getPeerID(peerAddres.getPort());//
        handShake.put(peerId);
        logger.trace("message: "  + bufferToString(handShake));
        logger.trace("position: "+ handShake.position());
        handShake.flip();
        logger.trace("position: "+ handShake.position());
        return handShake;
    }

    public ByteBuffer getBitfield(BitSet downloadedPieces) {
        byte[] bitfieldsByte = BitToByte(downloadedPieces);
        logger.trace("length bytes:" + bitfieldsByte.length);
        ByteBuffer buff = ByteBuffer.allocate(4 + bitfieldsByte.length + 1);
        buff.putInt(bitfieldsByte.length + 1);
        buff.put((byte) 5);
        buff.put(bitfieldsByte);
        logger.trace("length : "+buff.capacity());
        logger.trace("position " + buff.position());
        buff.flip();
        logger.trace("position " + buff.position());
        return buff;
    }

    public ByteBuffer getChocke() {
        logger.trace("create choke message");
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 0);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getUnchocke() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 1);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getInterested() {
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 2);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getNotInterested() {
        logger.trace("create not interested message");
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte) 3);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getRequest(int idx, int offset, int blockLength) {
        logger.trace("create request message");
        ByteBuffer buff = ByteBuffer.allocate(13 + 4);
        buff.putInt(13);
        buff.put((byte) 6);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.putInt(blockLength);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getPiece(int idx, int offset, byte[] data) {
        logger.trace("create piece message");
        logger.trace("data.length: "+data.length);
        ByteBuffer buff = ByteBuffer.allocate(13 + data.length);
        buff.putInt(data.length + 9);
        buff.put((byte) 7);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.put(data);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }

    public ByteBuffer getHave(int idx) {
        logger.trace("create have message");
        ByteBuffer buff = ByteBuffer.allocate(9);
        buff.putInt(5);
        buff.put((byte) 4);
        buff.putInt(idx);
        logger.trace("position:" + buff.position());
        buff.flip();
        logger.trace("position:" + buff.position());
        return buff;
    }
}
