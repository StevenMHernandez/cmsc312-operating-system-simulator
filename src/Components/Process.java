package Components;

import java.util.ArrayList;

public class Process {
    private ProcessState state;
    
    private int arrival;

    private ArrayList<String> queue;

    public Process(ArrayList<String> queue) {
        this.queue = queue;
        this.state = ProcessState.NEW;
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
