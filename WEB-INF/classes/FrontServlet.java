package etu1929.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import etu1929.framework.Mapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends jakarta.servlet.http.HttpServlet{
    HashMap<String,Mapping> MappingUrls;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURI());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURI());

    }
    
}
