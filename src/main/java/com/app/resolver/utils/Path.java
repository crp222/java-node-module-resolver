package com.app.resolver.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Path {

    /**
     * <pre>
     * Remove useless '/'-s and '.'-s from the given string
     * Ex: "./asd///ad./d///a" -> "asd/ad/d/a"
     * </pre>
     * @param path 
     * @return fixed string
     */
    private static String fixPath(String path){
        String fixed_path = "";
        for(int i=0;i<path.length();i++) {
            char c = path.charAt(i);
            if(c == ' ')
                continue;
            if(c == '.') {
                if(i != path.length()-1 && path.charAt(i+1) != '/'){
                    fixed_path += c;
                }
                continue;
            }
            if(c == '/'){
                if(fixed_path.length() > 0 && fixed_path.charAt(fixed_path.length()-1) != '/'){
                    fixed_path += c; 
                }
                continue;
            }
            fixed_path += c;  
        }
        return fixed_path;
    }

    public static String join(String... paths) {
        String joined = "";
        List<String> files = new LinkedList<>();
        for(String path : paths){
            String fixed_path = fixPath(path);
            String[] path_parts = fixed_path.split("/");
            for(String path_part : path_parts){
                files.add(path_part);
            }
        }
        joined = String.join("/", files);
        return joined;
    }

    /**
     * 
     * @param paths
     * @return The first existing path
     */
    public static String exist(String... paths) {
        try {
            for(String path : paths){
                File f = new File(path);
                if(f.exists()){
                    return path;
                }
            }
            return null;
        }catch(Exception e){
            return null;
        }
    }
}
