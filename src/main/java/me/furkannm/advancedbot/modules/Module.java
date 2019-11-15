package me.furkannm.advancedbot.modules;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.furkannm.advancedbot.AdvancedBot;
import me.furkannm.advancedbot.api.Configuration;
import me.furkannm.advancedbot.api.YamlConfiguration;
import me.furkannm.advancedbot.events.AdvancedEventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Module implements ModuleInterface{

    private static final String ADDON_CONFIG_FILENAME = "config.yml";
    private boolean enabled;
    private ModuleDescription description;
    private Configuration config;
    private File dataFolder;
    private File file;

    public Module() {
        enabled = false;
    }

    public AdvancedBot getCore() {
        return AdvancedBot.getInstance();
    }

    public Configuration getConfig() {
        if (config == null) {
            config = loadYamlFile();
        }
        return config;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public ModuleDescription getDescription() {
        return description;
    }

    public File getFile() {
        return file;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private Configuration loadYamlFile() {
        File yamlFile = new File(dataFolder, ADDON_CONFIG_FILENAME);

        Configuration yamlConfig = null;
        if (yamlFile.exists()) {
            try {
                yamlConfig = new YamlConfiguration().load(yamlFile);
            } catch (Exception e) {
                System.out.println("Could not load config.yml: " + e.getMessage());
            }
        }
        return yamlConfig;
    }

    public void registerListener(ListenerAdapter listener) {
    	AdvancedBot.getApi().addEventListener(listener);
    }
    
    public void registerAdvListener(AdvancedEventListener listener) {
    	AdvancedEventListener.registerListener(listener);
    }

    public void saveConfig() {
        try {
        	new YamlConfiguration().save(getConfig(), new File(dataFolder, ADDON_CONFIG_FILENAME));
        } catch (IOException e) {
            System.out.println("Could not save config!");
        }
    }

    public void saveDefaultConfig() {
        saveResource(ADDON_CONFIG_FILENAME, false);
        config = loadYamlFile();
    }

    public void saveResource(String resourcePath, boolean replace) {
        saveResource(resourcePath, dataFolder, replace, false);
    }

    public void saveResource(String jarResource, File destinationFolder, boolean replace, boolean noPath) {
        if (jarResource == null || jarResource.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        jarResource = jarResource.replace('\\', '/');
        try (JarFile jar = new JarFile(file)) {
            JarEntry jarConfig = jar.getJarEntry(jarResource);
            if (jarConfig != null) {
                try (InputStream in = jar.getInputStream(jarConfig)) {
                    if (in == null) {
                        throw new IllegalArgumentException(
                                "The embedded resource '" + jarResource + "' cannot be found in " + jar.getName());
                    }
                    File outFile = new File(destinationFolder, jarResource);
                    if (noPath) {
                        outFile = new File(destinationFolder, outFile.getName());
                    }
                    outFile.getParentFile().mkdirs();
                    if (!outFile.exists() || replace) {
                        java.nio.file.Files.copy(in, outFile.toPath());
                    }
                }
            }
        } catch (IOException e) {
           	System.out.println(
                    "Could not save from jar file. From " + jarResource + " to " + destinationFolder.getAbsolutePath());
        }
    }

    public InputStream getResource(String jarResource) {
        if (jarResource == null || jarResource.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        jarResource = jarResource.replace('\\', '/');
        try (JarFile jar = new JarFile(file)) {
            JarEntry jarConfig = jar.getJarEntry(jarResource);
            if (jarConfig != null) {
                try (InputStream in = jar.getInputStream(jarConfig)) {
                    return in;
                }
            }
        } catch (IOException e) {
        	System.out.println("Could not open from jar file. " + jarResource);
        }
        return null;
    }

    public void setModuleFile(File f) {
        file = f;
    }

    public void setDataFolder(File file) {
        dataFolder = file;
    }

    public void setDescription(ModuleDescription desc) {
        description = desc;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Module getModuleByName(String name) {
        return AdvancedBot.getModuleManager().getModuleByName(name);
    }

    public void log(String string) {
        System.out.println(string);
    }
}
 