class Process {

public:
    enum state {
        NEW,
        READY,
        RUN,
        WAIT,
        EXIT
    };

    Process(state current_state);
};
