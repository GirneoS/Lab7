package org.example.server;

import org.example.controller.Serialization;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveAction;

public class ResponseHandler extends RecursiveAction {
    private final SocketAddress clientAddress;
    private final String result;

    public ResponseHandler(SocketAddress clientAddress, String result) {
        this.clientAddress = clientAddress;
        this.result = result;
    }

    @Override
    protected void compute() {
        try(DatagramChannel channel = DatagramChannel.open()){
            byte[] bytesOfResult = Serialization.SerializeObject(result);

            ByteBuffer buffer = ByteBuffer.wrap(bytesOfResult);
            channel.send(buffer, clientAddress);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
