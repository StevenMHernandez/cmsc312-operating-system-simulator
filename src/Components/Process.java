package Components;

import java.util.ArrayList;

public class Process {

    private int calculate = 0;
    private int arrival;
    private int size;
    private int runTime = 0;
    private int ioRequests = 0;
    private int totalRuntime = 0;

    public String name;
    private String lastCommand;

    private ProcessState state = ProcessState.READY;

    private ArrayList<String> queue = new ArrayList<>();

    public Process(String name, ArrayList<String> queueIn, int size) {
        this.name = name;
        //this.queue = queueIn;
        this.size = size;
        this.state = ProcessState.NEW;

        for (int i = 0; i < queueIn.size(); i++) {
            this.queue.add(queueIn.get(i));
        }
        calculateTotalRunTime();
    }

    private void calculateTotalRunTime() {
        for(int i = 0; i < queue.size(); i++) {
            if (queue.get(i).equals("CALCULATE")) {
                totalRuntime = totalRuntime + Integer.valueOf(queue.get(i + 1));
            }
        }
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

    public void incrementIoRequests() {
        ioRequests++;
    }

    public int getIoRequests() {
        return ioRequests;
    }

    public int getTotalRuntime() {
        return totalRuntime;
    }
}