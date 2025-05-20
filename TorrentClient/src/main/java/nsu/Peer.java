package nsu;

public class Peer {
    private byte[] id;
    private int port;
    private String ip;
    Peer(String ip,int port){
        this.port = port;
        this.ip = ip;
    }
    public void setId(byte[] id){
        this.id = id;
    }
}
