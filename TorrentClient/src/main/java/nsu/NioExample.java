package nsu;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioExample {
    private static final int PORT = 8080;
    private static final String MESSAGE = "Hello from client!";

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[0].equals("server")) {
            startServer();
        } else {
            startClient();
        }
    }

    private static void startServer() throws IOException {
        System.out.println("Запуск сервера...");
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Новое соединение от " + client.getRemoteAddress());
                }

                if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    buffer.clear(); // position=0, limit=1024
                    int read = client.read(buffer); // читаем в буфер

                    if (read > 0) {
                        buffer.flip(); // position=0, limit=read
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data); // копируем данные
                        System.out.println("Сервер получил: " + new String(data).trim());

                        printBufferState("После получения", buffer);

                        // Отправляем ответ
                        buffer.clear();
                        buffer.put("Hello from server!".getBytes());
                        buffer.flip(); // готовим к отправке
                        printBufferState("Перед отправкой", buffer);
                        client.write(buffer);
                    }
                }

                keys.remove();
            }
        }
    }

    private static void startClient() throws IOException {
        System.out.println("Запуск клиента...");

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("localhost", PORT));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // Отправляем сообщение
        buffer.put(MESSAGE.getBytes());
        printBufferState("После put()", buffer);

        buffer.flip(); // position=0, limit=MESSAGE.length
        printBufferState("После flip()", buffer);
        channel.write(buffer);

        // Получаем ответ
        buffer.clear();
        printBufferState("После clear()", buffer);

        int read = channel.read(buffer);
        if (read > 0) {
            buffer.flip(); // position=0, limit=read
            printBufferState("После flip()", buffer);

            byte[] response = new byte[buffer.remaining()];
            buffer.get(response);
            System.out.println("Клиент получил: " + new String(response).trim());
        }

        channel.close();
    }

    private static void printBufferState(String step, ByteBuffer buffer) {
        System.out.printf("[%s] pos=%d, lim=%d, cap=%d%n",
                step,
                buffer.position(),
                buffer.limit(),
                buffer.capacity()
        );
    }
}
