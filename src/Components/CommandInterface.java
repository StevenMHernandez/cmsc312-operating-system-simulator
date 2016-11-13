package Components;

import Parsing.parseText;

public class CommandInterface {
  
  parseText pt = new parseText();
  
  public void temporaryname(String input) {
    //add check for valid command or in gui. use displayBox to display error
    pt.parseLine(input);
    if(pt.value == null)
      chooseCommand(pt.command);
    else
      chooseCommand(pt.command, pt.value);
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
      if(command == "load") //use .equalsTo?
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