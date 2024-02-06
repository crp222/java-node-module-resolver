package com.app.resolver.js_code_handling;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.app.resolver.resolvers.Resolver;
import com.app.resolver.utils.Path;

public class JSFile {
    
    List<ImportLine> importLines;
    List<String> codeLines;
    String path;
    String filename;

    public JSFile(File file) {
        codeLines = new LinkedList<>();
        try {
            if(!file.exists())
                throw new Exception("File is not exist!");
            
            String code = "";
            filename = file.getName();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                code += line+"\n";
                codeLines.add(line);
            }
            scanner.close();

            importLines = ImportLineParser.parse(code);

            codeLines.removeIf(line -> line.startsWith("import "));
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        };
    }

    public JSFile(String filepath) {
        this(new File(filepath));
    }

    public List<ImportLine> getImportLines() {
        return importLines;
    }

    public void setImportLines(List<ImportLine> importLines) {
        this.importLines = importLines;
    }

    /**
     * If you want to change the directory where this should search for modules, use Resolver.setModulesDirectory(...) !!!
     * If you want to change the prefix of the import paths use Resolver.setPrefix(...) !!! Default is "."
     */
    public void resolveImports(Class<?> resolver) {
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

    public void writeOut(String path) {
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
