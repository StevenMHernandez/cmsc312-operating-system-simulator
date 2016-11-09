package Components;
import java.util.ArrayList;

public class Scheduler {

    public static int MAX_CPU_TIME = 10;

    private int currentProcessTime = 0;
    
    private ArrayList<Process> queue = new ArrayList<>();

    Process currentProcess = null;

    public Process getCurrentPCB() {
        return currentProcess;
    }

    public Process getNextPCB() {
        // TODO: we could pick based on the arrival time of the process
        this.currentProcess = queue.isEmpty() ? null : queue.get(0);

        return this.currentProcess;
    }

    public void insertPCB(Process process){
        setArrival(process);

    	queue.add(process);
    }
    
    public void removePCB(Process process){
    	queue.remove(process);

    	currentProcessTime = 0;
    }
    
    public ProcessState getState(Process process){
    	return process.getState();
    }
    
    public void setState(Process process, ProcessState stateIn){
        if (stateIn == ProcessState.EXIT) {
            this.removePCB(currentProcess);
            this.currentProcess = null;
        }

    	process.setState(stateIn);
    }
    
    public int getWait(Process process){
        return process.getWait();
    }

    public int getArrival(Process process){
    	return process.getArrival();
    }

    public void setArrival(Process process){
    	process.setArrival(Clock.getClock());
    }
    
    public int getCPUTime(Process process){
    	return currentProcessTime;
    }

    public void addCPUTime(int time){
        currentProcessTime += time;
    }
}
