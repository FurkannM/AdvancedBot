package me.furkannm.advancedbot;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.security.auth.login.LoginException;

import me.furkannm.advancedbot.modules.ModuleManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class AdvancedBot {

	private static AdvancedBot instance;
	private static ModuleManager moduleManager = null;
	private static JDA API;

	public AdvancedBot() {
		try {
			API = new JDABuilder().build();
		} catch (LoginException e) {
			e.printStackTrace();
			return;
		}
		initialize();
	}
	
	public AdvancedBot(String withToken) {
        try {
			API = new JDABuilder().setToken(withToken).build();
		} catch (LoginException e) {
			e.printStackTrace();
			return;
		}
		initialize();
	}

	private void initialize() {
		instance = this;
		moduleManager = new ModuleManager(this);
		moduleManager.loadModules();
		moduleManager.enableModules();
	}
	
	public static JDA getApi() {
	    return API;
    }

	public static AdvancedBot getInstance() {
		return instance;
	}
	
	public static ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public File getDataFolder() {
		String path = new File(AdvancedBot.class.getProtectionDomain().getCodeSource().getLocation().toString()).getParent().substring(6);
		String decodedPath = path;
		try {
			decodedPath = URLDecoder.decode(decodedPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(decodedPath);
		return new File(decodedPath);
	}
}
