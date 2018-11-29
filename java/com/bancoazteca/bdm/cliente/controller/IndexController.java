package com.bancoazteca.bdm.cliente.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Cesar M Orozco R
 *
 */
@Controller
public class IndexController {
	
	private static final Logger log = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String printWelcome(Model model, HttpServletRequest request) {		
		return "login";
	}
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public String printCliente(Model model, HttpServletRequest request) {		
		return "index";
	}

}
