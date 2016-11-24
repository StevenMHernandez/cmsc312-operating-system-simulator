package Components;

import java.util.ArrayList;

public class Process {

    private ProcessState state;

    private int calculate = 0;
    private int arrival;

    private ArrayList<String> queue;

    public String name = "Process Name";

    private int size;

    public Process(ArrayList<String> queue, int size) {
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
}