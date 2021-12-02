package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(urlPatterns = "/basic")
public class BasicServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String name = req.getParameter("name");
        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("X-Powered-By", "Java EE Servlet");
        PrintWriter printWriter = res.getWriter();
        printWriter.write("<h1>Welcome,"+name+"</h1>");
        printWriter.flush();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = (String) req.getSession().getAttribute("username");
        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("X-Powered-By", "Java EE Servlet");
        PrintWriter printWriter = res.getWriter();
        printWriter.write("<h1>Welcome, "+(username!=null?username:"")+"</h1>");
        printWriter.flush();
    }

}
