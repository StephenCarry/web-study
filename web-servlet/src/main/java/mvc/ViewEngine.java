package mvc;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;

public class ViewEngine {
    private final PebbleEngine engine;

    public ViewEngine(ServletContext servletContext) {
        ServletLoader loader = new ServletLoader(servletContext);
        loader.setCharset("UTF-8");
        loader.setPrefix("/WEB-INF/templates");
        loader.setSuffix("");
        this.engine = new PebbleEngine.Builder()
                .autoEscaping(true)
                .cacheActive(false)
                .loader(loader)
                .build();
    }

    public void render(Writer writer, ModelAndView mvc) throws IOException {
        PebbleTemplate template = this.engine.getTemplate(mvc.view);
        template.evaluate(writer,mvc.model);
    }
}
