package Components;

public class CPU {

    private Process currentProcess;

    private int currentProcessTime = 0;
    public static int MAX_CPU_TIME = 10;

    public static int advanceClock() {
        InterruptProcessor.interrupted = false;

        return Clock.advanceClock();
    }

    public Process getCurrentPCB() {
        return currentProcess;
    }

    public void setCurrentPCB(Process process) { currentProcess = process; }

    public ProcessState getState(Process process) {
        return process.getState();
    }

    public void setState(Scheduler scheduler, ProcessState stateIn) {
        if (stateIn == ProcessState.WAIT) {
            //move process back into scheduler
            currentProcess.setState(ProcessState.READY);
            scheduler.insertPCB(currentProcess);
            this.currentProcess = null;
        } else if (stateIn == ProcessState.EXIT) {
            currentProcess.setState(stateIn);
            this.currentProcess = null;
        } else {
            currentProcess.setState(stateIn);
        }
    }

    public int getCPUTime(Process process) {
        return currentProcessTime;
    }

    public void addCPUTime(int time) {
        currentProcessTime += time;
    }


    public static boolean detectInterrupt() {
        return InterruptProcessor.interrupted;
    }

    public static boolean detectPreemption() {
        // TODO
        return false;
    }
}