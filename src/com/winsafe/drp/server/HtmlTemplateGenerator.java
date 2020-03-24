package com.winsafe.drp.server;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
 * @author: jelli 
 * @version:2009-9-22 下午12:47:56 
 * @copyright:www.winsafe.cn
 */
public class HtmlTemplateGenerator {
	Configuration cfg = null;    
    
    public HtmlTemplateGenerator(String templatePath) throws IOException {    
        cfg = new Configuration();    
        cfg.setDefaultEncoding("UTF-8");    
        cfg.setDirectoryForTemplateLoading(new File(templatePath));    
        cfg.setObjectWrapper(new DefaultObjectWrapper());    
    }    
        
    /**   
     * 生成静态文件   
     * @param ftlTemplate ftl模版文件   
     * @param contents    ftl要用到的动态内容   
     * @param savePath    文件保存路径   
     * @param saveFilename 保存文件名   
     * @throws IOException   
     * @throws TemplateException   
     */   
    public String create(String ftlTemplate, Map contents) throws IOException, TemplateException {    
        Template temp = cfg.getTemplate(ftlTemplate);        
       
        StringWriter out = new StringWriter(); 
        temp.process(contents, out);   
        out.flush();    
        out.close();
        return out.toString();
    }    
    
    public static void main(String[] args) throws Exception{
    	HtmlTemplateGenerator ht = new HtmlTemplateGenerator("e:\\temp");
    	Map map = new HashMap();
    	map.put("username", "test");
    	String s = ht.create("careers.ftl", map);
    	System.out.println(s);
    }
}
