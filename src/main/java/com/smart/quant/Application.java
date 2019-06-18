package com.smart.quant;

import java.net.URISyntaxException;
import java.util.LinkedList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws URISyntaxException {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		ctx.registerShutdownHook();
		ctx.start();
	}

}