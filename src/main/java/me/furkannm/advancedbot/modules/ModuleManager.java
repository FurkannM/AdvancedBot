package me.furkannm.advancedbot.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.furkannm.advancedbot.AdvancedBot;
import me.furkannm.advancedbot.api.Configuration;
import me.furkannm.advancedbot.api.YamlConfiguration;
import me.furkannm.advancedbot.events.AdvancedEvent;
import me.furkannm.advancedbot.events.ModuleDisableEvent;
import me.furkannm.advancedbot.events.ModuleEnableEvent;
import me.furkannm.advancedbot.events.ModuleLoadEvent;
import me.furkannm.advancedbot.exceptions.InvalidModuleFormatException;

public class ModuleManager {

    private List<Module> modules;
    private List<ModuleLoader> loader;
    private final Map<String, Class<?>> classes = new HashMap<>();
    private AdvancedBot plugin;

    public ModuleManager(AdvancedBot plugin) {
        this.plugin = plugin;
        modules = new ArrayList<>();
        loader = new ArrayList<>();
        loadModulesFromFile();
    }

    public void loadModulesFromFile() {
        System.out.println("Loading modules...");
        File f = new File(plugin.getDataFolder(), "modules");
        if (!f.exists() && !f.mkdirs()) {
        	System.out.println("Cannot make modules folder!");
            return;
        }
        for(File x :f.listFiles()) {
        	if(!x.isDirectory() && x.getName().endsWith(".jar")) {
        		loadModule(x);
        	}
        }
    }

    public void loadModules() {
    	for(Module module :modules) {
    		module.onLoad();
    		AdvancedEvent.call(new ModuleLoadEvent(module));
    		System.out.println("Loading " + module.getDescription().getName() + "...");
    	}
    	System.out.println("Loaded " + modules.size() + " modules.");
    }

    public void enableModules() {
    	System.out.println("Enabling modules...");
        for(Module module: modules) {
        	module.onEnable();
        	AdvancedEvent.call(new ModuleEnableEvent(module));
        	module.setEnabled(true);
        	System.out.println("Enabling " + module.getDescription().getName() + "...");
        }
        System.out.println("Modules successfully enabled.");
    }

    public Module getModuleByName(String name){
    	for(Module m : modules) {
    		if(m.getDescription().getName().contains(name)) {
    			return m;
    		}
    	}
		return null;
       
    }

    private Configuration moduleDescription(JarFile jar) throws InvalidModuleFormatException, IOException {
        JarEntry entry = jar.getJarEntry("module.yml");
        if (entry == null) {
            throw new InvalidModuleFormatException("Module '" + jar.getName() + "' doesn't contains module.yml file");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)));
        Configuration data = new YamlConfiguration().load(reader);
        return data;
    }

    private void loadModule(File f) {
        Module module;
        try (JarFile jar = new JarFile(f)) {
            Configuration data = moduleDescription(jar);
            ModuleLoader moduleClassLoader = new ModuleLoader(this, data, f, this.getClass().getClassLoader());
            loader.add(moduleClassLoader);
            module = moduleClassLoader.getModule();
            module.setDataFolder(new File(f.getParent(), module.getDescription().getName()));
            module.setModuleFile(f);
            AdvancedEvent.call(new ModuleLoadEvent(module)); 
            modules.add(module);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void disableModules() {
    	System.out.println("Disabling modules...");
        for(Module module: modules) {
        	module.onDisable();
        	AdvancedEvent.call(new ModuleDisableEvent(module));
        	System.out.println("Disabling " + module.getDescription().getName() + "...");
        }
        for(ModuleLoader l:loader) {
        	try{
        		l.close();
        	}catch(IOException e) {
        		
        	}
        }
        System.out.println("Modules successfully disabled.");
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<ModuleLoader> getLoader() {
        return loader;
    }

    public void setLoader(List<ModuleLoader> loader) {
        this.loader = loader;
    }

    public Class<?> getClassByName(final String name) {
    	Class<?> gclass = null;
    	for(ModuleLoader l:loader) {
    		if(l.findClass(name, false)!= null){
    			gclass = l.findClass(name, false);
    			break;
    		}
    	}
        return classes.getOrDefault(name, gclass);
    }

    public void setClass(final String name, final Class<?> clazz) {
        classes.putIfAbsent(name, clazz);
    }

    public List<String> listJarYamlFiles(JarFile jar, String folderPath) {
        List<String> result = new ArrayList<>();

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String path = entry.getName();

            if (!path.startsWith(folderPath)) {
                continue;
            }

            if (entry.getName().endsWith(".yml")) {
                result.add(entry.getName());
            }

        }
        return result;
    }
    
    public static List<String> getModuleNames() {
    	List<String> list = new ArrayList<>();
    	for(Module m:AdvancedBot.getModuleManager().modules) {
    		list.add(m.getDescription().getName() + "(" + m.getDescription().getVersion() +")");
    	}
    	return list;
    }
}