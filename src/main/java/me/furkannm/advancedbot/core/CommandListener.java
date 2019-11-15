package me.furkannm.advancedbot.core;

import me.furkannm.advancedbot.events.AdvancedEvent;
import me.furkannm.advancedbot.events.other.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();
		String command = message.split(" ")[0];
		if(message.length()>command.length()) {
			String[] args = message.substring(command.length()+1).split(" ");
			AdvancedEvent.call(new CommandEvent(event, command, args, event.getMember(), event.getTextChannel()));
		} else {
			AdvancedEvent.call(new CommandEvent(event, command, new String[] {}, event.getMember(), event.getTextChannel()));
		}
	}
	
}
