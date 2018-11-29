package com.bancoazteca.bdm.cliente.bussiness;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bancoazteca.bdm.serializer.SerealizerClass;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteMicroserviciosComponent {

	private static final Logger log = Logger.getLogger(ClienteMicroserviciosComponent.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ejecutaPeticionMicro(Map<String, Object> objetoRequest, Map<String, Object> requestMicro) {
		log.info("El objeto es: " + objetoRequest);
		Map<String, Object> resultado = null;
		String stringResultado = null;
		SerealizerClass serealizerClass = new SerealizerClass();
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			String requestString = mapper.writeValueAsString(requestMicro);
			String url = generaURL(objetoRequest.get("ipServerMicro").toString(), objetoRequest.get("portServerMicro").toString(), objetoRequest.get("pathMicro").toString());
			requestMicro.put("sistema", "BAZDIGITALAPP");
			requestMicro.put("llaveSistema", "e2d5f12dace2f38f3f7b4540d5042f7f");
			requestMicro.put("ip", "10.51.113.30");
			requestMicro.put("aplicacion", "Android-7.0-1000-BD");
			requestMicro.put("icuCliente", objetoRequest.get("icuCliente").toString());		
			requestString = serealizerClass.serealizerObjToStrEncrypt(requestMicro);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			HttpEntity<String> entity = new HttpEntity<String>(requestString, headers);
			responseEntity = rt.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
			resultado = (Map) serealizerClass.serealizeStringEncryptToObj((String)responseEntity.getBody(), HashMap.class);
			stringResultado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stringResultado;
	}
	
	private String generaURL(String ip, String puerto, String url) {
		String pathCompleto = "http://"+ip+":"+puerto+"/"+url;		
		return pathCompleto;
	}

}
