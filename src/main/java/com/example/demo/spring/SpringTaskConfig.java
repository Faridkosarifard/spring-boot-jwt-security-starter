package com.example.demo.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 5:13 PM
 */
@Configuration
@EnableScheduling
@ComponentScan({ "com.example.demo.task" })
public class SpringTaskConfig {

}

