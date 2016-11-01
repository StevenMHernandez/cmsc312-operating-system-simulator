package Components;

public class Process {
    private ProcessState state;
    
    private int arrival;
    private int wait;

    public Process() {
        this.state = ProcessState.NEW;
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
    	//return getClock() - arrival;? 
    	return wait;
    }
    
    public void setWait(int waitIn) {
    	this.wait = waitIn;
    }
}
