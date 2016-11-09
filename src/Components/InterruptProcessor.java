package Components;

public class InterruptProcessor {
    public static boolean interrupted = false;

    public void signalInterrupt() {
        interrupted = true;
    }

    public void addEvent(Event event) {
        // TODO
    }

    public Event getEvent() {
        return null;
    }
}
