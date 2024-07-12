package run.halo.bark.strategy;


import run.halo.bark.domain.NotifyMe;
import run.halo.bark.event.NotifyBaseEvent;

public interface NotifyStrategy {


    void process(NotifyBaseEvent event,
        NotifyMe setting);
}
