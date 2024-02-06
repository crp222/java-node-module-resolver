
import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class PackageJsonHandler {
    private String name;
    private String main;
    private Map<String,String> exports;

    public PackageJsonHandler(String path) throws Exception{
        File f = new File(path);
        if(!f.getName().equals("package.json")){
            throw new Exception("Given file is not a package.json");
        }
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if(line.startsWith("\"name\"") && name == null) {
                name = line.split(":")[1].replaceAll("[\",]","");
            }
            if(line.startsWith("\"main\"") && main == null) {
                main = line.split(":")[1].replaceAll("[\",]","");
            }
        }
        scanner.close();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain() {
        return this.main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Map<String,String> getExports() {
        return this.exports;
    }

    public void setExports(Map<String,String> exports) {
        this.exports = exports;
    }

}
