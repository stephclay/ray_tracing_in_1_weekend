package com.wombatsw.raytracing;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Basic Spring Boot startup
 */
@SpringBootApplication
public class Application {
    public static void main(String... args) {
        new SpringApplicationBuilder(Application.class)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false)
                .run(args)
                .close();
    }
}