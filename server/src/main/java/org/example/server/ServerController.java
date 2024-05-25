package org.example.server;

import org.example.models.MainCollection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerController {
    private final static Logger logger = Logger.getLogger("StartServerLogger");
    public static void main(String[] args) {
        logger.info("The server has just start!\n-------------------------------------------------\n");

        MainCollection.initCollection();
        new ServerInput().run();
    }
}
