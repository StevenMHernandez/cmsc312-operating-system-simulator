package Components;

import java.util.ArrayList;

public class Scheduler {

    private static ExecutionQueue queue = new ExecutionQueue();
    private CPU cpu = new CPU();

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

    public void execute() {
        if (getReadyQueue().size() > 0) {
            cpu.setCurrentPCB(queue.dequeueReady());
            Process current = cpu.execute();
            currentQuantum++;
            if (current != null) {
                if (current.getState() == ProcessState.WAIT) {
                    queue.enqueueIO(current);
                    currentQuantum = 0;
                } else {
                    if (currentQuantum < maxQuantum) {
                        queue.replaceReady(current);
                    } else {
                        queue.enqueueReady(current);
                        currentQuantum = 0;
                    }
                }
            } else {
                queue.getNextReady();
                currentQuantum = 0;
            }

            if (queue.getFreeMemory() > 0) {
                queue.getNextReady();
            }
        }

        CPU.advanceClock();
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
}