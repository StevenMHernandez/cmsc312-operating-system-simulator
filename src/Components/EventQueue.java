package Components;
import java.util.ArrayList;

public class EventQueue {
	private ArrayList<Event> queue = new ArrayList<>();
	
    public void enQueue(Event event) {
    	queue.add(event);
    }

    Event deQueue() {
        return queue.remove(0);
    }
}
