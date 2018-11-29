package com.bancoazteca.bdm.cliente.bussiness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.cliente.commons.EncryMorannon;
import com.bancoazteca.bdm.cliente.commons.EncryptWS;
import com.bancoazteca.bdm.cliente.commons.SHA256BD;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaInstaEntityTO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.RequestAuthUser360TO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteInstaCoreComponent {
	
	HttpClient httpClient = HttpClientBuilder.create().build();
	HttpContext localContext = new BasicHttpContext();
	
	private static final String LLAVE = "ExS5PCUw1OFc73+dRteTexH+ML7DUatr";
	
	public ResponseLoginAuthTO generaLogin(Map<String, Object> entityTO) {
		RequestAuthUser360TO authUser360TO = creaObjetoRequestAuth(entityTO);
		ResponseLoginAuthTO responseLoginAuthTO = new ResponseLoginAuthTO();
		entityTO.remove("request");
		HttpPost postRequest = new HttpPost(generaURLLogin(entityTO.get("ipServer").toString(), entityTO.get("puertoServer").toString()));
		CookieStore cookieStore = new BasicCookieStore();
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute("http.cookie-store", cookieStore);
		String strTO = EncryptWS.encryptSafeUrl(objToStrJson(authUser360TO), LLAVE);
		try {
			StringEntity input = new StringEntity(strTO);
			input.setContentType("text/plain");
			postRequest.setEntity(input);
			HttpResponse response = this.httpClient.execute(postRequest, localContext);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String outputSTR = "";
			String output3;
			while ((output3 = br.readLine()) != null) {
				System.out.println(output3);
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
			responseLoginAuthTO = (ResponseLoginAuthTO) srtoJsonToObj(outputSTR, ResponseLoginAuthTO.class);
			for (Cookie cookie : cookieStore.getCookies()) {
				entityTO.put("cookie", cookie.getValue());
				responseLoginAuthTO.setNombre(cookie.getValue());
			}
			if (responseLoginAuthTO != null) {
				entityTO.put("tokenCode", responseLoginAuthTO.getTokenCode());
			}
		}catch (RuntimeException con) {
			responseLoginAuthTO = new ResponseLoginAuthTO();
			responseLoginAuthTO.setCodigoOperacion("-1");
			responseLoginAuthTO.setDescripcion("Connection refused. Server not available");
		}catch (MalformedURLException e){
			e.printStackTrace();
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
		return responseLoginAuthTO;
	}
	
	public String ejecutaServicio(DatosEntradaInstaEntityTO entityTO) {
		Map<String, Object> requestMap = convertoRequestToMap(entityTO);
		CookieStore cookieStore = new BasicCookieStore();
		String outputSTR = "";
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), entityTO.getTokenCode());
			HttpPost httpPost = new HttpPost(generaURLServicio(entityTO.getIpServer(), entityTO.getPuertoServer(), entityTO.getPathService()));
			httpPost.setHeader("Cookie", "JSESSIONID=" + entityTO.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), entityTO.getTokenCode());
			StringEntity input = new StringEntity(strTO);
			input.setContentType("text/plain");
			httpPost.setEntity(input);
			HttpResponse httpResponse = this.httpClient.execute(httpPost, this.localContext);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + httpResponse.getStatusLine().getStatusCode());
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String output3;
			while ((output3 = br.readLine()) != null) {
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
			ResponseLoginAuthTO responseLoginAuthTO = (ResponseLoginAuthTO) srtoJsonToObj(outputSTR, ResponseLoginAuthTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputSTR;
	}
	
	private Map<String, Object> convertoRequestToMap(DatosEntradaInstaEntityTO entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestTOMap = new HashMap<String, Object>();
		try {
			requestTOMap = mapper.readValue(entityTO.getRequest(), new TypeReference<Map<String, Object>>(){});
			requestTOMap.put("tokenCode", entityTO.getTokenCode());
			requestTOMap.put("aplicacion", "Android-7.0-1000-BD");
			requestTOMap.put("firmaDigital", "e7xtEkO4wFM54xm-ccgbfw");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestTOMap;
	}
	
	private Map<String, Object> convertJsonToMap(DatosEntradaInstaEntityTO datosEntradaInstaEntityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!ValidacionesUtils.isNullOrEmpty(datosEntradaInstaEntityTO)) {
				map = mapper.readValue(datosEntradaInstaEntityTO.getRequest(), new TypeReference<Map<String, Object>>(){});
				map.put("tokenCode", datosEntradaInstaEntityTO.getTokenCode());
				map.put("aplicacion", datosEntradaInstaEntityTO.getAplicacion());
				map.put("firmaDigital", "e7xtEkO4wFM54xm-ccgbfw");
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private String generaURLLogin(String ip, String puerto) {
		return "http://"+ip+":"+puerto+"/WS_BancaDigital" + "/api/bdm/security/dologin";
	}
	
	private String generaURLServicio(String ip, String puerto, String path) {
		return "http://"+ip+":"+puerto+"/WS_BancaDigital/" + path;
	}
	
	private RequestAuthUser360TO creaObjetoRequestAuth(Map<String, Object> entityTO) {
		RequestAuthUser360TO authUser360TO = new RequestAuthUser360TO();
		authUser360TO.setInfoHashActivation(generaInfoHash());
		authUser360TO.setUsername(entityTO.get("usuario").toString());
		authUser360TO.setThePass(EncryMorannon.cifraPassword(entityTO.get("password").toString(), "BD"));
		authUser360TO.setAplicacion("Android-7.0-1000-BD");
		authUser360TO.setUbicacionGps(new double[] { -99.18603141718289D, 19.29650528340585D });
		authUser360TO.setDireccion("sucursal");
		authUser360TO.setTienePortabilidadNomina(false);
		authUser360TO.setTokenMovil("e7xtEkO4wFM54xm-ccgbfw");
		authUser360TO.setFoto(null);
		return authUser360TO;
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
	
	private <T> T srtoJsonToObj(String json, Class<T> objClass) {
		ObjectMapper mapper = new ObjectMapper();
		Object resultado = null;
		try {
			resultado = mapper.readValue(json, objClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) objClass.cast(resultado);
	}
	
	private String generaInfoHash() {
		UUID uuid = UUID.randomUUID();
		String infoHash = "kasdfofgh4567dfgh234cfsdfsdfsdfsd"+uuid.toString().substring(0, 4);
		return infoHash;
	}


}
