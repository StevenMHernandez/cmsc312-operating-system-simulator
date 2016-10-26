import Components.InterruptProcessor;
import Components.Process;
import Components.ProcessState;
import Components.Scheduler;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        InterruptProcessor interruptProcessor = new InterruptProcessor();

        Process currentProcess = null;
        boolean interrupted = false;
        boolean exeContinuously = true; // should we run the simulator continuously
        int exeSteps = -1; // should we run the simulator for a certain amount of steps?

        // TODO: every Clock-tick:
        while (true) {
            if (!exeContinuously && exeSteps == 0) {
                return;
            } else {
                exeSteps--;
            }

            if (null != currentProcess) {
                // currentProcess = get next process from Scheduler
            }

            // there may not be any more processes from the sceduler
            if (null != currentProcess) {
                // read next line from our process's queue. (dequeue)

                // TODO: check if line asks for user IO
                if (true) {
                    currentProcess.setState(ProcessState.WAIT);
                    // RequestRandomIO(currentProcess)
                    interruptProcessor.signalInterrupt();
                }

                // TODO: check if there are no items left in this process's queue
                if (true) {
                    currentProcess.setState(ProcessState.EXIT);
                    interruptProcessor.signalInterrupt();
                }

                // add time to Process CPU_Time
                scheduler.addCPUTime(1);

                if (scheduler.getCPUTime(currentProcess) > Scheduler.MAX_CPU_TIME) {
                    currentProcess.setState(ProcessState.READY);
                    interruptProcessor.signalInterrupt();
                }

                // advance the clock // Clock.advanceClock()
                // Next Clock tick, go back to the top of the loop
            }
        }
    }
}
