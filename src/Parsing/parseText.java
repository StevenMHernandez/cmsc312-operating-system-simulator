package Parsing;

import Gui.GuiScreen;

import java.util.*;
import java.io.*;

public class parseText {

    private String inputfile;
    private String command, value;
    private ArrayList<String> queue = new ArrayList<>();
    private Scanner input;

    public parseText() {

    }

    public void parseFile(String filename) {
        this.inputfile = "programs/" + filename + ".txt";
        parseFile();
    }

    public void parseLine(String command) {
        command = command.toLowerCase();
        input = new Scanner(command);
        this.command = input.next();
        value = null;
        if (input.hasNext())
            value = input.next();
        input.close();
    }

    private void parseFile() {
        queue.clear();
        try {
            File file = new File(inputfile);
            input = new Scanner(file);
            while (input.hasNext()) {
                queue.add(input.next());
            }
        } catch (Exception e) {
            GuiScreen.println("Program not found.");
        }
        input.close();
    }

    public ArrayList<String> getQueue() {
        return queue;
    }

    public String getCommand() {
        return command;
    }

    public String getValue() {
        return value;
    }
}