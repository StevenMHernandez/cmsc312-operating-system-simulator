package Components;

import java.util.ArrayList;

public class Scheduler {

    //private int freeMemory = 256;
    private static ArrayList<Process> readyQueue = new ArrayList<>();
    private static ArrayList<Process> waitingQueue = new ArrayList<>();

    public Process getNextPCB() {
        Process next =  readyQueue.remove(0);
        //freeMemory += next.getSize();

        return next;

    }

    public void insertReadyPCB(Process process) {
        setArrival(process);
        readyQueue.add(process);

        /*
        if (process.getSize() < freeMemory) {
            setArrival(process);

            readyQueue.add(process);
            freeMemory -= process.getSize();
        } else {
            insertWaitingPCB(process);
        }
        */
    }

    public void insertWaitingPCB(Process process) {
        setArrival(process);

        waitingQueue.add(process);
    }

    public void removeReadyPCB(Process process) {
        readyQueue.remove(process);
        //freeMemory += process.getSize();
    }

    public void removeWaitingPCB(Process process) {
        waitingQueue.remove(process);
    }

    public ProcessState getState(Process process) {
        return process.getState();
    }

    public void setState(Process process, ProcessState stateIn) {
        if (stateIn == ProcessState.READY) {
            readyQueue.remove(0);
            readyQueue.add(process);
        }else if(stateIn == ProcessState.WAIT) {
            process.setState((stateIn));
            insertWaitingPCB(process);
        } else if (stateIn == ProcessState.EXIT) {
            if (process.getState() == ProcessState.READY )
                removeReadyPCB(process);
            else if (process.getState() == ProcessState.WAIT)
                removeWaitingPCB(process);
        }

        process.setState(stateIn);
    }

    public int getWait(Process process) {
        return process.getWait();
    }

    public int getArrival(Process process) {
        return process.getArrival();
    }

    public void setArrival(Process process) {
        process.setArrival(Clock.getClock());
    }

    public static ArrayList<Process> getReadyQueue() {
        return readyQueue;
    }

    public static ArrayList<Process> getWaitingQueue() {
        return waitingQueue;
    }
}
