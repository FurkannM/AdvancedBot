package me.furkannm.advancedbot.events;

import me.furkannm.advancedbot.modules.Module;

public class ModuleLoadEvent extends AdvancedEvent{

	private Module module;
	
	public ModuleLoadEvent(Module module) {
		this.module = module;
	}
	
	public Module getModule() {
		return module;
	}
}
