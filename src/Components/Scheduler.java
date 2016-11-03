package Components;
import java.util.ArrayList;

public class Scheduler {

    public static int MAX_CPU_TIME = 10;

    private int currentProcessTime = 0;
    
    private ArrayList<Process> queue = new ArrayList<>();

    public void insertPCB(Process process){
    	queue.add(process);
//    	processsetWait(getClock());
    }
    
    public void removePCB(Process process){
    	queue.remove(process);
    	currentProcessTime = 0;
    }
    
    public ProcessState getState(Process process){
    	return process.getState();
    }
    
    public void setState(Process process, ProcessState stateIn){
    	process.setState(stateIn);
    }
    
    public int getWait(Process process){
        return process.getWait();
    }
    
    public void setWait(Process process, int waitIn){
    	process.setWait(waitIn);
    }
    
    public int getArrival(Process process){
    	return process.getArrival();
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
