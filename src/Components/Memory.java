package Components;

public class Memory {

    private final static int totalMemory = 256; //kb
    private static int usedMemory = 0;

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

    public static int getTotalMemory() {
        return totalMemory;
    }

    public static int getUsedMemory() {
        return usedMemory;
    }

    public static int getFreeMemory() {
        return totalMemory - usedMemory;
    }

}