package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/sign")
public class SignServlet extends HttpServlet {
    private final Map<String, String> map = new HashMap<String, String>() {{
        put("yaoo","yaoo123");
    }};

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter printWriter = res.getWriter();
        printWriter.write("<h1>Sign in</h1>");
        printWriter.write("<form action=\"/web_servlet_war/\" method=\"post\">");
        printWriter.write("<p>User:<input name=\"username\"></p>");
        printWriter.write("<p>Password:<input password=\"password\" type=\"password\"</p>");
        printWriter.write("<p><button type=\"submit\">Sign In</button></p>");
        printWriter.write("</form>");
        printWriter.flush();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String result = map.get(username);
        if (result!=null && result.equals(password)) {
            req.getSession().setAttribute("username", username);
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
