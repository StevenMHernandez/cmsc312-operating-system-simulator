package Components;

public class Memory {

    private static int totalMemory;
    private static int usedMemory = 0;

    public Memory(int memory) {
        totalMemory = memory;
    }

    /*
    public Memory() {

    }


    public void setTotalMemory(int value) {
        totalMemory = value;
    }
    */

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