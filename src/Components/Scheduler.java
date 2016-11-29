package Components;

import java.util.ArrayList;

public class Scheduler {

    private static ExecutionQueue queue = new ExecutionQueue();

    private static int maxQuantum = 10;
    private static int currentQuantum = 0;

    public Process getNextPCB() {
        return queue.dequeueReady();
    }

    public static void insertPCB(Process process) {
        process.setArrival(Clock.getClock());
        queue.enqueueReady(process);
    }

    public void removePCB(Process process) {
        queue.removeProcess(process);
    }

    /*
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
    */

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
        return queue.getReadyQueue();
    }

    public static ArrayList<Process> getWaitingQueue() {
        return queue.getWaitingQueue();
    }

    public static ArrayList<Process> getIoQueue() {
        return queue.getIoQueue();
    }

    public static void resetQuantum() {
        currentQuantum = 0;
    }

    public void increaseQuantum() {
        currentQuantum++;
    }

    public boolean checkQuantum() {
        return currentQuantum >= maxQuantum;
    }

    public void updateQueues() {
        Scheduler.queue.updateQueues();
    }

    public static void reset() {
        Scheduler.resetQuantum();
        queue.reset();
    }
}