package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;
import org.example.models.MainCollection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger("Laba7");
    private static final DatagramChannel channel;
    //потокобезопасная очередь для обработки запросов
    private static ConcurrentLinkedQueue<ExecutableCommand> queueOfCommands = new ConcurrentLinkedQueue<>();

    static{
        try {
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(8188));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        logger.info("The server has just start!\n-------------------------------------------------\n");
        MainCollection.initCollection();

        ExecutorService threadPool = Executors.newCachedThreadPool();
        while(true){
            try {
                byte[] bytesOfRequest = new byte[2048];
                ByteBuffer buffer = ByteBuffer.wrap(bytesOfRequest);

                SocketAddress address = channel.receive(buffer);
                logger.info("Request has been received\n-------------------------------------------------");
                //Чтение запроса
                threadPool.submit(() -> {
                    try {
                        ExecutableCommand command = Serialization.DeserializeObject(bytesOfRequest);
                        queueOfCommands.add(command);

                        ForkJoinPool pool = new ForkJoinPool();
                        //Обработка запроса и отправка ответа
                        pool.execute(new ClientHandler(address));
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static ConcurrentLinkedQueue<ExecutableCommand> getQueueOfCommands() {
        return queueOfCommands;
    }
}
