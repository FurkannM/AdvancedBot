package me.furkannm.advancedbot.core;

import me.furkannm.advancedbot.AdvancedBot;
import me.furkannm.advancedbot.console.Console;

public class Core extends AdvancedBot {

	private Core(String withToken) {
		super();
	}

	private static void initialize(String withToken) {
		new Core(withToken);
	}

	public static void main(String[] args) {
		initialize("NTM3NjkxNDEwMjk2NDcxNTcz.Dyo-hg.kb3zVRl0e-61auhmqam6iJroVXE");
		getApi().addEventListener(new CommandListener());
		System.out.println("Bot başlatıldı!");
		Console.loadConsole();
	}
}
