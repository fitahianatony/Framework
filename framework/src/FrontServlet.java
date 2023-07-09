package etu1929.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import etu1929.framework.Mapping;
import etu1929.framework.annotation.Url;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends jakarta.servlet.http.HttpServlet{
    HashMap<String,Mapping> urlMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            for (Class c : inPackage("test")){
                for (Method m : c.getDeclaredMethods()){
                    if(m.isAnnotationPresent(Url.class)){
                        urlMapping.put(m.getAnnotation(Url.class).url(), new Mapping(c.getName(), m.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public List<Class<?>> inPackage(String packageName) throws ClassNotFoundException, URISyntaxException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                classes.addAll(inDir(new File(resource.toURI()), packageName));
            }
        }
        return classes;
    }

    public  List<Class<?>> inDir(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(inDir(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        String url = req.getRequestURI();
        url = url.split("/")[url.split("/").length - 1];
        out.println(url);
        if(urlMapping.containsKey(url)){
            try {
                Object act = Class.forName(urlMapping.get(url).getClassName()).newInstance();
                ModelView mv = (ModelView)act.getClass().getDeclaredMethod(urlMapping.get(url).getMethod()).invoke(act);
               
               
                for (String key:mv.getData().keySet()){
                    Object value = mv.getData().get(key);
                    req.setAttribute(key,value);
                }
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(mv.getView());    
                requestDispatcher.forward(req,res);
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }
    
}
