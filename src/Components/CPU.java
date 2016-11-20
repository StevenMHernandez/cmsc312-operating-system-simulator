package Components;

import java.util.ArrayList;

public class CPU {

    private Process currentProcess;

    public static int advanceClock() {
        InterruptProcessor.interrupted = false;

        return Clock.advanceClock();
    }

    public Process getCurrentPCB() {
        return currentProcess;
    }

    public void setCurrentPCB(Process process) { currentProcess = process; }

    public Process execute() {
        String command = currentProcess.getQueue().remove(0);
        switch (command) {
            case "CALCULATE":
            case "OUT":
                break;
            case "IO":
                //process IO
                currentProcess.setState(ProcessState.WAIT);
                return currentProcess;
            default:
                break;
        }
        if (currentProcess.getQueue().size() > 0)
            return currentProcess;
        else
            return null;
    }

//    public ProcessState getState(Process process) {
//        return process.getState();
//    }
//
//    public void setState(Scheduler scheduler, ProcessState stateIn) {
//        if (stateIn == ProcessState.WAIT) {
//            //move process back into scheduler
//            currentProcess.setState(ProcessState.READY);
//            scheduler.insertPCB(currentProcess);
//            this.currentProcess = null;
//        } else if (stateIn == ProcessState.EXIT) {
//            currentProcess.setState(stateIn);
//            this.currentProcess = null;
//        } else {
//            currentProcess.setState(stateIn);
//        }
//    }

    public static boolean detectInterrupt() {
        return InterruptProcessor.interrupted;
    }

    public static boolean detectPreemption() {
        // TODO
        return false;
    }
}