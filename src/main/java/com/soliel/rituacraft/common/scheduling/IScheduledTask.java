package com.soliel.rituacraft.common.scheduling;

import net.minecraftforge.event.TickEvent;

public interface IScheduledTask {
    TickEvent.Type getEventType();
    int getTickFrequency();
    void onSchedulerRun(TickEvent event);
}
