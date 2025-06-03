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
    private final Logger logger = LogManager.getLogger(Handler.class);

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
        if(buffer.remaining() != 68){
            return false;
        }
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
}
