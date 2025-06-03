package nsu;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TorrentData {
    String torrentPathName;
    String localPathName = null;
    long length;
    String name;
    long pieceLength;
    byte[] pieces ;
    byte[] infoHash;
    BitSet piecesCard;
    File localFile;
    File torrentFile;
    private static final int SHAsize =20;
    private String downloadDir = "/home/oksana/Downloads";
    private final Logger logger = LogManager.getLogger(TorrentData.class);
    private final Handler handler = new Handler();

    TorrentData(String pathName, int peerCount){
        logger.trace("create a TorrentData class");
        torrentPathName = pathName;
        //genPeerAddress(peerCount,peers);
    }
    TorrentData(String torrentFilePath, String localFilePath, int peerCount){
        logger.trace("create a TorrentData class with localFile");
        torrentPathName = torrentFilePath;
        localPathName = localFilePath;
        localFile = new File(localPathName);
        //genPeerAddress(peerCount,peers);
    }

    public byte[] getInfoHash(){
        return infoHash;
    }
    public long getPieceSize(){return pieceLength;}
    public File getFile(){return localFile;}
    public long getLength(){return length;}

    public boolean compareSHAHashWithTorrent(int index, byte[] filePiece){
        byte[] torrentHash = new byte[20];
        System.arraycopy(pieces,index*SHAsize,torrentHash,0,SHAsize);
        try{
            byte[] localHash = handler.getSHAHash(ByteBuffer.wrap(filePiece));
            return Arrays.equals(torrentHash,localHash);
        }catch(Exception e){}
        return false;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }
    public void parseTorrentFile()  throws IOException,java.security.NoSuchAlgorithmException {
        System.out.println("parse");
        logger.trace("start parsing torrent");
        Bencode bencode = new Bencode(StandardCharsets.UTF_8);//парсим как байтмассив а не строку
        torrentFile = new File(torrentPathName);
        byte[] dataArray = new byte[(int)torrentFile.length()];
        try(FileInputStream input = new FileInputStream(torrentFile)){
            input.read(dataArray);
        }
        Map<String, Object> torrentData = (Map<String, Object>)bencode.decode(dataArray, Type.DICTIONARY);
        @SuppressWarnings("unchecked")
        Map<String, Object> info = (Map<String, Object>)torrentData.get("info");
        length = (Long)info.get("length");
        name = (String)info.get("name");
        pieceLength = (Long)info.get("piece length");
        String strPiece = (String)info.get("pieces");
        pieces = strPiece.getBytes(StandardCharsets.UTF_8);
        infoHash = handler.getSHAHash(ByteBuffer.wrap(bencode.encode(info)));
        logger.trace("length " + length);
        logger.trace("name "+ name);
        logger.trace("pieceLength "+ pieceLength);
        logger.trace("infoHash "+ bytesToHex(infoHash));
    }

    public long getCountPieces(){return length/pieceLength;} //возможно оно нацело не делится

    public void createPartCard(int pieceIdx, int begin) {
        try(RandomAccessFile file = new RandomAccessFile(localFile, "r")){
            FileChannel chanel = file.getChannel();
            ByteBuffer downloadedBytes = ByteBuffer.allocate((int)pieceLength);//надо придумать какую записывать за раз
            long offset = begin + (long)pieceIdx * pieceLength;
            chanel.read(downloadedBytes,offset);
            byte[] pieceHash = handler.getSHAHash(downloadedBytes);
            int counter = 0;
            for(int i = 0;i<20;i++){
                if(pieceHash[i] != pieces[pieceIdx*(int)pieceLength + i]){
                    return;
                }
            }
            piecesCard.set(pieceIdx);
        }catch(IOException e){
            logger.trace(e.getMessage());
        }
    }

    public void createCard() throws IOException, java.security.NoSuchAlgorithmException{
        logger.trace("create card pieces");
        piecesCard = new BitSet((int)length*8);
        if(localPathName == null){
            localPathName = downloadDir + "/" + name;
            localFile = new File(localPathName);
            logger.trace("create a file: " + localPathName);
            return;
        }
        long countPieces = length/pieceLength;
        for(int i=0;i<countPieces;i++){
            createPartCard(i,0);
        }
    }

    public BitSet getDownloadedBytes(){
        return piecesCard;
    }
}
