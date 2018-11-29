package com.bancoazteca.bdm.cliente.bussiness;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.encryptsecure.entity.beans.external.RequestAuthAppTO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.external.ResponseAuthAppTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteExternalComponent {
	
	private CookieStore cookieStore = new BasicCookieStore();
	HttpContext localContext = new BasicHttpContext();
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public ResponseAuthAppTO ejecutaLogin(Map<String, Object> datosEntrada) {
		RequestAuthAppTO authAppTO = new RequestAuthAppTO();
		ResponseAuthAppTO responseAuthAppTO = new ResponseAuthAppTO();
		authAppTO.setInfoHashActivation("eloy-2553a5586f517c0a163d051b83");
		authAppTO.setUsername(datosEntrada.get("usuario").toString());
		authAppTO.setThePass(datosEntrada.get("password").toString());
		authAppTO.setDispositivo("gs1089882");
		authAppTO.setKeyApp("JVCiOS");
		authAppTO.setAplicacion("IOS-IPAD-JVC 24.0");
		HttpPost postRequest = new HttpPost(generaURLLogin(datosEntrada));
		BasicClientCookie objCookie = new BasicClientCookie("JSESSIONID", datosEntrada.get("cookie").toString());
		localContext.setAttribute("http.cookie-store", cookieStore);
		String strTO = objToStrJson(authAppTO);
		try {
		StringEntity entity = new StringEntity(strTO);
		entity.setContentType("text/plain");
		postRequest.setEntity(entity);
		HttpResponse response = httpClient.execute(postRequest, localContext);
		if(response.getStatusLine().getStatusCode() != 200) {
			 throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String outputSTR = "";
		String output3;
	      while ((output3 = bufferedReader.readLine()) != null)
	      {
	        System.out.println("output3 " + output3);
	        if (output3 != null) {
	          outputSTR = outputSTR + output3;
	        }
	      }
	      responseAuthAppTO = (ResponseAuthAppTO) srtoJsonToObj(outputSTR, ResponseAuthAppTO.class);
	      for (Cookie cookie : this.cookieStore.getCookies())
	      {
	    	  System.out.println("La cookie es: " + cookie.getValue());
	    	  datosEntrada.put("cookie", cookie.getValue());
	      }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseAuthAppTO;
	}
	
	public Map<String, Object> ejecutaServicio(Map<String, Object> datosEntrada, Map<String, Object> request) {
		Map<String, Object> responseJson = new HashMap<>();
		HttpPost postRequest = new HttpPost(generaURLServicio(datosEntrada));
		BasicClientCookie objCookie = new BasicClientCookie("JSESSIONID", datosEntrada.get("cookie").toString());
		CookieStore cookieStore = new BasicCookieStore();
		objCookie.setPath(datosEntrada.get("pathServicio").toString());
		cookieStore.addCookie(objCookie);
		postRequest.setHeader("Cookie", "JSESSIONID=" + datosEntrada.get("cookie"));
		String strTO = objToStrJson(request);
		try {
		StringEntity input = new StringEntity(strTO);
	    input.setContentType("text/plain");
	    postRequest.setEntity(input);
	    HttpResponse response = httpClient.execute(postRequest, localContext);
	    if (response.getStatusLine().getStatusCode() != 200){
	        System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode() + " message: " + response.getStatusLine().getReasonPhrase());
	        throw new RuntimeException("Failed : HTTP error code");
	    }
	    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      
	    String outputSTR = "";
	    String output3;
	    while ((output3 = br.readLine()) != null) {
	       if (output3 != null) {
	         outputSTR = outputSTR + output3;
	      }
	    }
	    responseJson = (Map)srtoJsonToObj(outputSTR, HashMap.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseJson;
	}
	
	
	private String generaURLLogin(Map<String, Object> datosEntrada) {
		String url = null;
		url = "http://" + datosEntrada.get("ipServer") + ":" + datosEntrada.get("portServer") + "/WS_BDMAPPS/external/security/login/0.1/dologinapp";
		return url;
	}
	
	private String generaURLServicio(Map<String, Object> datosEntrada) {
		String url = null;
		url = "http://" + datosEntrada.get("ipServer") + ":" + datosEntrada.get("portServer") + "/WS_BDMAPPS"+datosEntrada.get("pathServicio");
		return url;
	}
	
	private <T> String objToStrJson(T objTO) {
		String resultado = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			resultado = mapper.writeValueAsString(objTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public <T> T srtoJsonToObj(String json, Class<T> objClass) {
		ObjectMapper mapper = new ObjectMapper();
		Object resultado = null;
		try {
			resultado = mapper.readValue(json, objClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) objClass.cast(resultado);
	}	

}
