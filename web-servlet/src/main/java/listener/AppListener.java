package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sec) {
        System.out.println("WebApp init");
    }
    public void contextDestroyed (ServletContextEvent sec) {
        System.out.println("WebApp destroy");
    }
}
