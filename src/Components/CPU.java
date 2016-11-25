package Components;

public class CPU {

    private Process currentProcess;

    public static int advanceClock() {
        InterruptProcessor.interrupted = false;

        return Clock.advanceClock();
    }

    public Process getCurrentPCB() {
        return currentProcess;
    }

    public void setCurrentPCB(Process process) {
        if (this.currentProcess != null) {
            this.currentProcess.setState(ProcessState.READY);
        }
        if (process != null) {
            this.currentProcess = process;
            this.currentProcess.setState(ProcessState.RUN);
            Scheduler.resetQuantum();
        }
    }

    public Process execute() {
        String command = currentProcess.getQueue().remove(0);
        if (currentProcess.getCalculate() == 0) {
            switch (command) {
                case "CALCULATE":
                    currentProcess.setCalculate(Integer.valueOf(currentProcess.getQueue().remove(0)));
                    return currentProcess;
                case "OUT":
                    //display to textarea
                    break;
                case "YIELD":
                    break;
                case "IO":
                    //process IO
                    currentProcess.setState(ProcessState.WAIT);
                    return currentProcess;
                default:
                    break;
            }
        } else {
            currentProcess.decrementCalculate();
            return currentProcess;
        }
        if (currentProcess.getQueue().size() > 0)
            return currentProcess;
        else
            return null;
    }

    public ProcessState getState(Process process) {
        return process.getState();
    }

    public void setState(Scheduler scheduler, ProcessState stateIn) {
        if (stateIn == ProcessState.WAIT) {
            //move process back into scheduler
            Scheduler.insertPCB(currentProcess);
            this.currentProcess = null;
        } else if (stateIn == ProcessState.EXIT) {
            currentProcess.setState(stateIn);
            this.currentProcess = null;
        } else {
            currentProcess.setState(stateIn);
        }
    }

    public boolean detectInterrupt() {
        return InterruptProcessor.interrupted;
    }

    public boolean detectPreemption() {
        return null != EventQueue.peek() && EventQueue.peek().time < Clock.getClock();
    }
}