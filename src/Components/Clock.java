package Components;

public class Clock {
    
	private static int tick = 0;
	
    public static void execute(){
        // nothing to execute
    }
    
    public static int getClock(){
    	return tick;
    }
    
    public static int advanceClock() {
        return tick++;
    }

    public static void reset() {
        tick = 0;
    }
}