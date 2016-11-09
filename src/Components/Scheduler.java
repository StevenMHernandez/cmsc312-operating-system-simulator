package Components;

import java.util.ArrayList;

public class Scheduler {

    public static int MAX_CPU_TIME = 10;

    private int currentProcessTime = 0;

    private static ArrayList<Process> queue = new ArrayList<>();

    Process currentProcess = null;

    public Process getCurrentPCB() {
        return currentProcess;
    }

    public Process getNextPCB() {
        for (Process process : queue) {
            if (process.getState() == ProcessState.READY || process.getState() == ProcessState.NEW) {
                this.currentProcess = process;

                return this.currentProcess;
            }
        }

        this.currentProcess = null;

        return null;
    }

    public void insertPCB(Process process) {
        setArrival(process);

        queue.add(process);
    }

    public void removePCB(Process process) {
        queue.remove(process);

        currentProcessTime = 0;
    }

    public ProcessState getState(Process process) {
        return process.getState();
    }

    public void setState(Process process, ProcessState stateIn) {
        if (stateIn == ProcessState.READY) {
            queue.remove(0);
            queue.add(process);
        } else if (stateIn == ProcessState.EXIT) {
            this.removePCB(currentProcess);
            this.currentProcess = null;
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

    public int getCPUTime(Process process) {
        return currentProcessTime;
    }

    public void addCPUTime(int time) {
        currentProcessTime += time;
    }

    public static ArrayList<Process> getQueue() {
        return queue;
    }
}
