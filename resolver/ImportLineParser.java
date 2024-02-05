import java.util.ArrayList;
import java.util.List;

public class ImportLineParser {


    private static List<String> readImportLines(String code) {
        List<String> lines = new ArrayList<>();
        String[] parts = code.split("\n|;");
        for(String part : parts){
            if(part.startsWith("import")){
                lines.add(part);
            }
        }
        return lines;
    } 


    /**
     * <pre>
     * import A from "./asd" - true;
     * import {A} from "./asd" - false;
     * import A,{B,C} from "./asd" - true;
     * </pre>
     * @param code "A" | "{A}" | "A,{B,C}"
     * @return
     */
    private static boolean isDefaultImport(String code){
        return code.matches("^[A-Za-z].*$");
    }

    /**
     * <pre>
     * import A from "./asd" - [];
     * import {A} from "./asd" - [A];
     * import A,{B,C} from "./asd" - [B,C];
     * </pre>
     * @param code "A" | "{A}" | "A,{B,C}"
     * @return
     */
    private static List<String> getModules(String code) {
        int start = code.indexOf("{")+1;
        int end = code.indexOf("}");
        List<String> modules = new ArrayList<>();
        if(start == -1 || end == -1) {
            modules.add(code);
            return modules;
        }
        if(start != 1){
            String first = code.substring(0,start-1);
            first = first.replaceAll(",",""); // A,{B,C} -> A
            modules.add(first);
        }
        String important = code.substring(start,end);
        for(String e : important.split(",")){
           modules.add(e);
        }
        return modules;
    }

    private static boolean isRelative(String path){
        return path.matches("^[\\.\\\\/\\\\].*$");
    }

    // TODO
    private static boolean isCore(String path) {
        return false;
    }

    private static String preFormat(String str) {
        str = str.replaceAll(" |\"","");
        return str;
    }

    /**
     * ex: import A from "./module"
     * @param line ["A","./module"]
     */
    private static ImportLine createImportLine(String[] line,String full_line) throws Exception{
        if(line.length != 2){
            throw new Exception("Error with line: " + full_line);
        }
        String module = preFormat(line[0]);
        String path = preFormat(line[1]);
        if(path.charAt(0) == '.')
            path = path.substring(1,path.length());
        ImportLine importLine = new ImportLine(getModules(module),isDefaultImport(module),path,isRelative(path),isCore(path));
        return importLine;
    }

    public static List<ImportLine> parse(String code) throws Exception{
        List<ImportLine> list = new ArrayList<>();
        List<String> lines = readImportLines(code);
        for(String line : lines) {
            String fixed_line = line.substring(6,line.length()); // without 'import';
            String[] lineParts = fixed_line.split("from");
            ImportLine importLine = createImportLine(lineParts,line);
            list.add(importLine);
        }
        return list;
    }
}
