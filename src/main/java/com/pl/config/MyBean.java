package com.pl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.pl.service","com.pl.dao"})
public class MyBean {

}
