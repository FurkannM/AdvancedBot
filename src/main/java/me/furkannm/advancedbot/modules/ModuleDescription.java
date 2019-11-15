package me.furkannm.advancedbot.modules;

import java.util.Arrays;
import java.util.List;

public final class ModuleDescription {

    private String main;
    private String name;
    private String version;
    private String description;
    private List<String> authors;

    public ModuleDescription() {}

    public ModuleDescription(String main, String name, String version, String description, List<String> authors) {
        this.main = main;
        this.name = name;
        this.version = version;
        this.description = description;
        this.authors = authors;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public String getMain() {
        return main;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public static class ModuleDescriptionBuilder{

        private ModuleDescription description;

        public ModuleDescriptionBuilder(String name){
            description = new ModuleDescription();
            description.setName(name);
        }

        public ModuleDescriptionBuilder withAuthor(String... authors){
            description.setAuthors(Arrays.asList(authors));
            return this;
        }

        public ModuleDescriptionBuilder withDescription(String desc){
            description.setDescription(desc);
            return this;
        }

        public ModuleDescriptionBuilder withVersion(String version){
            description.setVersion(version);
            return this;
        }

        public ModuleDescription build(){
            return description;
        }

    }
}