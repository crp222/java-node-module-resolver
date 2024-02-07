package com.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.app.resolver.js_code_handling.ImportLine;
import com.app.resolver.js_code_handling.JSFile;
import com.app.resolver.resolvers.RelativeModulesResolver;

import jakarta.servlet.http.HttpServletRequest;

public class ResourceHandler implements ResourceResolver {


    /**
     * You cannot load local resource.
     * This function converts local resource paths to relative resource paths.
     * @param jsFile
     */
    private void fixLocalImports(JSFile jsFile) {
        for(ImportLine line : jsFile.getImportLines()){
            if(line.getPath().matches("^[A-Z][:].*$")) {
                line.setPath("/web"+line.getPath().split("/web")[1]);
            }
        }
    }

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations,
            ResourceResolverChain chain) {
                
        try {
            Resource resource = new ClassPathResource(requestPath);
            if(!resource.exists())
                throw new FileNotFoundException("Resource ["+requestPath+"] doesn't exist!");
            if(resource.getFilename().endsWith(".js")){
                JSFile jsFile = new JSFile(resource.getFile());
                jsFile.resolveImports(RelativeModulesResolver.class);
                fixLocalImports(jsFile);
                File tempFile = File.createTempFile("temp", ".js");
                Files.write(tempFile.toPath(), jsFile.toString().getBytes());
                return new FileSystemResource(tempFile);
            }else {
                return resource;
            }
        }catch(FileNotFoundException err){
            return null;
        }catch(Exception err){
            return null;
        }
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        System.out.println(resourcePath);
        throw new UnsupportedOperationException("Unimplemented method 'resolveUrlPath'");
    }
    
}
