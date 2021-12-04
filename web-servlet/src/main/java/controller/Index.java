package controller;

import mvc.GetMapping;
import mvc.ModelAndView;

import java.util.Collections;

public class Index {
    @GetMapping("/index")
    public ModelAndView index(String name) {
        if (name == null) name = "friend";
        return new ModelAndView("index.html", Collections.singletonMap("name",name));
    }
}
