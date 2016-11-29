package Components;

public class InterruptProcessor {
    public static boolean interrupted = false;

    public static void signalInterrupt() {
        InterruptProcessor.interrupted = true;
    }

    public static void addEvent(Event event) {
        EventQueue.enQueue(event);
    }

    public static Event getEvent() {
        return EventQueue.deQueue();
    }
}
