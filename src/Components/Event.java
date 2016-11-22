package Components;

public class Event {
    public String type;
    public Process process;
    public int time;

    public Event(Process process, int time, String type) {
        this.process = process;
        this.time = time;
        this.type = type;
    }
}
