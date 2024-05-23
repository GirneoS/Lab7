package org.example.server;

import org.example.controller.commands.SaveCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ServerInput extends Thread{
    private static HashSet<String> serverCommand = new HashSet<>();
    private static ServerInput consoleInputServer;

    private ServerInput() {
    }

    @Override
    public void run() {
    }
}
