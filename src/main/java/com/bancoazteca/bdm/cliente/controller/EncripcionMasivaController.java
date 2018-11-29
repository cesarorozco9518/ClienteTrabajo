package com.bancoazteca.bdm.cliente.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class EncripcionMasivaController {

	@RequestMapping(value = { "/encripcion"}, method =  RequestMethod.GET)
	public String inicioPage(Model model, HttpServletRequest request) {
		return "encripcion";
	}

}
