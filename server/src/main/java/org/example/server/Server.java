package org.example.server;

import java.util.logging.Logger;

public class Server extends Thread{
    private final Logger logger = Logger.getLogger("StartServerLogger");
    private boolean authenticated = false;
    @Override
    public void run() {
        logger.info("The server has just start!\n-------------------------------------------------\n");

        while(true) {
            if(!authenticated) {
                authenticated = ServerNetController.GetRequestAndSendResponse();
            }else{
                ServerNetController.GetRequestAndSendResponse();
            }
            if (authenticated){
                ServerController.makeNewThreadServer();
            }
        }
    }
}
