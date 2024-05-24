package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class SMTH implements Runnable{
    private final SocketAddress address;
    private DatagramChannel channel;
    private final byte[] bytesOfRequest;

    public SMTH(SocketAddress address, DatagramChannel channel, byte[] bytesOfRequest) {
        this.address = address;
        this.channel = channel;
        this.bytesOfRequest = bytesOfRequest;
    }

    @Override
    public void run() {
        try {
            ForkJoinPool pool = new ForkJoinPool();

            ExecutableCommand command = Serialization.DeserializeObject(bytesOfRequest);
            pool.execute(new CommandExecutor(address, command));
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}
