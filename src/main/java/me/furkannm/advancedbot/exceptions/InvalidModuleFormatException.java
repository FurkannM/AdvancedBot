package me.furkannm.advancedbot.exceptions;

public class InvalidModuleFormatException extends ModuleException {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 4858257651871354886L;

	public InvalidModuleFormatException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();

        System.out.println("   Basic format : (module.yml)");
        System.out.println("   main: path.to.your.MainClass");
        System.out.println("   name: <NameOfYourModule>");
        System.out.println("   authors: <AuthorA> | <AuthorA, AuthorB>");
        System.out.println("   version: YourVersion");
    }
}

