package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDispatcher {
    public abstract ModelAndView invoke(HttpServletRequest req, HttpServletResponse res)
            throws InvocationTargetException, IllegalAccessException;
}
