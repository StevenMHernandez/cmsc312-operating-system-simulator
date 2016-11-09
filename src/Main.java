import Components.*;

import Components.Process;
import Gui.Gui;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Application.launch(Gui.class, args);

        Scheduler scheduler = new Scheduler();
        InterruptProcessor interruptProcessor = new InterruptProcessor();

        // should we run the simulator continuously
        boolean exeContinuously = true;
        // should we run the simulator for a certain number of steps/clock cycles?
        int exeSteps = -1;

        while (true) {
            if (!exeContinuously && exeSteps == 0) {
                return;
            } else {
                exeSteps--;
            }

            if (null != scheduler.getCurrentPCB()) {
                scheduler.getNextPCB();
            }

            // there may not be any more processes from the scheduler
            if (null != scheduler.getCurrentPCB()) {
                Process currentProcess = scheduler.getCurrentPCB();

                scheduler.setState(currentProcess, ProcessState.RUN);

                if (currentProcess.getQueue().isEmpty()) {
                    scheduler.setState(currentProcess, ProcessState.EXIT);

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
                            scheduler.setState(currentProcess, ProcessState.WAIT);

                            // RequestRandomIO(currentProcess)

                            interruptProcessor.signalInterrupt();
                            return;
                        default:
                            break;
                    }

                    // add time to Process CPU_Time
                    scheduler.addCPUTime(1);

                    if (scheduler.getCPUTime(currentProcess) > Scheduler.MAX_CPU_TIME) {
                        scheduler.setState(currentProcess, ProcessState.READY);

                        interruptProcessor.signalInterrupt();
                        return;
                    }
                }

                CPU.advanceClock();
            }
        }
    }
}
