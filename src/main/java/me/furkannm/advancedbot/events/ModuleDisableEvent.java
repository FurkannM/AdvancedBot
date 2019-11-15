package me.furkannm.advancedbot.events;

import me.furkannm.advancedbot.modules.Module;

public class ModuleDisableEvent extends AdvancedEvent{

	private Module module;
	
	public ModuleDisableEvent(Module module) {
		this.module = module;
	}
	
	public Module getModule() {
		return module;
	}
}
