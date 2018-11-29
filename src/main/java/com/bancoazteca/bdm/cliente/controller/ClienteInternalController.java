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

import com.bancoazteca.bdm.cliente.bussiness.ClienteInternalComponent;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.dao.ContratoInterfazDAO;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaInternalEntityTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Cesar M Orozco R
 *
 */
@RestController
@RequestMapping(value="/controllerCliente")
public class ClienteInternalController {
	
	@Autowired
	private ClienteInternalComponent clienteInternal;
	@Autowired
	private ContratoInterfazDAO contratoInterfazDAO;
	
	
	/**
	 * @param path
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value= {"/internal/obtieneRequestInternal"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String obtieneRequest(@RequestBody String path, HttpServletRequest request) throws JsonProcessingException {
		String pathController = null;
		String jsonRequestCore = null;
		Map<String, Object> map = convertoRequestToMap(null, path);
		pathController = map.get("pathInternal").toString();
		String jsonRequest = contratoInterfazDAO.buscaRequestInternal(pathController);
		if(!jsonRequest.equalsIgnoreCase("No existe Request")) {			
			jsonRequestCore = jsonRequest;
		}else {
			jsonRequestCore = "{\"info\":\"No existe request con ese Path, ingrese manualmente el request\"}";
		}
		return jsonRequestCore;
	}
	
	/**
	 * @param entradaInternalEntityTO
	 * @param request
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value={"/internal/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionInternalURL(@RequestBody DatosEntradaInternalEntityTO entradaInternalEntityTO, HttpServletRequest request) throws JsonProcessingException {
		entradaInternalEntityTO.setPathInternal(validaPath(entradaInternalEntityTO.getPathInternal()));
		Map<String, Object> entradaEntityMap = convertoObjToMap(entradaInternalEntityTO);
		Map<String, Object> requestInternalMap = convertoRequestToMap(entradaInternalEntityTO, null);
		Map<String, Object> resultado = clienteInternal.ejecutaPeticionInternal(entradaEntityMap, requestInternalMap);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultado);
		if(json.equalsIgnoreCase("{ }")) {
			json = "";
		} 
		return json;
	}
	
	
	private Map<String, Object> convertoObjToMap(DatosEntradaInternalEntityTO entityTO){
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
	
	private Map<String, Object> convertoRequestToMap(DatosEntradaInternalEntityTO entityTO, String path){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestInternalTOMap = new HashMap<String, Object>();
		try {
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				requestInternalTOMap = mapper.readValue(entityTO.getRequestInternal(), new TypeReference<Map<String, Object>>(){});
			}else if(!ValidacionesUtils.isNullOrEmpty(path)){
				requestInternalTOMap = mapper.readValue(path, new TypeReference<Map<String, Object>>(){});
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
	
	private String validaPath(String path) {
		String pathSinEspacios = path.replace(" ", "");
		if(pathSinEspacios.startsWith("/")) {
			System.out.println("************** Si trae slahs y se lo quitamos");
			return pathSinEspacios.substring(1, pathSinEspacios.length());
		}
		return pathSinEspacios;
	}

}
