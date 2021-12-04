package mvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Index;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private final List<Class<?>> controllers = new ArrayList<>();

    private final Map<String,GetDispatcher> getMappings = new HashMap<String,GetDispatcher>();

    private final Map<String,PostDispatcher> postMappings = new HashMap<String,PostDispatcher>();

    private ViewEngine viewEngine;

    @Override
    public void init() throws ServletException {
        this.viewEngine = new ViewEngine(getServletContext());
        this.scan();
    }

    private void scan() {
        //扫描获取所有带标记的类
        this.readController();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //扫描每个类的每个方法
        for (Class<?> c : this.controllers) {
            try {
                Object o = c.getConstructor().newInstance();
                for (Method method : o.getClass().getMethods()) {
                    if (method.getAnnotation(GetMapping.class) != null) {
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException("Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        String path = method.getAnnotation(GetMapping.class).value();
                        String[] paramNames = Arrays.stream(method.getParameters()).map(Parameter::getName).toArray(String[]::new);
                        this.getMappings.put(path, new GetDispatcher(o, method, paramNames, method.getParameterTypes()));
                    } else if (method.getAnnotation(PostMapping.class) != null) {
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException("Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        String path = method.getAnnotation(GetMapping.class).value();
                        this.postMappings.put(path, new PostDispatcher(o, method, method.getParameterTypes(), mapper));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readController() {
        this.controllers.add(Index.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req,resp,getMappings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req,resp,postMappings);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp, Map<String, ? extends AbstractDispatcher> mapping) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        //根据路径dispatcher
        String path = req.getRequestURI().substring(req.getContextPath().length());
        AbstractDispatcher dispatcher = mapping.get(path);
        if (dispatcher == null) {
            resp.sendError(404);
            return;
        }
        try {
            ModelAndView mvc = dispatcher.invoke(req,resp);
            if (mvc == null) {
                return;
            }
            if (mvc.view.startsWith("redirect:")) {
                resp.sendRedirect(mvc.view.substring(9));
                return;
            }
            PrintWriter writer = resp.getWriter();
            this.viewEngine.render(writer,mvc);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
