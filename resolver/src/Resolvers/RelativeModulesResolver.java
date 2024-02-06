package resolvers;

import js_code_handling.ImportLine;
import utils.PackageJsonHandler;
import utils.Path;

public class RelativeModulesResolver extends Resolver{

    public static RelativeModulesResolver instance;

    private RelativeModulesResolver() {
        super();
    };

    public static RelativeModulesResolver getInstance() {
        if(instance == null)
            instance = new RelativeModulesResolver();
        return instance;
    }

    @Override
    public void resolve(ImportLine line) {
        String search_dir = line.getRelative() ? "." : Resolver.getModulesDirectory();
        String module = Path.exist(
            Path.join(search_dir,line.getPath()+".js"),
            Path.join(search_dir,line.getPath()+".json"),
            Path.join(search_dir,line.getPath()+".node"),
            Path.join(search_dir,line.getPath(),"index.js"),
            Path.join(search_dir,line.getPath(),"index.json"),
            Path.join(search_dir,line.getPath(),"index.node")
        );
        if(module != null){
            moduleMap.put(line.getPath(), module);
            line.setPath(Resolver.getPrefix()+module);
            return;
        }
        String package_json_path = Path.exist(Path.join(search_dir,line.getPath(),"package.json"));
        if(package_json_path != null){
            try {
                PackageJsonHandler package_json = new PackageJsonHandler(package_json_path);
                if(package_json.getMain() != null) {
                    String module_path = Path.join(search_dir,line.getPath(),package_json.getMain());
                    if(!module_path.endsWith(".js"))
                        module_path += ".js";
                    moduleMap.put(line.getPath(),module_path);
                    line.setPath(Resolver.getPrefix()+module_path);
                }
                    
            }catch(Exception e){
                e.printStackTrace();
            };
        }
        
    }
    
}
