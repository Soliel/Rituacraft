package com.soliel.rituacraft.common.scheduling;

import com.soliel.rituacraft.common.TickHandler;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonScheduler {
    private final Map<TickEvent.Type, List<IScheduledTask>> taskMap = new HashMap<>();
    private final TickHandler tickHandler;

    public CommonScheduler(TickHandler handler) {
        this.tickHandler = handler;
        handler.register(TickEvent.Type.WORLD, this::runWorldTasks);
        handler.register(TickEvent.Type.SERVER, this::runServerTasks);
        handler.register(TickEvent.Type.PLAYER, this::runPlayerTasks);
        handler.register(TickEvent.Type.CLIENT, this::runClientTasks);
        handler.register(TickEvent.Type.RENDER, this::runRenderTasks);
    }

    public void register(IScheduledTask scheduledTask) {
        TickEvent.Type type = scheduledTask.getEventType();
        if(!taskMap.containsKey(type)) {
            taskMap.put(type, new ArrayList<>());
        }

        List<IScheduledTask> scheduledList = taskMap.get(type);
        scheduledList.add(scheduledTask);
        taskMap.put(type, scheduledList);
    }

    private void runTask(TickEvent.Type type, TickEvent event) {
        if(!taskMap.containsKey(type)) return;

        List<IScheduledTask> scheduledList = taskMap.get(type);
        int currentTick = tickHandler.getCurrentTypeTickCount(type);

        scheduledList.forEach(task -> {
            int taskFrequency = task.getTickFrequency();

            if(currentTick % taskFrequency == 0) {
                task.onSchedulerRun(event);
            }
        });
    }

    private void runWorldTasks(TickEvent event) {
        runTask(TickEvent.Type.WORLD, event);
    }

    private void runServerTasks(TickEvent event) {
        runTask(TickEvent.Type.SERVER, event);
    }

    private void runPlayerTasks(TickEvent event) {
        runTask(TickEvent.Type.PLAYER, event);
    }

    private void runClientTasks(TickEvent event) {
        runTask(TickEvent.Type.CLIENT, event);
    }

    private void runRenderTasks(TickEvent event) {
        runTask(TickEvent.Type.RENDER, event);
    }


}
