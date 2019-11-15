package me.furkannm.advancedbot.events.other;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandTYPE {

	public static List<CommandTYPE> cmdTypes = new ArrayList<CommandTYPE>();
	
	private String name;
	
	public CommandTYPE(String name) {
		this.name = name;
		cmdTypes.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public static CommandTYPE valueOf(String type_name) {
		CommandTYPE type = null;
		for(CommandTYPE ctype : cmdTypes) {
			if(ctype.getName().equalsIgnoreCase(type_name)) {
				type = ctype;
				break;
			}
		}
		return type;
	}
}
