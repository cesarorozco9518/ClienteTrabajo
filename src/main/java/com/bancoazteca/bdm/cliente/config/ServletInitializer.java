package com.bancoazteca.bdm.cliente.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.bancoazteca.bdm.application.ClienteBAZDigitalApplication;

public class ServletInitializer extends SpringBootServletInitializer {

    public ServletInitializer() {
        super();
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(ClienteBAZDigitalApplication.class);
    }

}
