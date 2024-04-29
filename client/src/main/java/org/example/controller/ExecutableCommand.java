package org.example.controller;

import java.io.Serializable;

/**
 * This interface makes the class possible to use in the app as a command.
 */
public interface ExecutableCommand extends Serializable {
    /**
     * This method contains the logic of command execution
     */
    String execute();

    /**
     * This method validates a command from the console
     * @return returns true if command was entered correctly and false if it was entered incorrectly
     */
    boolean validate();
    void setCmd(String[] cmd);
    String toString();
}
