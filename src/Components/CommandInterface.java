package Components;

import Parsing.parseText;

public class CommandInterface {

    //static class

    private static final parseText pt = new parseText();

    public boolean temporaryName(String input) {
        //add check for valid command or in gui. use displayBox to display error
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

    private boolean valid() { //getting errors when trying to compare
        String testing = pt.getCommand();
        if(testing.equals("proc"))
            return true;
        //add integer check for pt.getValue()
        return false;
    }

    private void chooseCommand(String command) {
        switch(command) {
            case "proc": proc(); break;
            case "mem": mem(); break;
            case "exe": exe(); break;
            case "reset": reset(); break;
            case "exit": exit(); break;
            default: break;
        }
    }

    private void chooseCommand(String command, String value) {
        if(command.equals("load"))
            load(value);
    }

    private void proc() {

    }

    private void mem() {

    }

    private void load(String job) {
        pt.parseFile(job);
        //change parsetext datatype to arraylist to match with process class

    }

    private void exe() {
        // scheduler.insertPCB()
    }

    private void reset() {

    }

    private void exit() {

    }

    public void promptUser(){

    }
}