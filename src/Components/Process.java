package Components;

import java.util.ArrayList;

public class Process {

    private int calculate = 0;
    private int arrival;
    private int size;
    private int runTime = 0;

    public String name;
    private String lastCommand;

    private ProcessState state = ProcessState.READY;

    private ArrayList<String> queue;

    public Process(String name, ArrayList<String> queue, int size) {
        this.name = name;
        this.queue = queue;
        this.size = size;
        this.state = ProcessState.NEW;
    }

    public int getCalculate() {
        return calculate;
    }

    public void setCalculate(int value) {
        calculate = value;
    }

    public void decrementCalculate() {
        calculate--;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return this.state.toString();
    }

    public ArrayList<String> getQueue() {
        return queue;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrivalIn) {
        this.arrival = arrivalIn;
    }

    public int getWait() {
        return Clock.getClock() - arrival;
    }

    public String getLastCommand() {
        if (null != this.lastCommand && this.lastCommand.equals("CALCULATE")) {
            return this.lastCommand + "(" + this.calculate + ")";
        }

        return this.lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public int getRunTime() {
        return runTime;
    }

    public void incrementRunTime() {
        this.runTime++;
    }
}