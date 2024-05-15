package org.example.server;


public class ServerController {
    public static void main(String[] args) {
        ServerInput input = ServerInput.makeConsoleInput();

        if(!input.isAlive())
            input.start();

        makeNewThreadServer();
    }

    public static void makeNewThreadServer(){
        Server server = new Server();
        server.start();
    }
}
