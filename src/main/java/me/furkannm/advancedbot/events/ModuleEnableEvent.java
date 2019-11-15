package me.furkannm.advancedbot.events;

import me.furkannm.advancedbot.modules.Module;

public class ModuleEnableEvent extends AdvancedEvent{

	private Module module;
	
	public ModuleEnableEvent(Module module) {
		this.module = module;
	}
	
	public Module getModule() {
		return module;
	}
}
