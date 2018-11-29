package com.bancoazteca.bdm.bussiness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.commons.EncryMorannon;
import com.bancoazteca.bdm.commons.EncryptWS;
import com.bancoazteca.bdm.commons.SHA256BD;
import com.bancoazteca.bdm.commons.ValidacionesUtils;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.RequestAuthUser360TO;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.bancoazteca.bdm.entity.DatosEntradaEntityTO;
import com.bancoazteca.bdm.entity.MantieneSesionEntityTO;
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
public class ClienteLoginComponent {

	private static final Logger log = Logger.getLogger(ClienteLoginComponent.class);

	HttpClient httpClient = HttpClientBuilder.create().build();
	HttpContext localContext = new BasicHttpContext();
	MantieneSesionEntityTO sesionEntityTO = new MantieneSesionEntityTO();
	List<String> tokenCodeLista = new ArrayList<String>();	

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
	public String lanzaPathEspecifico(DatosEntradaEntityTO datosEntradaEntityTO) {
		if(!ValidacionesUtils.isNullOrEmpty(sesionEntityTO.getTokeCode()))
			datosEntradaEntityTO.setTokenCode(sesionEntityTO.getTokeCode());
		Map<String, Object> requestMap = convertJsonToMap(datosEntradaEntityTO);
		CookieStore cookieStore = new BasicCookieStore();
		String outputSTR = "";				
		try {
			String cifrado = SHA256BD.testHashCipher(objToStrJson(requestMap), sesionEntityTO.getTokeCode());
			HttpPost httpPost = new HttpPost("http://"+datosEntradaEntityTO.getIpServer()+":"+datosEntradaEntityTO.getPuertoServer()+"/WS_BancaDigital/" + datosEntradaEntityTO.getPathService());
			httpPost.setHeader("Cookie", "JSESSIONID=" + sesionEntityTO.getCookie());
			httpPost.setHeader("headerOwn", cifrado);
			String strTO = EncryptWS.encryptSafeUrl(objToStrJson(requestMap), sesionEntityTO.getTokeCode());
			StringEntity input = new StringEntity(strTO);
			input.setContentType("text/plain");
			httpPost.setEntity(input);
			HttpResponse httpResponse = this.httpClient.execute(httpPost, this.localContext);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
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
			for (Cookie cookie : cookieStore.getCookies()) {
				sesionEntityTO.setCookie(cookie.getValue());
			}
			if (responseLoginAuthTO != null) {
				tokenCodeLista.add(responseLoginAuthTO.getTokenCode());
				sesionEntityTO.setTokeCode(responseLoginAuthTO.getTokenCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputSTR;
	}
	
	private Map<String, Object> convertJsonToMap(DatosEntradaEntityTO datosEntradaEntityTO){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(datosEntradaEntityTO.getRequest(), new TypeReference<Map<String, String>>(){});
			map.put("tokenCode", datosEntradaEntityTO.getTokenCode());
			map.put("aplicacion", datosEntradaEntityTO.getAplicacion());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
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
				sesionEntityTO.setCookie(cookie.getValue());
			}
			if (responseLoginAuthTO != null) {
				tokenCodeLista.add(responseLoginAuthTO.getTokenCode());
				sesionEntityTO.setTokeCode(responseLoginAuthTO.getTokenCode());
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
