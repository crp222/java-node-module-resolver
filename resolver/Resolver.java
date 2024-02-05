import java.util.HashMap;
import java.util.Map;

public abstract class Resolver {

    private static String modulesDirectory = "./node_modules";

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

    public Map<String,String> getModuleMap() {
        return moduleMap;
    }

    public abstract void resolve(ImportLine line);
}
