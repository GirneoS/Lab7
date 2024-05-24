package org.example.server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveAction;

public class ResponseHandler extends RecursiveAction {
    private final SocketAddress clientAddress;
    private final byte[] resultOfCommand;

    public ResponseHandler(SocketAddress clientAddress, byte[] resultOfCommand) {
        this.clientAddress = clientAddress;
        this.resultOfCommand = resultOfCommand;
    }

    @Override
    protected void compute() {
        try(DatagramChannel channel = DatagramChannel.open()){

            ByteBuffer buffer = ByteBuffer.wrap(resultOfCommand);
            channel.send(buffer, clientAddress);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
