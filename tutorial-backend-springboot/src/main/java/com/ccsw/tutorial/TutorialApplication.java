package com.ccsw.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TutorialApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "Europe/Madrid");
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));

        SpringApplication.run(TutorialApplication.class, args);
    }

}
