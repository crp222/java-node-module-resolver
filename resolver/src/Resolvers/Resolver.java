package resolvers;

import java.util.HashMap;
import java.util.Map;

import js_code_handling.ImportLine;

public abstract class Resolver {

    private static String modulesDirectory = "../node_modules";
    private static String prefix = ".";

    protected Map<String,String> moduleMap;

    protected Resolver() {
        moduleMap = new HashMap<>();
    }

    public static Resolver getInstance() {
        return null;
    }

    public static void setModulesDirectory(String modulesDirectory) {
        Resolver.modulesDirectory = modulesDirectory;
    }

    public static String getModulesDirectory() {
        return modulesDirectory;
    }

    public static void setPrefix(String prefix) {
        Resolver.prefix = prefix;
    }

    public static String getPrefix() {
        return prefix;
    }

    public Map<String,String> getModuleMap() {
        return moduleMap;
    }

    public abstract void resolve(ImportLine line);
}
