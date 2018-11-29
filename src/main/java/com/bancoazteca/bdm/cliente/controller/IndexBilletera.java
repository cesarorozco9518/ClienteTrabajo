package com.bancoazteca.bdm.cliente.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexBilletera {
	
	@RequestMapping(value = { "/billetera"}, method =  RequestMethod.GET)
	public String inicioPage(Model model, HttpServletRequest request) {
		return "billetera";
	}
	
	@RequestMapping(value = { "/indexBilletera"}, method =  RequestMethod.GET)
	public String peticionPage(Model model, HttpServletRequest request) {
		return "billeteraIndex";
	}

}
