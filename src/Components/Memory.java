package Components;

public class Memory {

    private final static int totalMemory = 256000000;
    private int usedMemory = 0;

    public int addUsedMemory(int value) {
        usedMemory = usedMemory + value;
        return usedMemory;
    }

    public int decreaseUsedMemory(int value) {
        usedMemory = usedMemory - value;
        if(usedMemory < 0)
            usedMemory = 0;
        return usedMemory;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public int getFreeMemory() {
        return totalMemory - usedMemory;
    }

}