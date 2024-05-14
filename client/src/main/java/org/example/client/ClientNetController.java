package org.example.client;

import org.example.controller.Serialization;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientNetController {
    public static boolean Authenticate(byte[] arr) throws IOException {
        boolean result = false;

        try(DatagramChannel channel = DatagramChannel.open()){
            SocketAddress myAddress = new InetSocketAddress("127.0.0.1",8286);
            channel.bind(myAddress);

            SocketAddress address = new InetSocketAddress("127.0.0.1", 8187);

            ByteBuffer buffer = ByteBuffer.wrap(arr);
            channel.getLocalAddress();
            channel.send(buffer, address);

            buffer.clear();
            buffer.flip();
            byte[] responseOfAuthentication = new byte[256];
            buffer = ByteBuffer.wrap(responseOfAuthentication);

            channel.receive(buffer);
            result = Serialization.DeserializeObject(responseOfAuthentication);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static void SendRequest(byte[] arr) throws IOException {

        try(DatagramChannel channel = DatagramChannel.open()){
            SocketAddress myAddress = new InetSocketAddress("127.0.0.1",8286);
            channel.bind(myAddress);

            SocketAddress address = new InetSocketAddress("127.0.0.1", 8187);

            ByteBuffer buffer = ByteBuffer.wrap(arr);
            channel.getLocalAddress();
            channel.send(buffer, address);

        }

    }

    public static void GetResponse() {

        try(DatagramChannel channel = DatagramChannel.open()){
            SocketAddress myAddress = new InetSocketAddress("127.0.0.1",8286);
            channel.bind(myAddress);

            byte[] bytesOfResponse = new byte[2048];
            ByteBuffer buffer = ByteBuffer.wrap(bytesOfResponse);
            channel.receive(buffer);

            String text_response = Serialization.DeserializeObject(bytesOfResponse);
            if(text_response.trim().equals("exit")){
                System.exit(0);
            }else{
                System.out.println(text_response);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}