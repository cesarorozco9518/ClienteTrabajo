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

import com.bancoazteca.bdm.cliente.bussiness.ClienteMicroserviciosComponent;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.dao.ContratoInterfazDAO;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaMicroservicioEntityTO;
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
public class ClienteMicroserviciosController {
	
	@Autowired
	private ClienteMicroserviciosComponent microserviciosComponent;
	@Autowired
	private ContratoInterfazDAO contratoInterfazDAO;
	
	
	/**
	 * @param microservicioEntityTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value= {"/micro/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionMicro(@RequestBody DatosEntradaMicroservicioEntityTO microservicioEntityTO, HttpServletRequest request) {
		microservicioEntityTO.setPathMicro(validaPath(microservicioEntityTO.getPathMicro()));
		Map<String, Object> entradaEntityMap = convertoObjToMap(microservicioEntityTO);
		Map<String, Object> requestMicroMap = convertoRequestToMap(microservicioEntityTO, null);
		String resultado = microserviciosComponent.ejecutaPeticionMicro(entradaEntityMap, requestMicroMap);		
		return resultado;
	}
	
	
	/**
	 * @param path
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value= {"/micro/obtieneRequestMicro"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String obtieneRequest(@RequestBody String path, HttpServletRequest request) throws JsonProcessingException {
		String pathController = null;
		String jsonRequestCore = null;
		Map<String, Object> map = convertoRequestToMap(null, path);
		pathController = map.get("pathMicro").toString();
		String jsonRequest = contratoInterfazDAO.buscaRequestMicro(pathController);
		if(!jsonRequest.equalsIgnoreCase("No existe Request")) {
			Map<String, Object> mapRequestMicro = convertoRequestToMap(null, jsonRequest);
			mapRequestMicro.remove("sistema");
			mapRequestMicro.remove("llaveSistema");
			mapRequestMicro.remove("ip");
			mapRequestMicro.remove("timeStamp");
			mapRequestMicro.remove("aplicacion");
			mapRequestMicro.put("icuCliente", "cede8c790070447abbf52962616e7e8e");
			mapRequestMicro.remove("tokenCode");
			jsonRequestCore = new ObjectMapper().writeValueAsString(mapRequestMicro);
		}else {
			jsonRequestCore = "{\"info\":\"No existe request con ese Path, ingrese manualmente el request\"}";
		}
		return jsonRequestCore;
	}
	
	private Map<String, Object> convertoObjToMap(DatosEntradaMicroservicioEntityTO entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> entityTOMap = new HashMap<String, Object>();
		entityTOMap = mapper.convertValue(entityTO, Map.class);
		return entityTOMap;
	}
	
	private Map<String, Object> convertoRequestToMap(DatosEntradaMicroservicioEntityTO entityTO, String path){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestMicroTOMap = new HashMap<String, Object>();
		try {
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				requestMicroTOMap = mapper.readValue(entityTO.getRequestMicro(), new TypeReference<Map<String, Object>>(){});
			}else if(!ValidacionesUtils.isNullOrEmpty(path)){
				requestMicroTOMap = mapper.readValue(path, new TypeReference<Map<String, Object>>(){});
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestMicroTOMap;
	}
	
	private String validaPath(String path) {
		String pathSinEspacios = path.replace(" ", "");
		if(pathSinEspacios.startsWith("/")) {
			return pathSinEspacios.substring(1, pathSinEspacios.length());
		}
		return pathSinEspacios;
	}

}
