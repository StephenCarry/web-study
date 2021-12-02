package com.yaoo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PropertySource("config.properties")
public class User {
    @Value("${name:yaoo}")
    private String name;
    @Value("classpath:/login.txt")
    private Resource resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() throws IOException {
        System.out.println("hello "+name);
        System.out.println(resource.getInputStream().read());
    }

}
