package me.furkannm.advancedbot.console;

import java.util.Scanner;

import me.furkannm.advancedbot.AdvancedBot;

public class Console {

	private static Scanner scanner;
	
	public static void loadConsole() {
		scanner = new Scanner(System.in);
		while(true) {
			if(scanner.hasNext()) {
				String command = scanner.next();
				if (command.equalsIgnoreCase("reload")) {
					AdvancedBot.getModuleManager().disableModules();
					AdvancedBot.getModuleManager().loadModules();
					AdvancedBot.getModuleManager().enableModules();
				} else if (command.equalsIgnoreCase("stop")) {
					System.exit(0);
				}
			}
		}
	}
}
