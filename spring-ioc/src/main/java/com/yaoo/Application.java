package com.yaoo;

import com.yaoo.serivce.UserService;
import com.yaoo.pojo.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.ZoneId;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args) {
//        Application.xmlRun();
        Application.annotationRun();
    }

    private static void annotationRun() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.run();
        User user1 = applicationContext.getBean(User.class);
        User user2 = applicationContext.getBean(User.class);
        if (user1 == user2) System.out.println("yes");
        else System.out.println("no");
        try {
            user1.print();
            user2.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void xmlRun() {
        //按需创建
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml.destory"));
        User users = beanFactory.getBean(User.class);
        System.out.println(users.getName());
        //一次性创建
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml.destory");
        User user = applicationContext.getBean(User.class);
        System.out.println(user.getName());
        UserService userService = applicationContext.getBean(UserService.class);
        userService.run();
    }

    @Bean
    @Qualifier("utc8")
    @Profile("test")
    ZoneId createZoneId() {
        return ZoneId.of("UTC+08:00");
    }
}
