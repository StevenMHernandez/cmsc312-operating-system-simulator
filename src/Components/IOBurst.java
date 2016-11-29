package Components;

import java.util.Random;

public class IOBurst {
    Random random = new Random();

    public int generateIOBurst() {
        return Clock.getClock() + ((random.nextInt(25) + 1) + 25);  // 25-50 cycles
    }
}