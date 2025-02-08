package org.example.mpp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class MppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MppBackendApplication.class, args);
    }

}
