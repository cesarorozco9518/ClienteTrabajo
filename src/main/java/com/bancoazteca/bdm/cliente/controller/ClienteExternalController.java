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

import com.bancoazteca.bdm.cliente.bussiness.ClienteExternalComponent;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaExternalEntityTO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.external.ResponseAuthAppTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/controllerCliente")
public class ClienteExternalController {
	
	@Autowired
	private ClienteExternalComponent externalComponent; 
	
	@RequestMapping(value= {"/external/ejecutapeticion"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionExternal(@RequestBody DatosEntradaExternalEntityTO datosEntradaExternalEntityTO, HttpServletRequest request) {
		System.out.println("** Entra al controller de ejecutaPeticionExternal::: ClienteExternalController **");
		request.getSession().invalidate();
		Map<String, Object> respuestaServicio = new HashMap<>();
		String respuestaController = null;
		datosEntradaExternalEntityTO.setCookie(request.getSession().getId());
		Map<String, Object> datosEntradaExternal = convertoObjToMap(datosEntradaExternalEntityTO);
		System.out.println("******************** El mapa es: " + datosEntradaExternal);
		ResponseAuthAppTO authAppTO = externalComponent.ejecutaLogin(datosEntradaExternal);
		if(ValidacionesUtils.isNullOrEmpty(authAppTO)) {
			respuestaController = "{\"error\":\"lo sentimos ocurrio un error al realizar login, intente de nuevo\"}";
		}else if(!ValidacionesUtils.isNullOrEmpty(authAppTO) && authAppTO.getCodigoOperacion().equalsIgnoreCase("0")) {
			System.out.println("******* La respuesta del login es: " + authAppTO.getCodigoOperacion());
			Map<String, Object> requestMap = convertoRequestToMap(datosEntradaExternalEntityTO);
			requestMap.put("tokenCode", authAppTO.getTokenCode());
			requestMap.put("aplicacion", "Android-7.0-1000-BD");
			respuestaServicio = externalComponent.ejecutaServicio(datosEntradaExternal, requestMap);
			System.out.println("************* La respuesta final es: " + respuestaServicio);
			respuestaController = responseMapToResponseStro(respuestaServicio);
		}else {
			respuestaController = "{\"error\":\"lo sentimos ocurrio un error inesperado\"}";
		}
		return respuestaController;
	}
	
	private Map<String, Object> convertoObjToMap(DatosEntradaExternalEntityTO datosEntradaExternalEntityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> entityTOMap = new HashMap<String, Object>();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(datosEntradaExternalEntityTO);
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
	
	private Map<String, Object> convertoRequestToMap(DatosEntradaExternalEntityTO entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestInternalTOMap = new HashMap<String, Object>();
		try {
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				requestInternalTOMap = mapper.readValue(entityTO.getRequest(), new TypeReference<Map<String, Object>>(){});
				requestInternalTOMap.put("tokenCode", entityTO.getTokenCode());
				System.out.println("*************************************** El request final es: " + requestInternalTOMap);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestInternalTOMap;
	}
	
	private String responseMapToResponseStro(Map<String, Object> response) {
		String responseTO = null;
		try {
			responseTO = new ObjectMapper().writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseTO;
	}

}
