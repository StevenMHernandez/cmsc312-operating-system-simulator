package Components;

public class Process {
    private ProcessState state;

    public Process() {
        this.state = ProcessState.NEW;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }
}
