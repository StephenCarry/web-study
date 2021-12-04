package mvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PostDispatcher extends AbstractDispatcher {
    Object instance;

    Method method;

    Class<?>[] paramClasses;

    ObjectMapper objectMapper;

    public PostDispatcher(Object instance, Method method, Class<?>[] paramClasses, ObjectMapper objectMapper) {
        this.instance = instance;
        this.method = method;
        this.paramClasses = paramClasses;
        this.objectMapper = objectMapper;
    }

    public ModelAndView invoke(HttpServletRequest req, HttpServletResponse res) throws InvocationTargetException, IllegalAccessException {
        //获取方法参数
        Object[] arg = new Object[paramClasses.length];
        for (int i=0;i<paramClasses.length;i++) {
            Class<?> _class = paramClasses[i];
            if (_class == HttpServletRequest.class) {
                arg[i] = req;
            } else if (_class == HttpServletResponse.class) {
                arg[i] = res;
            } else if (_class == HttpSession.class) {
                arg[i] = req.getSession();
            } else {
                try {
                    BufferedReader reader = req.getReader();
                    arg[i] = this.objectMapper.readValue(reader, _class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return (ModelAndView) this.method.invoke(this.instance,arg);
    }
}
