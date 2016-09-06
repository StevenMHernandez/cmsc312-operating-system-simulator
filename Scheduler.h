class Scheduler {

public:

    void insertPCB(PCB processIn);
    void removePCB(PCB processIn);

    State getState(PCB processIn);
    void setState(PCB processIn);

    int getWait(PCB processIn);
    void setWait(PCB processIn);

    int getArrival(PCB processIn);
    void setArrival(PCB processIn);

    int getCPUTime(PCB processIn);
    void setCPUTime(PCB processIn);
};