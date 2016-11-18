package Components;

public class Memory {

    private int totalMemory;
    private int usedMemory;

    public int getTotalMemory {
        return totalMemory;
    }

    public int getUsedMemory {
        return usedMemory;
    }

    public int getFreeMemory {
        return totalMemory - usedMemory;
    }

}