package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Milad Tehrany
 * Date: 11/14/23
 * Time: 1:38 AM
 */

@Configuration
public class SpringConfiguration {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Bean
    public ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
