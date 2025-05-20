package nsu;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class CheckPieces {
//    public byte[] getSHAHash(ByteBuffer message) throws  java.security.NoSuchAlgorithmException{
//        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
//        sha1.update(message);
//        return sha1.digest();
//    }

    private final int lengthName =19;
    private final int lengthMessage = lengthName+49;
    public ByteBuffer getHandshake(byte[] infoHash, byte[] peerId){
        ByteBuffer handShake = ByteBuffer.allocate(lengthMessage);
        byte firstByte = (byte)lengthName;
        handShake.put(firstByte);
        String name = "BitTorrent protocol";
        handShake.put(name.getBytes());
        for(int i=0;i<8;i++){
            handShake.put((byte)0);
        }
        handShake.put(infoHash);
        handShake.put(peerId);
        return handShake;
    }

    public ByteBuffer getBitfield(byte[] bitfieldsByte){
        ByteBuffer buff = ByteBuffer.allocate(4+ bitfieldsByte.length +1);
        buff.putInt(bitfieldsByte.length+1);
        buff.put((byte)5);
        buff.put(bitfieldsByte);
        return buff;
    }

    public ByteBuffer getChocked(){
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte)0);
        return buff;
    }

    public ByteBuffer getUnchocked(){
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte)1);
        return buff;
    }

    public ByteBuffer getInterested(){
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte)2);
        return buff;
    }

    public ByteBuffer getNotInterested(){
        ByteBuffer buff = ByteBuffer.allocate(5);
        buff.putInt(1);
        buff.put((byte)3);
        return buff;
    }

    public ByteBuffer getRequest(int idx, int offset, int blockLength){
        ByteBuffer buff = ByteBuffer.allocate(13+4);
        buff.putInt(13);
        buff.put((byte)6);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.putInt(blockLength);
        return buff;
    }

    public ByteBuffer getPiece(int idx, int offset, byte[] data){
        ByteBuffer buff = ByteBuffer.allocate(9 + data.length);
        buff.putInt(data.length+1);
        buff.put((byte)7);
        buff.putInt(idx);
        buff.putInt(offset);
        buff.put(data);
        return buff;
    }

    public ByteBuffer getHave(int idx){
        ByteBuffer buff = ByteBuffer.allocate(9);
        buff.putInt(5);
        buff.put((byte)4);
        buff.putInt(idx);
        return buff;
    }
}
