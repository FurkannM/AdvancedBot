package me.furkannm.advancedbot.events.other;

import me.furkannm.advancedbot.events.AdvancedEvent;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandEvent extends AdvancedEvent {

	private MessageReceivedEvent event;
	private String command;
	private String[] args;
	private Member member;
	private GuildChannel channel;

	public CommandEvent(MessageReceivedEvent event, String command, String[] args, Member member, GuildChannel channel) {
		this.event = event;
		this.command = command;
		this.args = args;
		this.member = member;
		this.channel = channel;
		
		Command cmd = CommandManager.getCommand(command);
		if(cmd!=null) {
			cmd.onCommand(this);
		}
	}
	
	public MessageReceivedEvent getEvent() {
		return event;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public Member getMember() {
		return member;
	}
	
	public GuildChannel getChannel() {
		return channel;
	}
	
}
