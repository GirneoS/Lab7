package org.example.server;


import java.io.IOException;
import java.util.logging.Logger;

public class InputServer{
    private static final Logger logger = Logger.getLogger("StartServerLogger");
    private static boolean authenticated = false;
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        logger.info("The server has just start!\n-------------------------------------------------\n");

        while(true) {
            ServerThread thread = ServerThread.makeConsoleInput();
            if(!thread.isAlive())
                thread.start();

            while(!authenticated)
                authenticated = ServerNetController.authenticateUser();

            ServerNetController.GetRequestAndSendResponse();
        }
    }
}
