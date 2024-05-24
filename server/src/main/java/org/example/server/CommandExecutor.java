package org.example.server;

import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ForkJoinPool;

public class CommandExecutor implements Runnable{
    private SocketAddress clientAddress;
    private ExecutableCommand command;

    public CommandExecutor(SocketAddress clientAddress, ExecutableCommand command) {
        this.clientAddress = clientAddress;
        this.command = command;
    }

    //Исполняет команду, сериализует результат и отправляет его в CommandExecutor для отправки клиенту
    @Override
    public void run() {
        try {
            ForkJoinPool pool = new ForkJoinPool();

            String result = command.execute(command.getUserName(), command.getPassword());
            byte[] bytesOfResult = Serialization.SerializeObject(result);

            pool.execute(new ResponseHandler(clientAddress, bytesOfResult)); //сюда добавляем код, который будет вызывать отправку ответа
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
