package me.furkannm.advancedbot.exceptions;

public class ModuleException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4434766347986380619L;

	public ModuleException(String errorMessage){
        super("ModuleException : " + errorMessage);
    }

}
