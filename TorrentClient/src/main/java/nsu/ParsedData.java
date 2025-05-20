package nsu;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

public class ParsedData {
    private int length;
    private String name;
    private int pieceLength;
    private byte[] infoHash;//
    private List<byte[]> piecesHashes;//
    private byte[] pieces;
    int countPeers = 3;

    File purposeFile;
    BitSet piecesCard;
    ParsedData(){
    }
    public void initCard(){
       piecesCard = new BitSet(length*8);
    }
    public byte[] getSHAHash(ByteBuffer message) throws  java.security.NoSuchAlgorithmException{
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update(message);
        return sha1.digest();
    }



    public void createPartCard(int pieceIdx, int begin) throws IOException, java.security.NoSuchAlgorithmException{
//        ByteBuffer downloadedBytes = ByteBuffer.allocate(length);
        try(RandomAccessFile file = new RandomAccessFile(purposeFile, "r")){
            FileChannel chanel = file.getChannel();
            ByteBuffer downloadedBytes = ByteBuffer.allocate(pieceLength);//надо придумать какую записывать за раз
            long offset = begin + (long)pieceIdx * pieceLength;
            chanel.read(downloadedBytes,offset);
            byte[] pieceHash = getSHAHash(downloadedBytes);
            int counter = 0;
            for(int i = 0;i<20;i++){
                if(pieceHash[i] != pieces[i]){
                    break;
                }
                counter++;
            }
            if(counter ==20){
                piecesCard.set(pieceIdx);
            }
        }
    }

    public void createCard() throws IOException, java.security.NoSuchAlgorithmException{
        int countPieces = length/pieceLength;
        for(int i=0;i<countPieces;i++){
            createPartCard(i,0);
        }
    }

    public void PeerIDGen(int count, Peer[] peers){
        Random random = new Random();
        for(int i=0;i<count;i++){
            byte[] peerId = new byte[20];
            random.nextBytes(peerId);
            peers[i].setId(peerId);
        }
    }

}
