package Components;

public class OS {
    private Scheduler scheduler = new Scheduler();
    private CPU cpu = new CPU();

    public void execute() {
        // if there is room for process, remove them from the waiting queue
        // and remove and process that are labeled as `EXIT`
        scheduler.updateQueues();

        // check for interrupts
        if (cpu.detectInterrupt() || null == cpu.getCurrentPCB()) {
            cpu.setCurrentPCB(scheduler.getNextPCB());
            if (null != cpu.getCurrentPCB()) {
                cpu.setState(ProcessState.RUN);
            }
        }

        cpu.advanceClock();

        // check for events
        if (cpu.detectPreemption()) {
            Event event = InterruptProcessor.getEvent();

            switch (event.type) {
                case "IO":
                    if (null != cpu.getCurrentPCB()) {
                        cpu.setState(ProcessState.READY);
                    }
                    cpu.setCurrentPCB(event.process);
                    cpu.setState(ProcessState.RUN);
                    break;
                default:
                    break;
            }
        }

        // there may not be any more readyProcessList from the scheduler
        if (null != cpu.getCurrentPCB()) {
            Process currentProcess = cpu.getCurrentPCB();

            if (currentProcess.getQueue().isEmpty()) {
                cpu.setState(ProcessState.EXIT);

                InterruptProcessor.signalInterrupt();
            } else {
                cpu.execute();

                // add time to Process CPU_Time
                scheduler.increaseQuantum();

                if (scheduler.checkQuantum()) {
                    Scheduler.resetQuantum();
                    cpu.setState(ProcessState.READY);

                    InterruptProcessor.signalInterrupt();
                }
            }
        }
    }
}
