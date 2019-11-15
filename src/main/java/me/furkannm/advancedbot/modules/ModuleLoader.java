package me.furkannm.advancedbot.modules;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import me.furkannm.advancedbot.api.Configuration;
import me.furkannm.advancedbot.exceptions.InvalidModuleFormatException;
import me.furkannm.advancedbot.exceptions.InvalidModuleInheritException;

public class ModuleLoader extends URLClassLoader {
	
	    private final Map<String, Class<?>> classes = new HashMap<>();
	    private Module addon;
	    private ModuleManager loader;
	
	    public ModuleLoader(ModuleManager addonsManager, Configuration data, File path, ClassLoader parent)
	            throws InvalidModuleInheritException,
	            MalformedURLException,	       
	            InstantiationException,
	            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidModuleFormatException {
	        super(new URL[]{path.toURI().toURL()}, parent);
	
	        loader = addonsManager;
	
	        Class<?> javaClass = null;
	        try {
	            String mainClass = data.getString("main");
	            javaClass = Class.forName(mainClass, true, this);
	            if(mainClass.startsWith("me.furkannm.advancedbot")){
	                throw new InvalidModuleInheritException("Package declaration cannot start with 'me.furkannm.advancedbot'");
	            }
	        } catch (Exception e) {
	            System.out.println("Could not load '" + path.getName() + "' in folder '" + path.getParent() + "' - " + e.getMessage());
	        }
	
	        Class<? extends Module> addonClass;
	        try {
	            addonClass = javaClass.asSubclass(Module.class);
	        } catch (ClassCastException e) {
	            throw new InvalidModuleFormatException("Main class does not extend 'Addon'");
	        }
	
	        addon = addonClass.getDeclaredConstructor().newInstance();
	        addon.setDescription(asDescription(data));
	    }
	
	    private ModuleDescription asDescription(Configuration data) {
	    	ModuleDescription.ModuleDescriptionBuilder adb = new ModuleDescription.ModuleDescriptionBuilder(data.getString("name"))
	                .withVersion(data.getString("version"))
	                .withAuthor(data.getString("authors"));	  
	        return adb.build();
	    }
	
	    @Override
	    protected Class<?> findClass(String name) {
	        return findClass(name, true);
	    }
	
	    public Class<?> findClass(String name, boolean checkGlobal) {
	        if (name.startsWith("me.furkannm.advancedbot")) {
	            return null;
	        }
	        Class<?> result = classes.get(name);
	        if (result == null) {
	            if (checkGlobal) {
	                result = loader.getClassByName(name);
	            }
	            if (result == null) {
	                try {
	                    result = super.findClass(name);
	                } catch (ClassNotFoundException | NoClassDefFoundError e) {
	                    result = null;
	                }
	                if (result != null) {
	                    loader.setClass(name, result);
	                }
	            }
	            classes.put(name, result);
	        }
	        return result;
	    }
	
	    public Module getModule() {
	        return addon;
	    }
}


