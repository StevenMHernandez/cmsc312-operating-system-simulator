package Components;
import java.util.ArrayList;

public class Scheduler {

    public static int MAX_CPU_TIME = 10;

    private int currentProcessTime = 0;
    
    private ArrayList<Process> queue = new ArrayList<Process>();

    public void insertPCB(Process process){
    	queue.add(process);
    	processsetWait(getClock());
    }
    
    public void removePCB(Process process){
    	queue.remove(process);
    	currentProcessTime = 0;
    }
    
    public String getState(Process process){
    	//state is a part of process. why not just use process.getState()?
    	String state = process.getState();
    	return state;
    }
    
    public void setState(Process process, String stateIn){
    	process.setState(stateIn);
    }
    
    public int getWait(Process process){
    	int wait = process.getWait();
    	return wait;
    }
    
    public void setWait(Process process, int waitIn){
    	process.setWait(waitIn);
    }
    
    public int getArrival(Process process){
    	int arrival = process.getArrival();
    	return arrival;
    }
    
    public void setArrival(Process process, int arrivalIn){
    	process.setArrival(arrivalIn);
    }
    
    public int getCPUTime(Process process){
    	return 0;
    }

    public void setCPUTime(Process process, int waitIn){
    	//currentProcessTime = waitIn;?
    }

    public void addCPUTime(int time){
        currentProcessTime += time;
    }
}
