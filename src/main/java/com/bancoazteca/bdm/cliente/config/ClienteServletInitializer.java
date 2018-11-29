package com.bancoazteca.bdm.cliente.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Cesar M Orozco R
 *
 */
public class ClienteServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ClienteWebConfig.class };
	}

	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
