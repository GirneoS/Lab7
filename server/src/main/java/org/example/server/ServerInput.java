package org.example.server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerInput implements Runnable{
    private static final Logger logger = Logger.getLogger("Laba7");
    private DatagramChannel channel;

    public ServerInput() {
        try{
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(8188));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Чтение запроса
    public void Start() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            while(true) {
                byte[] bytesOfRequest = new byte[2048];
                ByteBuffer buffer = ByteBuffer.wrap(bytesOfRequest);

                SocketAddress address = channel.receive(buffer);
                logger.info("Request has been received\n-------------------------------------------------");

                threadPool.execute(new Executor(address, channel, bytesOfRequest));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
