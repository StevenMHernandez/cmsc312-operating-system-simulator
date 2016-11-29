package Components;

public class IOScheduler {
    private static IOBurst ioBurst = new IOBurst();

    public static void scheduleIO(Process process){
        Event event = new Event(process, ioBurst.generateIOBurst(), "IO");
        InterruptProcessor.addEvent(event);
    }

    public static void startIO() {
        // TODO
    }
}
