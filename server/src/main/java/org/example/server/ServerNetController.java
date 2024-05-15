package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class ServerNetController {

    private static final Logger logger = Logger.getLogger("Laba7");
    private static boolean connection = false;
    public static void SendResponse(byte[] arr, SocketAddress address) {
        try(DatagramChannel channel = DatagramChannel.open()){
            ByteBuffer buffer = ByteBuffer.wrap(arr);

            channel.send(buffer,address);
            logger.info("Response just has been sent\n-------------------------------------------------\n\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean GetRequestAndSendResponse() {

        try(DatagramChannel channel = DatagramChannel.open()){
            channel.bind(new InetSocketAddress(8188));
            byte[] bytesOfRequest = new byte[2048];

            ByteBuffer buffer = ByteBuffer.wrap(bytesOfRequest);
            SocketAddress address = channel.receive(buffer);
            System.out.println("запрос получен");

            if(!connection){
                logger.info("Client has connected to server\n-------------------------------------------------");
                connection = true;
            }

            ExecutableCommand command = Serialization.DeserializeObject(bytesOfRequest);

            logger.info("Request has been received"+"\n-------------------------------------------------");

            if(command.getType().equals("login") || command.getType().equals("register")){
                return true;
            }

            String resultOfCommand = command.execute(command.getUserName(), command.getPassword());
            System.out.println(resultOfCommand);

            byte[] response = Serialization.SerializeObject(resultOfCommand);
            SendResponse(response,address);

        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
        return false;
    }

}
