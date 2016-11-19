package Components;

import java.util.ArrayList;

public class ExecutionQueue {
    private int freeMemory = 256;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> waitingQueue = new ArrayList<>();


    public void enqueueReady(Process process) {
        if (process.getSize() < freeMemory) {
            readyQueue.add(process);
            freeMemory -= process.getSize();
        } else {
            enqueueWaiting(process);
        }
    }

    public void replaceReady(Process process) {
        readyQueue.add(0, process);
    }

    public void enqueueWaiting(Process process) {
        waitingQueue.add(process);
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

    public void getNextReady() {
        for (Process process : waitingQueue) {
            enqueueReady(process);
        }
    }
}
