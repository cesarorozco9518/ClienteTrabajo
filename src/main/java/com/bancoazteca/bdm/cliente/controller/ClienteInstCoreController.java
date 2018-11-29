package com.bancoazteca.bdm.cliente.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bancoazteca.bdm.cliente.bussiness.ClienteInstaCoreComponent;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaInstaEntityTO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/controllerCliente")
public class ClienteInstCoreController {
	
	@Autowired
	private ClienteInstaCoreComponent instaCoreComponent;
	
	
	@RequestMapping(value={"/intantaneo/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticion(@RequestBody DatosEntradaInstaEntityTO datosEntradaInstaEntityTO, HttpServletRequest request) {
		request.getSession().invalidate();
		ResponseLoginAuthTO responseLoginAuthTO = new ResponseLoginAuthTO();
		String respuesta = null;
		responseLoginAuthTO = instaCoreComponent.generaLogin(convertoObjToMap(datosEntradaInstaEntityTO));
		if(!ValidacionesUtils.isNullOrEmpty(responseLoginAuthTO) && responseLoginAuthTO.getCodigoOperacion().equals("0")) {
			datosEntradaInstaEntityTO.setTokenCode(responseLoginAuthTO.getTokenCode());
			datosEntradaInstaEntityTO.setCookie(responseLoginAuthTO.getNombre());
			respuesta = instaCoreComponent.ejecutaServicio(datosEntradaInstaEntityTO);
		}
		return respuesta;
	}
	
	private Map<String, Object> convertoObjToMap(DatosEntradaInstaEntityTO entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> entityTOMap = new HashMap<String, Object>();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entityTO);
			entityTOMap = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entityTOMap;
	}

}
