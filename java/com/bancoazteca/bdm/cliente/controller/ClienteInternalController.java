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

import com.bancoazteca.bdm.bussiness.ClienteInternalComponent;
import com.bancoazteca.bdm.entity.DatosEntradaInternalEntityTO;
import com.fasterxml.jackson.core.JsonParseException;
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
	
	@RequestMapping(value={"/internal/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionInternalURL(@RequestBody DatosEntradaInternalEntityTO entradaInternalEntityTO, HttpServletRequest request) {
		Map<String, Object> requestMap = convertoToMap(entradaInternalEntityTO);
		System.out.println("******************************** El mapa es: " + requestMap);
		String resultado = clienteInternal.ejecutaPeticionInternal(requestMap);
		return resultado;
	}
	
	private Map<String, Object> convertoToMap(DatosEntradaInternalEntityTO entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String jsonObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entityTO);
			map = mapper.readValue(jsonObject, new TypeReference<Map<String, String>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
