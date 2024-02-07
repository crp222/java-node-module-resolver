package com.app.resolver.js_code_handling;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ImportLine {
    private List<String> modules;
    private boolean def; // default
    private String path;
    private boolean relative;
    private boolean core;
    private String original;
    private boolean resolved;

    public ImportLine() {
    }

    public ImportLine(List<String> modules, boolean def, String path, boolean relative, boolean core,String original) {
        this.modules = modules;
        this.def = def;
        this.path = path;
        this.relative = relative;
        this.core = core;
        this.original = original;
        this.resolved = false;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginal() {
        return original;
    }

    public List<String> getModules() {
        return this.modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    public boolean isDef() {
        return this.def;
    }

    public boolean getDef() {
        return this.def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRelative() {
        return this.relative;
    }

    public boolean getRelative() {
        return this.relative;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    public boolean isCore() {
        return this.core;
    }

    public boolean getCore() {
        return this.core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isResolved() {
        return resolved;
    }

 
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ImportLine)) {
            return false;
        }
        ImportLine importLine = (ImportLine) o;
        return Objects.equals(modules, importLine.modules) && def == importLine.def && Objects.equals(path, importLine.path) && relative == importLine.relative && core == importLine.core;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modules, def, path, relative, core);
    }


    /**
     * !!! \n is included !!!
     */
    @Override
    public String toString() {
        if(resolved == false)
            return original + "\n";

        StringBuilder res = new StringBuilder("import ");

        String fixed_path = "";
        if(!path.startsWith(".") && !path.matches("^[A-Z][:].*$") && !path.startsWith("/") && !path.startsWith("\\") && relative){ 
            fixed_path += "./";
        }

        fixed_path += path;
        Iterator<String> mIterator = modules.iterator();
        if(def == true){
            res.append(mIterator.next());
        }
        if(mIterator.hasNext()){
            if(def == true) 
                res.append(",{");
            else
                res.append("{");
            while (mIterator.hasNext()) {
                res.append(mIterator.next()).append(",");
            }
            res.deleteCharAt(res.length()-1); // remove hanging ","
            res.append("}");
        }
        res.append(" from ");
        res.append("\"").append(fixed_path).append("\"\n");
        return res.toString();
    }
    
}
