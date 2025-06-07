package nsu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionContext {
    SocketChannel channel;
    ByteBuffer readBuffer;
    Queue<ByteBuffer> writeQueue = new ConcurrentLinkedQueue<>();
    int connectionCounter =0;
    private final Logger logger = LogManager.getLogger(ConnectionContext.class);
    public ConnectionContext(SocketChannel ch, int bufferSize) {
        this.channel = ch;
        this.readBuffer = ByteBuffer.allocate(bufferSize);
    }

    public int getConnectionCounter(){
        return connectionCounter;
    }

    public void incrConnectionCounter(){
        connectionCounter++;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public Queue<ByteBuffer> getWriteQueue() {
        return writeQueue;
    }
    public void addToQueue(ByteBuffer buf, Selector selector) {
        logger.trace("selector2 "+ selector);
        logger.trace("add a queue");
        writeQueue.add(buf);
        SelectionKey key = channel.keyFor(selector);
        if (key != null && key.isValid()) {
            logger.trace("key is valid");
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }
}
