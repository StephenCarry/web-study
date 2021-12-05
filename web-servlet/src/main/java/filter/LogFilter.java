package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Servlet前执行
        System.out.println("Log: process "+((HttpServletRequest)servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
        //Servlet后执行
        System.out.println("Log: end process");
    }

}
