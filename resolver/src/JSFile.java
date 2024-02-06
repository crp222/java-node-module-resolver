import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class JSFile {
    
    List<ImportLine> importLines;
    List<String> codeLines;
    String path;
    String filename;

    public JSFile(String filepath) {
        codeLines = new LinkedList<>();
        try {
            String code = "";
            File f = new File(filepath);
            filename = f.getName();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                code += line+"\n";
                codeLines.add(line);
            }
            scanner.close();

            importLines = ImportLineParser.parse(code);

            codeLines.subList(0,importLines.size()).clear();
        }catch(Exception e){
            e.printStackTrace();
        };
    }

    /**
     * If you want to change the directory where this should search for modules, use Resolver.setModulesDirectory(...) !!!
     * If you want to change the prefix of the import paths use Resolver.setPrefix(...) !!! Default is "."
     */
    void resolveImports(Class<?> resolver) {
        try {
            if(Resolver.class.isAssignableFrom(resolver))  {
                Resolver resolverObject = (Resolver)resolver.getMethod("getInstance", null).invoke(null);
                for(ImportLine line : importLines){
                    resolverObject.resolve(line);
                }
            }else {
                throw new Exception("Given class is not instance of Resolver");
            }
        }catch(Exception e){
            e.printStackTrace();
        };
    }

    void writeOut(String path) {
        try{
            File f = new File(path);
            String filepath = path;
            if(f.isDirectory()){
                filepath = Path.join(path,filename);
            }
            File out = new File(filepath);
            FileWriter writer = new FileWriter(out);
            writer.write(this.toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(ImportLine e : importLines)
            res.append(e.toString());
        for(String l : codeLines)
            res.append(l+"\n");
        return res.toString();
    }
}
