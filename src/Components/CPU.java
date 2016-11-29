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
        this.currentProcess = process;

        if (process != null) {
            this.currentProcess.setState(ProcessState.RUN);
            Scheduler.resetQuantum();
        }
    }

    public Process execute() {
        currentProcess.incrementRunTime();

        if (currentProcess.getCalculate() == 0) {
            String command = currentProcess.getQueue().remove(0);

            currentProcess.setLastCommand(command);

            switch (command) {
                case "CALCULATE":
                    currentProcess.setCalculate(Integer.valueOf(currentProcess.getQueue().remove(0)));
                    return currentProcess;
                case "OUT":
                    String out = currentProcess.getName() + "::";
                    out += currentProcess.getQueue().remove(0).substring(1);

                    while (!out.substring(out.length() - 1).equals("\"")) {
                        out = out + " " + currentProcess.getQueue().remove(0);
                    }
                    out = out.substring(0, out.length() - 1);

                    GuiScreen.println(out);
                    break;
                case "YIELD":
                    this.setState(ProcessState.READY);

                    InterruptProcessor.signalInterrupt();
                    break;
                case "IO":
                    this.setState(ProcessState.BLOCKED);

                    IOScheduler.scheduleIO(currentProcess);

                    InterruptProcessor.signalInterrupt();
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

    public void setState(ProcessState stateIn) {
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