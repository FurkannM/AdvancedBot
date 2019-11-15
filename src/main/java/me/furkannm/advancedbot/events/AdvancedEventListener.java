package me.furkannm.advancedbot.events;

import java.util.ArrayList;
import java.util.List;

import me.furkannm.advancedbot.events.AdvancedEvent;
import me.furkannm.advancedbot.events.other.CommandEvent;

public abstract class AdvancedEventListener {
	public static List<AdvancedEventListener> advancedEventsListener = new ArrayList<>();
	public static List<AdvancedEventListener> getEventsListener() {
		return advancedEventsListener;
	}
	public static void registerListener(AdvancedEventListener advancedlistener) {
		advancedEventsListener.add(advancedlistener);
	}
	
	public abstract void onEvent(AdvancedEvent e);
	
	public abstract void onCommandEvent(CommandEvent e);
}
