package Components;

import java.util.ArrayList;
import java.util.Collections;

public class ExecutionQueue {
    private int freeMemory = 256;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> waitingQueue = new ArrayList<>();
    private ArrayList<Process> ioQueue = new ArrayList<>();


    public void enqueueReady(Process process) {
        if (process.getSize() < freeMemory) {
            process.setState(ProcessState.READY);
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
        // rotate queue, round robin (only if there are unblocked processes)
        int unblocked = 0;
        for (Process p : this.readyQueue) {
            if (p.getState() != ProcessState.BLOCKED) {
                unblocked++;
            }
        }
        if (unblocked > 0) {
            Collections.rotate(this.readyQueue, 1);
        }

        // return process
        for (Process p : this.readyQueue) {
            if (p.getState() != ProcessState.BLOCKED) {
                freeMemory += p.getSize();
                return p;
            }
        }

        return null;
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

    public void updateQueues() {
        // remove old processes
        ArrayList<Process> processToRemove = new ArrayList();
        for (Process p : this.readyQueue) {
            if (p.getState() == ProcessState.EXIT) {
                processToRemove.add(p);
            }
        }

        for (Process p : processToRemove) {
            this.readyQueue.remove(p);
        }

        // add waiting processes
        if (this.freeMemory > 0 && this.waitingQueue.size() > 0) {
            Process p = this.waitingQueue.get(0);
            if (p.getSize() < this.freeMemory) {
                this.enqueueReady(p);
                this.waitingQueue.remove(p);
            }
        }
    }
}
