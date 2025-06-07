package nsu;

//import com.dampcake.bencode.Bencode;
//import com.dampcake.bencode.BencodeInputStream;
//import com.dampcake.bencode.Type;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.bcodec.BEValue;
import com.turn.ttorrent.common.Torrent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TorrentData {
    String torrentPathName;
    String localPathName = null;
    long length;
    String name;
    //String filePathName = "COPY123dadushka_au2";
    //String extension = ".avi";
    String filePathName = "image";
    String extension = "jpeg";
    long pieceLength;
    byte[] pieces;
    byte[] infoHash;
    long countPieces;
    BitSet piecesCard;
    File localFile;
    File torrentFile;
    private String numberClient;
    private static final int SHAsize = 20;
    private String downloadDir = "/home/oksana/Downloads";
    private final Logger logger = LogManager.getLogger(TorrentData.class);
    private final Handler handler = new Handler();

    TorrentData(String pathName, String numberClient) {
        logger.trace("create a TorrentData class");
        torrentPathName = pathName;
        this.numberClient = numberClient;
        //genPeerAddress(peerCount,peers);
    }

    TorrentData(String torrentFilePath, String localFilePath, String numberClient) {
        logger.trace("create a TorrentData class with localFile");
        torrentPathName = torrentFilePath;
        localPathName = localFilePath;
        localFile = new File(localPathName);
        this.numberClient = numberClient;
        //genPeerAddress(peerCount,peers);
    }

    public byte[] getInfoHash() {
        return infoHash;
    }

    public long getPieceSize() {
        return pieceLength;
    }

    public File getFile() {
        return localFile;
    }

    public long getLength() {
        return length;
    }

    public boolean compareSHAHashWithTorrent(int index, byte[] filePiece) {
        logger.trace("compare hash");
        byte[] torrentHash = new byte[20];
        System.arraycopy(pieces, index * SHAsize, torrentHash, 0, SHAsize);
        try {
            byte[] localHash = handler.getSHAHash(ByteBuffer.wrap(filePiece));
            return Arrays.equals(torrentHash, localHash);
        } catch (Exception e) {
        }
        return false;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }


    private List<byte[]> parsePieceHashes(Map<String, Object> info) {
        ByteBuffer piecesBuffer = (ByteBuffer) info.get("pieces");
        byte[] pieces = new byte[piecesBuffer.remaining()];
        piecesBuffer.get(pieces);

        List<byte[]> pieceHashes = new ArrayList<>();
        for (int i = 0; i < pieces.length; i += 20) {
            byte[] hash = new byte[20];
            System.arraycopy(pieces, i, hash, 0, 20);
            pieceHashes.add(hash);
        }

        return pieceHashes;
    }


    public void parseTorrentFile() throws IOException, java.security.NoSuchAlgorithmException {
        logger.trace("start parsing torrent");
        BDecoder decoder = new BDecoder(new FileInputStream(torrentPathName));
        BEValue decoded = decoder.bdecode();
        Map<String, BEValue> map = decoded.getMap();
        BEValue infoValue = map.get("info");
        Map<String, BEValue> infoMap = infoValue.getMap();
        pieceLength = infoMap.get("piece length").getLong();
        length = infoMap.get("length").getLong();
        BEValue piecesValue = infoMap.get("pieces");
        pieces = piecesValue.getBytes();
        torrentFile = new File(torrentPathName);
        byte[] dataArray = new byte[(int) torrentFile.length()];
        try (FileInputStream input = new FileInputStream(torrentFile)) {
            input.read(dataArray);
        }
        countPieces = length / pieceLength;
        Torrent torrent = Torrent.load(torrentFile);
        infoHash = torrent.getInfoHash();
        name = torrent.getName();
        logger.trace("length " + length);
        logger.trace("name " + name);
        logger.trace("pieceLength " + pieceLength);
        logger.trace("infoHash " + bytesToHex(infoHash));
        logger.trace("countPieces " + countPieces);
    }


    public long getCountPieces(){return countPieces;}


    public void createPartCard(int pieceIdx, int begin) {
        //logger.trace("create part card");
        //logger.trace("piece index" + pieceIdx);
        try(RandomAccessFile file = new RandomAccessFile(localFile, "r")){
            FileChannel chanel = file.getChannel();
            ByteBuffer downloadedPiece = ByteBuffer.allocate((int)pieceLength);//надо придумать какую записывать за раз
            long offset = begin + (long)pieceIdx * pieceLength;
            chanel.read(downloadedPiece,offset);
            //logger.trace("position "+downloadedPiece.position());
            downloadedPiece.flip();
            //logger.trace("position "+downloadedPiece.position());
            byte[] pieceHash = handler.getSHAHash(downloadedPiece);
            byte[] torrentPieceHash = new byte[20];
            System.arraycopy(pieces,pieceIdx*20, torrentPieceHash, 0,20);
            if(!Arrays.equals(pieceHash,torrentPieceHash)){
                logger.trace("not equals pieces!!!!");
                logger.trace("Expected hash: {}", HexFormat.of().formatHex(torrentPieceHash));
                logger.trace("Actual hash:   {}", HexFormat.of().formatHex(pieceHash));
                return;
            }
            //logger.trace("equals pieces!!!!");
            piecesCard.set(pieceIdx);
        }catch(IOException e){
            logger.trace("IOEexception!!!!" + e.getMessage());
        }
    }

    public void createCard() {
        logger.trace("create card pieces");
        logger.trace("create bitset1");
//        logger.trace(length);
//        logger.trace((int)length);
//        logger.trace(length*8);
//        logger.trace((int)length*8);
        logger.trace(countPieces);
        logger.trace((int)countPieces);
        logger.trace(countPieces*8);
        logger.trace((int)countPieces*8);
        piecesCard = new BitSet((int)countPieces);
        logger.trace("bitset is created");
        if(localPathName == null){
            localPathName = downloadDir + "/" + filePathName + numberClient + extension;
            localFile = new File(localPathName);
            logger.trace("create a file: " + localPathName);
            return;
        }
        for(int i=0;i<countPieces;i++){
            createPartCard(i,0);
        }
    }

    public BitSet getDownloadedBytes(){
        return piecesCard;
    }
}
