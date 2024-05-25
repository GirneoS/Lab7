package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientHandler implements Runnable{
    private final SocketAddress clientAddress;

    public ClientHandler(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    public void run() {
        try(DatagramChannel channel = DatagramChannel.open()){
            ExecutableCommand command = Server.getQueueOfCommands().poll();
            
            //обработка запроса
            String result = command.execute(command.getUserName(), command.getPassword());

            byte[] bytesOfResult = Serialization.SerializeObject(result);
            ByteBuffer buffer = ByteBuffer.wrap(bytesOfResult);
            
            //оправка ответа
            channel.send(buffer, clientAddress);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
