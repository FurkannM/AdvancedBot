package me.furkannm.advancedbot.events;

import java.util.ArrayList;
import java.util.List;

import me.furkannm.advancedbot.events.other.CommandEvent;

public abstract class AdvancedEvent {
	public static List<AdvancedEvent> advancedEvent = new ArrayList<>();
	public static List<AdvancedEvent> getEventsList() {
		return advancedEvent;
	}
	
	public AdvancedEvent() {
		advancedEvent.add(this);
	}
	
	public static void call(AdvancedEvent e) {
		for(AdvancedEventListener ael : AdvancedEventListener.getEventsListener()) {
			ael.onEvent(e);
			if(e instanceof CommandEvent) {
				ael.onCommandEvent((CommandEvent) e);
			}
		}
	}
}
