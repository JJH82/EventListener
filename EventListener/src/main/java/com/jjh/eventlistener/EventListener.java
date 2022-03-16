package com.jjh.eventlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/com/jjh/eventlistener/repository/*.xml")
@EnableAspectJAutoProxy
public class EventListener {
    
    public static void main(String[] args) {
        SpringApplication.run(EventListener.class, args);    
    }
    
}