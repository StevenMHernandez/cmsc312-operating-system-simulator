package Components;

public class OS {
    private Scheduler scheduler = new Scheduler();
    private CPU cpu = new CPU();
    private InterruptProcessor interruptProcessor = new InterruptProcessor();

    public void execute() {
        cpu.advanceClock();

        // if there is room for process, remove them from the waiting queue
        // and remove and process that are labeled as `EXIT`
        scheduler.updateQueues();

        // check for interrupts
        if (cpu.detectInterrupt() || null == cpu.getCurrentPCB()) {
            cpu.setCurrentPCB(scheduler.getNextPCB());
        }

        // check for events
        if (cpu.detectPreemption()) {
            Event event = EventQueue.deQueue();

            switch (event.type) {
                case "IO":
                    cpu.setCurrentPCB(event.process);
                    break;
                default:
                    break;
            }
        }

        // there may not be any more readyProcessList from the scheduler
        if (null != cpu.getCurrentPCB()) {
            Process currentProcess = cpu.getCurrentPCB();

            cpu.setState(scheduler, ProcessState.RUN);

            if (currentProcess.getQueue().isEmpty()) {
                cpu.setState(scheduler, ProcessState.EXIT);

                interruptProcessor.signalInterrupt();
                return;
            } else {
                // read next line from our process's queue. (dequeue)
                String nextCommand = currentProcess.getQueue().remove(0);

                // check if the command has parameters we should read (remove from the queue)
                switch (nextCommand) {
                    case "CALCULATE":
                    case "OUT":
                        currentProcess.getQueue().remove(0);
                        break;
                    default:
                        break;
                }

                // check if the command needs us to do anything specific
                switch (nextCommand) {
                    case "IO":
                        cpu.setState(scheduler, ProcessState.WAIT);

                        IOScheduler.scheduleIO(currentProcess);

                        interruptProcessor.signalInterrupt();
                        return;
                    default:
                        break;
                }

                // add time to Process CPU_Time
                scheduler.increaseQuantum();

                if (scheduler.checkQuantum()) {
                    cpu.setState(scheduler, ProcessState.READY);
                    System.out.println("Done");

                    interruptProcessor.signalInterrupt();
                }
            }
        }
    }
}
