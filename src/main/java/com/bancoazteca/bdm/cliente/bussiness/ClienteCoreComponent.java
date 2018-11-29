package com.bancoazteca.bdm.cliente.bussiness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.cliente.commons.EncryMorannon;
import com.bancoazteca.bdm.cliente.commons.EncryptWS;
import com.bancoazteca.bdm.cliente.commons.SHA256BD;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.entity.CierraSesionEntity;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaAsyncronaEntityTO;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaEntityTO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.RequestAuthUser360TO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Cesar M Orozco R
 *
 */
@Service
public class ClienteCoreComponent {

	private static final Logger log = Logger.getLogger(ClienteCoreComponent.class);

	HttpClient httpClient = HttpClientBuilder.create().build();
	HttpContext localContext = new BasicHttpContext();

	private static final String LLAVE = "ExS5PCUw1OFc73+dRteTexH+ML7DUatr";
	

	/**
	 * @param entityTO
	 * @return
	 */
	public ResponseLoginAuthTO generaLogin(DatosEntradaEntityTO entityTO) {
		log.info("Entra al metodo de generaLogin::: ClienteLoginComponent");
		RequestAuthUser360TO authUser360TO = new RequestAuthUser360TO();
		authUser360TO.setInfoHashActivation(entityTO.getInfoHash());
		authUser360TO.setUsername(entityTO.getUsuario());
		authUser360TO.setThePass(EncryMorannon.cifraPassword(entityTO.getPassword(), "BD"));
		authUser360TO.setAplicacion("Android-7.0-1000-BD");
		authUser360TO.setUbicacionGps(new double[] { -99.18603141718289D, 19.29650528340585D });
		authUser360TO.setDireccion("sucursal");
		authUser360TO.setTienePortabilidadNomina(false);
		authUser360TO.setTokenMovil("e7xtEkO4wFM54xm-ccgbfw");
		authUser360TO.setFoto(null);
		ResponseLoginAuthTO responseLoginAuthTO = haceLogin(authUser360TO, entityTO);
		entityTO.setTokenCode(responseLoginAuthTO.getTokenCode());
		return responseLoginAuthTO;
	}
	

	/**
	 * @param datosEntradaEntityTO
	 * @return
	 */
	public Map<String, Object> lanzaPathEspecifico(DatosEntradaEntityTO datosEntradaEntityTO) {
		Map<String, Object> requestMap = convertJsonToMap(datosEntradaEntityTO, null);
		CookieStore cookieStore = new BasicCookieStore();
		Map<String, Object> mapResponse = new HashMap<>();
		String outputSTR = "";
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), datosEntradaEntityTO.getTokenCode());
			HttpPost httpPost = new HttpPost("http://"+datosEntradaEntityTO.getIpServer()+":"+datosEntradaEntityTO.getPuertoServer()+"/WS_BancaDigital/" + datosEntradaEntityTO.getPathService());
			httpPost.setHeader("Cookie", "JSESSIONID=" + datosEntradaEntityTO.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), datosEntradaEntityTO.getTokenCode());
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
			mapResponse = convierteRespuestaMapa(outputSTR);
			for (Cookie cookie : cookieStore.getCookies()) {
				mapResponse.put("cookie", cookie.getValue());
			}
			if (responseLoginAuthTO != null) {
				mapResponse.put("tokenCode", responseLoginAuthTO.getTokenCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapResponse;
	}
	
	private Map<String, Object> convierteRespuestaMapa(String respuesta){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mapResponse = new HashMap<>();
		try {
			mapResponse = mapper.readValue(respuesta, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapResponse;
	}
	
	
	/**
	 * @param datosEntradaEntityTO
	 * @return
	 */
	public Map<String, Object> lanzaPathEspecificoSinOTP(DatosEntradaEntityTO datosEntradaEntityTO) {
		Map<String, Object> requestMap = convertJsonToMap(datosEntradaEntityTO, null);
		Map<String, Object> mapResponse = new HashMap<>();	
		String outputSTR = "";				
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), LLAVE);
			HttpPost httpPost = new HttpPost(generaURL(datosEntradaEntityTO.getIpServer(), datosEntradaEntityTO.getPuertoServer(), datosEntradaEntityTO.getPathService()));			
			httpPost.setHeader("Cookie", "JSESSIONID=" + datosEntradaEntityTO.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), LLAVE);
			StringEntity input = new StringEntity(strTO);
			input.setContentType("text/plain");
			httpPost.setEntity(input);
			HttpResponse httpResponse;
			if(httpPost.getURI().getPath().contains("/api/bdm/consulta/ultimosDocumentos")) {
				log.info("Entra al if porque encontro el path de consulta/ultimosDocumentos y le agrega el timeout de 1 segundo");
				int timeout = 1; // seconds
				HttpParams httpParams = httpClient.getParams();
				httpParams.setParameter(
				  CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
				httpParams.setParameter(
				  CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);			
				httpResponse = httpClient.execute(httpPost, this.localContext);				
			}else {
				httpResponse = this.httpClient.execute(httpPost, this.localContext);
			}
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
			mapResponse = convierteRespuestaMapa(outputSTR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapResponse;
	}
	
	public Map<String, Object> lanzaPathEspecificoSinSesion(DatosEntradaAsyncronaEntityTO asyncronaEntityTO) {
		Map<String, Object> requestMap = convertJsonToMap(null, asyncronaEntityTO);
		Map<String, Object> mapResponse = new HashMap<>();	
		String outputSTR = "";				
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), LLAVE);
			HttpPost httpPost = new HttpPost(generaURL(asyncronaEntityTO.getIpServerAsyn(), asyncronaEntityTO.getPortServerAsyn(), asyncronaEntityTO.getPathAsyn()));
			httpPost.setHeader("Cookie", "JSESSIONID=" + asyncronaEntityTO.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), LLAVE);
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
			mapResponse = convierteRespuestaMapa(outputSTR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapResponse;
	}
	
	
	/**
	 * @param cierraSesionEntity
	 * @return
	 */
	public String cierraSesion(CierraSesionEntity cierraSesionEntity) {
		Map<String, Object> requestMap = convertJsonToMapCierraSesion(cierraSesionEntity);
		String outputSTR = "";
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), cierraSesionEntity.getTokenCode());
			HttpPost httpPost = new HttpPost("http://"+cierraSesionEntity.getIp()+":"+cierraSesionEntity.getPuerto()+"/WS_BancaDigital/api/bdm/security/doLogout");
			httpPost.setHeader("Cookie", "JSESSIONID=" + cierraSesionEntity.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), LLAVE);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputSTR;
	}	
	
	/**
	 * @param objTO
	 * @return
	 */
	public <T> String objToStrJson(T objTO) {
		String resultado = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			resultado = mapper.writeValueAsString(objTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * @param json
	 * @param objClass
	 * @return
	 */
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
	
	private Map<String, Object> convertJsonToMap(DatosEntradaEntityTO datosEntradaEntityTO, DatosEntradaAsyncronaEntityTO asyncronaEntityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!ValidacionesUtils.isNullOrEmpty(datosEntradaEntityTO)) {
				map = mapper.readValue(datosEntradaEntityTO.getRequest(), new TypeReference<Map<String, Object>>(){});
				map.put("tokenCode", datosEntradaEntityTO.getTokenCode());
				map.put("aplicacion", datosEntradaEntityTO.getAplicacion());				
			}else if(!ValidacionesUtils.isNullOrEmpty(asyncronaEntityTO)){
				map = mapper.readValue(asyncronaEntityTO.getRequestAsyn(), new TypeReference<Map<String, Object>>(){});
				map.put("aplicacion", asyncronaEntityTO.getAplicacion());			
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
	
	private Map<String, Object> convertJsonToMapCierraSesion(CierraSesionEntity entityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		String request = "{"+"\""+"tokenCode"+"\":\""+entityTO.getTokenCode()+"\","+ "\""+"aplicacion"+"\":\""+entityTO.getAplicacion()+"\""+"}";
		entityTO.setRequestFinSesion(request);
		try {	
			map = mapper.readValue(entityTO.getRequestFinSesion(), new TypeReference<Map<String, Object>>(){});			
			System.out.println("El mapa de cerrar sesion es: " + map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		String aCifrar = "{\"username\":\"elizandro135\",\"isMobile\":false,\"infoHashActivation\":\"kasdfofgh4567dfgh234cfsdfsdfsdfsda687\",\"direccion\":\"sucursal\",\"tienePortabilidadNomina\":false,\"tokenMovil\":\"e7xtEkO4wFM54xm-ccgbfw\",\"foto\":null,\"tokenCode\":null,\"aplicacion\":\"Android-7.0-1000-BD\",\"timeStamp\":null,\"icuCliente\":null,\"sistema\":null,\"llaveSistema\":null,\"ip\":null,\"ubicacionGps\":[-99.18603141718289,19.29650528340585],\"recuperaFD\":false,\"longitud\":-99.18603141718289,\"latitud\":19.29650528340585}";
		String resuklt = EncryptWS.encryptSafeUrl(aCifrar, "ExS5PCUw1OFc73+dRteTexH+ML7DUatr");
		System.out.println(resuklt);
	}

	private ResponseLoginAuthTO haceLogin(RequestAuthUser360TO authUser360TO, DatosEntradaEntityTO entityTO) {
		ResponseLoginAuthTO responseLoginAuthTO = new ResponseLoginAuthTO();
		HttpPost postRequest = new HttpPost("http://"+entityTO.getIpServer()+":"+entityTO.getPuertoServer()+"/WS_BancaDigital" + "/api/bdm/security/dologin");
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
			log.info("El nombre del login es: " + responseLoginAuthTO.getNombre());
			for (Cookie cookie : cookieStore.getCookies()) {
				responseLoginAuthTO.setNombre(cookie.getValue());
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
	
	private String generaURL(String ip, String puerto, String path) {
		String urlComplet = "http://"+ip+":"+puerto+"/WS_BancaDigital/" + path;
		return urlComplet;
	}

}
