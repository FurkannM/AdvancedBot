package me.furkannm.advancedbot.events.other;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

	public static List<Command> commands = new ArrayList<Command>();
	
	public static void registerCommand(Command command) {
		commands.add(command);
	}
	
	public static Command getCommand(String command) {
		for(Command cmds: commands) {
			if(cmds.getCommand().equalsIgnoreCase(command)) {
				return cmds;
			} else {
				for(String aliases: cmds.getAliases()) {
					if(aliases.equalsIgnoreCase(command)) {
						return cmds;
					}
				}
			}
		}
		return null;
	}
}
