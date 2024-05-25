package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ForkJoinPool;

public class Executor implements Runnable{
    private final SocketAddress address;
    private DatagramChannel channel;
    private final byte[] bytesOfRequest;

    public Executor(SocketAddress address, DatagramChannel channel, byte[] bytesOfRequest) {
        this.address = address;
        this.channel = channel;
        this.bytesOfRequest = bytesOfRequest;
    }

    //Чтение запроса
    @Override
    public void run() {
        try {
            ForkJoinPool pool = new ForkJoinPool();

            ExecutableCommand command = Serialization.DeserializeObject(bytesOfRequest);
            String result = command.execute(command.getUserName(), command.getPassword());

            pool.execute(new ResponseHandler(address, result));
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}
