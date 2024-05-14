package org.example.server;

import org.example.controller.AuthenticationForm;
import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;
import org.example.models.DataBaseHandler;
import org.example.models.RequestDTO;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerNetController {

    private static final Logger logger = Logger.getLogger("Laba7");
    private static boolean connection = false;
    public static boolean authenticateUser() {
        boolean result = false;
        try(DatagramChannel channel = DatagramChannel.open()){
            channel.bind(new InetSocketAddress(8187));
            byte[] bytesOfUser = new byte[1024];

            ByteBuffer buffer = ByteBuffer.wrap(bytesOfUser);
            SocketAddress address = channel.receive(buffer);

            AuthenticationForm authentication = Serialization.DeserializeObject(bytesOfUser);
            String form = authentication.getForm();
            String userName = authentication.getUserName();
            String password = authentication.getPassword();

            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.connectToDataBase();

            if(form.equals("login"))
                result = dbHandler.authorization(userName,password);
            else
                result = dbHandler.registration(userName,password);

            byte[] bytesOfStatus = Serialization.SerializeObject(result);
            SendResponse(bytesOfStatus,address);

        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static void SendResponse(byte[] arr, SocketAddress address) {
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
            System.out.println("запрос получен");

            if(!connection){
                logger.info("Client has connected to server\n-------------------------------------------------");
                connection = true;
            }

            RequestDTO dto = Serialization.DeserializeObject(bytesOfRequest);

            logger.info("Request has been received"+"\n-------------------------------------------------");

            ExecutableCommand command = dto.getCommand();

            String resultOfCommand = command.execute(dto.getUserName(), dto.getPassword());
            System.out.println(resultOfCommand);
            byte[] response = Serialization.SerializeObject(resultOfCommand);
            SendResponse(response,address);
        }

    }

}
