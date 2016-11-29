package Components;
import java.util.PriorityQueue;

public class EventQueue {
    public static PriorityQueue<Event> queue = new PriorityQueue<>(10, (a, b) -> a.time - b.time);
	
    public static void enQueue(Event event) {
    	queue.add(event);
    }

    public static Event peek() {
        return queue.peek();
    }

    public static Event deQueue() {
        return queue.poll();
    }

    public static void reset() {
        queue.clear();
    }
}
