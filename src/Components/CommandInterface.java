package Components;

import Gui.Gui;
import Parsing.parseText;

public class CommandInterface extends Gui{


    private static final parseText pt = new parseText();

    public static boolean temporaryName(String input) {
        pt.parseLine(input);
        boolean valid = valid();
        if (valid) {

            if (pt.getValue() == null)
                chooseCommand(pt.getCommand());
            else
                chooseCommand(pt.getCommand(), pt.getValue());
        }
        return valid;
    }

    private static boolean valid() {
        if(pt.getCommand().equals("proc"))
            return true;
        if(pt.getCommand().equals("mem"))
            return true;
        if(pt.getCommand().equals("exe"))
            return true;
        if(pt.getCommand().equals("reset"))
            return true;
        if(pt.getCommand().equals("exit"))
            return true;
        if(pt.getCommand().equals("load")) if(pt.getValue() != null)
            return true;
        return false;
    }

    private static void chooseCommand(String command) {
        switch(command) {
            case "proc": proc(); break;
            case "mem": mem(); break;
            case "exe": exe(); break;
            case "reset": reset(); break;
            case "exit": exit(); break;
            default: break;
        }
    }

    private static void chooseCommand(String command, String value) {
        if(command.equals("load"))
            load(value);
    }

    private static void proc() {

    }

    private static void mem() {

    }

    private static void load(String job) {
        pt.parseFile(job);
        if(!pt.getQueue().isEmpty()) {
            int size = Integer.valueOf(pt.getQueue().remove(0));
            Scheduler.insertPCB(new Process(job, pt.getQueue(), size));
        }
    }

    private static void exe() {
        // scheduler.insertPCB()
    }

    private static void reset() {
        Scheduler.resetQuantum();
    }

    private static void exit() {

    }

    public void promptUser(){

    }
}