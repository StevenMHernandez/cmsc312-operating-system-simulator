package Components;

public class CPU {
    public static int advanceClock() {
        InterruptProcessor.interrupted = false;

        return Clock.advanceClock();
    }

    public static boolean detectInterrupt() {
        return InterruptProcessor.interrupted;
    }

    public static boolean detectPreemption() {
        // TODO
        return false;
    }
}