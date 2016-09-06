#include "src/Event.h"

class EventQueue {

public:
    void enQueue(Event event);

    Event deQueue();
};