package Components;

import java.util.ArrayList;

public class IOScheduler {
	private Process currentProcess;
    int remainingIO;
    IOBurst ioBurst = new IOBurst();
    ArrayList<Process> ioQueue;

    public IOScheduler (ArrayList<Process> ioQueue) {
        this.ioQueue = ioQueue;
    }

    public void scheduleIO(Process process){
        ioQueue.add(process);
    }

    public void startIO() {
        currentProcess = ioQueue.remove(0);
        remainingIO = ioBurst.generateIOBurst();
    }

    public void processIO(Scheduler scheduler) {
        remainingIO--;
        if (remainingIO == 0) {
            currentProcess.setState(ProcessState.READY);
            scheduler.insertPCB(currentProcess);
            currentProcess = null;
        }
    }

}
