package Components;
import java.util.PriorityQueue;

public class EventQueue {
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
    public void enQueue(Event event) {
    	queue.add(event);
    }

    Event deQueue() {
        return queue.poll();
    }
}
