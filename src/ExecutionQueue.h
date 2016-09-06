#include "src/Event.h"

class ExecutionQueue {

public:
    void enQueue(Event event);

    Event deQueue();
};