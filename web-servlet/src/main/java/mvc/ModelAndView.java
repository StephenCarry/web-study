package mvc;

import java.util.Map;

public class ModelAndView {
    Map<String,Object> model;

    String view;

    public ModelAndView(String view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }
}
