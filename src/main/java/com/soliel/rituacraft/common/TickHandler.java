package com.soliel.rituacraft.common;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TickHandler {
    private final Map<TickEvent.Type, List<Consumer<TickEvent>>> tickHandlers = new HashMap<>();
    private final Map<TickEvent.Type, Integer> tickAmounts = new HashMap<>();

    public void attachTickEvents(IEventBus eventBus) {
        eventBus.addListener(this::worldTick);
        eventBus.addListener(this::serverTick);
        eventBus.addListener(this::playerTick);
        eventBus.addListener(this::renderTick);
        eventBus.addListener(this::clientTick);

        tickAmounts.put(TickEvent.Type.WORLD, 0);
        tickAmounts.put(TickEvent.Type.SERVER, 0);
        tickAmounts.put(TickEvent.Type.PLAYER, 0);
        tickAmounts.put(TickEvent.Type.CLIENT, 0);
        tickAmounts.put(TickEvent.Type.RENDER, 0);
    }

    public void register(TickEvent.Type type, Consumer<TickEvent> consumer) {
        if(!tickHandlers.containsKey(type)) {
            tickHandlers.put(type, new ArrayList<>());
        }

        List<Consumer<TickEvent>> consumerList = tickHandlers.get(type);
        consumerList.add(consumer);
        tickHandlers.put(type, consumerList);
    }

    public int getCurrentTypeTickCount(TickEvent.Type type) {
        return tickAmounts.get(type);
    }

    private void tick(TickEvent.Type type, TickEvent event) {
        int tick = tickAmounts.get(type);
        tickAmounts.put(type, ++tick);
        List<Consumer<TickEvent>> handlerList = tickHandlers.get(type);
        handlerList.forEach(consumer -> consumer.accept(event));
    }

    private void worldTick(TickEvent.WorldTickEvent event) {
        tick(TickEvent.Type.WORLD, event);
    }

    private void serverTick(TickEvent.ServerTickEvent event) {
        tick(TickEvent.Type.SERVER, event);
    }

    private void playerTick(TickEvent.PlayerTickEvent event) {
        tick(TickEvent.Type.PLAYER, event);
    }

    private void renderTick(TickEvent.RenderTickEvent event) {
        tick(TickEvent.Type.RENDER, event);
    }

    private void clientTick(TickEvent.ClientTickEvent event) {
        tick(TickEvent.Type.CLIENT, event);
    }
}
