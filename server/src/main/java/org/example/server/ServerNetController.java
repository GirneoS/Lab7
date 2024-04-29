package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class ServerNetController {

    private static final Logger logger = Logger.getLogger("Laba6");
    private static boolean connection = false;
    public static void SendResponse(byte[] arr, SocketAddress address) throws SocketException {
//        try(DatagramSocket socket = new DatagramSocket()){
//
//            DatagramPacket packet = new DatagramPacket(arr, arr.length, address);
//
//            socket.send(packet);
//            logger.info("Response just has been sent\n-------------------------------------------------\n\n");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try(DatagramChannel channel = DatagramChannel.open()){
            ByteBuffer buffer = ByteBuffer.wrap(arr);

            channel.send(buffer,address);
            logger.info("Response just has been sent\n-------------------------------------------------\n\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void GetRequestAndSendResponse() throws IOException, ClassNotFoundException {

        try(DatagramChannel channel = DatagramChannel.open()){
            channel.bind(new InetSocketAddress(8187));
            byte[] bytesOfRequest = new byte[2048];

            ByteBuffer buffer = ByteBuffer.wrap(bytesOfRequest);
            SocketAddress address = channel.receive(buffer);
            System.out.println("ответ получен");

            if(!connection){
                logger.info("Client has connected to server\n-------------------------------------------------");
                connection = true;

            }

            ExecutableCommand command = Serialization.DeserializeObject(bytesOfRequest);

            logger.info("Request has been received - "+command.getClass()+"\n-------------------------------------------------");

            String resultOfCommand = command.execute();
            System.out.println(resultOfCommand);
            byte[] response = Serialization.SerializeObject(resultOfCommand);
            SendResponse(response,address);
        }

    }

}
