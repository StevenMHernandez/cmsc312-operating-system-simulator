package Components;

import java.util.ArrayList;

public class ExecutionQueue {


    private int freeMemory = 256;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> waitingQueue = new ArrayList<>();
    private ArrayList<Process> ioQueue = new ArrayList<>();


    public void enqueueReady(Process process) {
        if (process.getSize() < freeMemory) {
            readyQueue.add(process);
            freeMemory -= process.getSize();
        } else {
            enqueueWaiting(process);
        }
    }

    public void getNextReady() {
        Process nextProcess;
        for (int i = 0; i < waitingQueue.size(); i++) {
            nextProcess = dequeueWaiting();
            if (nextProcess.getSize() < freeMemory) {
                enqueueReady(nextProcess);
            } else {
                enqueueWaiting(nextProcess);
            }

        }
    }

    public void replaceReady(Process process) {
        freeMemory -= process.getSize();
        readyQueue.add(0, process);
    }

    public void enqueueWaiting(Process process) {
        waitingQueue.add(process);
    }

    public void enqueueIO(Process process) {
        ioQueue.add(process);
    }

    public Process dequeueReady() {
        Process returnProcess = readyQueue.remove(0);
        freeMemory += returnProcess.getSize();

        return returnProcess;
    }

    public Process dequeueWaiting() {
        return waitingQueue.remove(0);
    }

    public void removeProcess(Process process) {
        if (readyQueue.remove(process))
            return;
        else
            waitingQueue.remove(process);
    }

    public int getReadyQueueSize() {
        return readyQueue.size();
    }

    public int getWaitingQueueSize() {
        return waitingQueue.size();
    }

    public ArrayList<Process> getReadyQueue() {
        return readyQueue;
    }

    public ArrayList<Process> getWaitingQueue() {
        return waitingQueue;
    }

    public ArrayList<Process> getIoQueue() {
        return ioQueue;
    }

    public int getFreeMemory() {
        return freeMemory;
    }
}
