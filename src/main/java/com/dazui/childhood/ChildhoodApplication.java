package com.dazui.childhood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

@SpringBootApplication
public class ChildhoodApplication implements EmbeddedServletContainerCustomizer{

	public static void main(String[] args) {
		SpringApplication.run(ChildhoodApplication.class, args);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(80);
	}
	
}
