package Components;

import java.util.ArrayList;

public class Scheduler {

    private static ArrayList<Process> queue = new ArrayList<>();

    public Process getNextPCB() {
        for (Process process : queue) {
            if (process.getState() == ProcessState.READY || process.getState() == ProcessState.NEW) {
                removePCB(process);
                return process;
            }
        }

        return null;
    }

    public void insertPCB(Process process) {
        setArrival(process);

        queue.add(process);
    }

    public void removePCB(Process process) {
        queue.remove(process);
    }

    public ProcessState getState(Process process) {
        return process.getState();
    }

    public void setState(Process process, ProcessState stateIn) {
        if (stateIn == ProcessState.READY) {
            queue.remove(0);
            queue.add(process);
        } else if (stateIn == ProcessState.EXIT) {
            this.removePCB(process);
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

    public static ArrayList<Process> getQueue() {
        return queue;
    }
}
