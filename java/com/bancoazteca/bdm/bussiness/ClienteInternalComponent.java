package com.bancoazteca.bdm.bussiness;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.bancoazteca.bdm.commons.EncripcionInternals;

@Service
public class ClienteInternalComponent {
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public String ejecutaPeticionInternal(Map<String, Object> objetoRequest) {
		String resultado = null;
		String url = generaURL(objetoRequest.get("ipServerJVC").toString(), objetoRequest.get("portServerJVC").toString(), objetoRequest.get("pathInternal").toString());
		HttpPost postRequest = new HttpPost(url);
		String strTO = EncripcionInternals.cifraCadenaInternal(objetoRequest);
		StringEntity input = new StringEntity(strTO, "UTF-8");
		input.setContentType("text/plain;charset=UTF-8");
		postRequest.setEntity(input);
		try {
			HttpResponse response = httpClient.execute(postRequest);
			String result = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    		}
			resultado = EncripcionInternals.decifraCadenaInternal(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	private String generaURL(String ip, String puerto, String url) {
		String pathCompleto = "http://"+ip+":"+puerto+"/WS_BDMAPPS/"+url;		
		return pathCompleto;
	}
	
	
}
