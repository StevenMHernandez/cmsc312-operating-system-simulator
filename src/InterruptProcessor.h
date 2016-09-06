#include "src/Event.h"

class InterruptProcessor {

public:
    void signalInterrupt();

    void addEvent(Event event);

    Event getEvent();
};