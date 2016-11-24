package Parsing;

import java.util.*;
import java.io.*;

public class parseText {

    private String inputfile = "input.txt";
    private String command, value;
    private LinkedList<String> queue = new LinkedList<String>();
    private File file = new File(inputfile);
    private Scanner input;


    public parseText() {

    }

    public void parseFile(String filename) {
        this.inputfile = filename;
        parse();
    }

    public void parseLine(String command) {
        command.toLowerCase();
        input = new Scanner(command);
        command = input.next();
        value = null;
        if(input.hasNext())
            value = input.next();
        input.close();
    }

    private void parse() {
        try {
            input = new Scanner(file);
            while(input.hasNext()) {
                queue.add(input.next());
            }
        } catch (Exception e) { System.out.println("uh what do i put here"); }
        input.close();
    }

    public LinkedList<String> getStuff() {
        return queue;
    }

    public String getCommand() {
        return command;
    }

    public String getValue() {
        return value;
    }

    public void testprint() {

        while(queue.peek() != null) {
            System.out.println(queue.poll());
        }
    }
}