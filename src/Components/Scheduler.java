package Components;

public class Scheduler {

    public static int MAX_CPU_TIME = 10;

    private int currentProcessTime = 0;

    public void insertPCB(Process process){
    	
    }
    
    public void removePCB(Process process){
    	
    }
    
    public String getState(Process process){
    	return null;
    }
    
    public void setState(Process process, String stateIn){
    	
    }
    
    public int getWait(Process process){
    	return 0;
    }
    
    public void setWait(Process process, int waitIn){
    	
    }
    
    public int getArrival(Process process){
    	return 0;
    }
    
    public void setArrival(Process process, int waitIn){
    	
    }
    
    public int getCPUTime(Process process){
    	return 0;
    }

    public void setCPUTime(Process process, int waitIn){

    }

    public void addCPUTime(int time){
        currentProcessTime += time;
    }
}
