package me.furkannm.advancedbot.events.other;

import net.dv8tion.jda.api.Permission;

public abstract class Command {
	public abstract String getCommand();
	public abstract String[] getAliases();
	public abstract String getDescription();
	public abstract Permission[] getPermissions();
	public abstract CommandTYPE getCommandType();
	
	public abstract void onCommand(CommandEvent e);
}
