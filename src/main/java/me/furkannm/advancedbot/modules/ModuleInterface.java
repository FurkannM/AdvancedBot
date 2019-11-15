package me.furkannm.advancedbot.modules;

public interface ModuleInterface {
	void onEnable();
    default void onDisable() {}
    default void onLoad() {}
}
