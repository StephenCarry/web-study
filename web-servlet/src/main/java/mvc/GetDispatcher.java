package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetDispatcher extends AbstractDispatcher {
    Object instance;

    Method method;

    String[] paramNames;

    Class<?>[] paramClasses;

    public GetDispatcher(Object instance, Method method, String[] paramNames, Class<?>[] paramClasses) {
        this.instance = instance;
        this.method = method;
        this.paramNames = paramNames;
        this.paramClasses = paramClasses;
    }

    public ModelAndView invoke(HttpServletRequest req, HttpServletResponse res) throws InvocationTargetException, IllegalAccessException {
        //获取方法参数
        Object[] arg = new Object[paramClasses.length];
        for (int i=0;i<paramClasses.length;i++) {
            String name = paramNames[i];
            Class<?> _class = paramClasses[i];
            if (_class == HttpServletRequest.class) {
                arg[i] = req;
            } else if (_class == HttpServletResponse.class) {
                arg[i] = res;
            } else if (_class == HttpSession.class) {
                arg[i] = req.getSession();
            } else if (_class == int.class || _class == Integer.class) {
                arg[i] = Integer.valueOf(this.getValue(req,name,"0"));
            } else if (_class == boolean.class || _class == Boolean.class) {
                arg[i] = Boolean.valueOf(this.getValue(req,name,"false"));
            } else if (_class == String.class) {
                arg[i] = this.getValue(req,name,"");
            } else {
                throw new RuntimeException("Missing handler for type: "+_class);
            }
        }
        return (ModelAndView) this.method.invoke(this.instance,arg);
    }

    private String getValue(HttpServletRequest req,String name,String value) {
        String s = req.getParameter(name);
        return s == null? value: s;
    }
}
