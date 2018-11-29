package com.bancoazteca.bdm.cliente.bussiness;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.cliente.commons.EncripcionInternals;

/**
 * @author Cesar M Orozco R
 *
 */
@Service
@SuppressWarnings("deprecation")
public class ClienteInternalComponent {
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public Map<String, Object> ejecutaPeticionInternal(Map<String, Object> objetoRequest, Map<String, Object> requestInternal) {
		Map<String, Object> resultadoObject = new HashMap<>();
		String url = generaURL(objetoRequest.get("ipServerJVC").toString(), objetoRequest.get("portServerJVC").toString(), objetoRequest.get("pathInternal").toString());
		HttpPost postRequest = new HttpPost(url);
		System.out.println("Mapa recibido en ejecutaPeticionInternal es: " + requestInternal);
		String strTO = EncripcionInternals.cifraCadenaInternal(requestInternal);
		StringEntity input = new StringEntity(strTO, "UTF-8");
		input.setContentType("text/plain; charset=UTF-8");
		postRequest.setEntity(input);
		postRequest.setHeader("Accept-Encoding", "UTF-8");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			String result = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    		}
			resultadoObject = EncripcionInternals.decifraCadenaInternal(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoObject;
	}
	
	private String generaURL(String ip, String puerto, String url) {
		String pathCompleto = "http://"+ip+":"+puerto+"/WS_BancaDigital/"+url;		
		return pathCompleto;
	}
	
}
