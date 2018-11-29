package com.bancoazteca.bdm.cliente.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/", "/inicio" }, method =  RequestMethod.GET)
	public String inicioPage(Model model, HttpServletRequest request) {
		return "inicio";
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/coreLogin" }, method =  RequestMethod.GET)
	public String loginPage(Model model, HttpServletRequest request) {
		return "loginCliente";
	}
	
	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/cliente" }, method =  RequestMethod.GET)
	public String clientePage(Model model, HttpServletRequest request) {
		return "index";
	}

	
}
