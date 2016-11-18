package Components;

public class IOScheduler {
	private Process waitingProcess;
    int remainingIO;
    IOBurst ioBurst;
    Scheduler scheduler;

    public void scheduleIO(Scheduler schedulerIn,Process process){
    	waitingProcess = process;
        remainingIO = 5;
        ioBurst = new IOBurst();
        scheduler = schedulerIn;
    }
    
    public void startIO() {
    	ioBurst.generateIOBurst(remainingIO);
        if (remainingIO < 1) {
            waitingProcess.setState(ProcessState.READY);
            scheduler.insertReadyPCB(waitingProcess);

        }
    }

}
