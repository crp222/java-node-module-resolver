package com.app;

import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewHandler extends AbstractUrlBasedView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            String url = this.getUrl();
            ClassPathResource resource = new ClassPathResource("templates/"+url);
            String res_content = new String(resource.getInputStream().readAllBytes(),"UTF-8");
            response.getWriter().write(res_content);
    }

    
}
