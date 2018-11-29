package com.bancoazteca.bdm.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@ComponentScan({ "com.bancoazteca.bdm" })
public class ClienteBAZDigitalApplication extends SpringBootServletInitializer{
	
	private static Class<ClienteBAZDigitalApplication> applicationClass = ClienteBAZDigitalApplication.class;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {		
		return builder.sources(applicationClass);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClienteBAZDigitalApplication.class, args);
	}

}
