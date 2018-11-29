package com.bancoazteca.bdm.cliente.bussiness;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.bancoazteca.bdm.cliente.commons.CifradoPublico;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaBilletera;
import com.bancoazteca.bdm.serializer.SerealizerClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteBilleteraComponent {
	
	private static final Logger log = Logger.getLogger(ClienteBilleteraComponent.class);
	
	private static final SerealizerClass serializador = new SerealizerClass();
	
	HttpClient httpClient = HttpClientBuilder.create().build();
	HttpContext localContext = new BasicHttpContext();
	
	public String generaTokenAPP(DatosEntradaBilletera entityTO) {
		String outputSTR = "";
		HttpPost postRequest = new HttpPost("http://"+entityTO.getIpServer()+":"+entityTO.getPuertoServer()+"/WSWallet/api/wallet/auth/security/authorization");
		StringEntity input;
		try {
			input = new StringEntity(cifraCadenaGetTokenAPP());
			input.setContentType("text/plain");
			postRequest.setEntity(input);
			HttpResponse response = this.httpClient.execute(postRequest, localContext);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output3;
			while ((output3 = br.readLine()) != null) {
				System.out.println(output3);
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return outputSTR;
	}
	
	private String cifraCadenaGetTokenAPP() {
		String clientSecret = new CifradoPublico().cifraClientSecretV2("0b7040e8007395a611eba36984fd62b1");
		log.info("El client_secret cifrado es: " + clientSecret);
		String cadena = "{\"tokenApp\":null,\"accessToken\":null,\"aplicacion\":null,\"icuCliente\":null,\"sistema\":\"WALLETSISTEMA\",\"llaveSistema\":null,\"ip\":\"10.51.113.30\",\"client_id\":\"b21b0572bcb7e88c52049df6e2bf9333\",\"client_secret\":\""+clientSecret+"\",\"grant_type\":null}";
		String cadenaCifrada = null;
		try {
			cadenaCifrada = serializador.encryptSafeUrl(cadena, "ExS5PCUw1OFc73+dRteTexH+ML7DUatr");
			log.info("La cadena cifrada es: " + cadenaCifrada);
		} catch (Exception e) {
			log.error("Error: ", e);
		}
		return cadenaCifrada;
	}
	
	public String validaCelular(DatosEntradaBilletera billetera, String tokenApp) {
		String outputSTR = "";
		CookieStore cookieStore = new BasicCookieStore();
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute("http.cookie-store", cookieStore);
		HttpPost postRequest = new HttpPost("http://"+billetera.getIpServer()+":"+billetera.getPuertoServer()+"/WSWallet/api/wallet/client/cell/validate");
		StringEntity input;
		try {
			
			input = new StringEntity(generaCadenaValidaCel(tokenApp, billetera.getTelefono()));
			input.setContentType("text/plain");
			postRequest.setEntity(input);
			HttpResponse response = this.httpClient.execute(postRequest, localContext);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output3;
			while ((output3 = br.readLine()) != null) {
				System.out.println(output3);
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
			for (Cookie cookie : cookieStore.getCookies()) {
				log.info("La cookie que se genero en el componente es: " + cookie.getValue());
				billetera.setCookie(cookie.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return outputSTR;
	}
	
	public String generaLogin(HttpSession session) {
		String outputSTR = "";
		HttpPost postRequest = new HttpPost("http://"+session.getAttribute("ipServer")+":"+session.getAttribute("puertoServer")+"/WSWallet/api/wallet/auth/security/login");
		postRequest.setHeader("Cookie", session.getAttribute("cookie").toString());
		StringEntity input;
		try {			
			input = new StringEntity(generaCadenaLogin(session.getAttribute("tokenApp").toString(), session.getAttribute("nip").toString()));
			input.setContentType("text/plain");
			postRequest.setEntity(input);
			HttpResponse response = this.httpClient.execute(postRequest, localContext);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output3;
			while ((output3 = br.readLine()) != null) {
				System.out.println(output3);
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return outputSTR;
	}
	
	public String generaPeticion(HttpSession session, Map<String, Object> request, String pathServicio) {
		String requestTO = MapToString(request);
		String outputSTR = "";
		HttpPost postRequest = new HttpPost("http://"+session.getAttribute("ipServer")+":"+session.getAttribute("puertoServer")+"/WSWallet/"+pathServicio);
		postRequest.setHeader("Cookie", session.getAttribute("cookie").toString());
		StringEntity input;
		try {			
			input = new StringEntity(generaCadenaPeticion(requestTO));
			input.setContentType("text/plain");
			postRequest.setEntity(input);
			HttpResponse response = this.httpClient.execute(postRequest, localContext);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Output from Server .... Error: " + response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output3;
			while ((output3 = br.readLine()) != null) {
				System.out.println(output3);
				if (output3 != null) {
					outputSTR = outputSTR + output3;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return outputSTR;
	}
	
	private String generaCadenaValidaCel(String tokenAPP, String numCelular) {
		String cadena = "{\"tokenApp\":\""+tokenAPP+"\",\"accessToken\":null,\"aplicacion\":\"WINDOWS-CHROME-1.8-WalletDig\",\"icuCliente\":null,\"sistema\":\"WALLETSISTEMA\",\"llaveSistema\":null,\"ip\":\"10.51.113.30\",\"clavePais\":\"52\",\"celular\":\""+numCelular+"\",\"infoHashWallet\":\"340150f6bc42de2ef39feab44dbe60c3\",\"isMobile\":true,\"longitud\":null,\"latitud\":null,\"mobile\":true}";
		String cadenaCifrada = null;
		try {
			cadenaCifrada = serializador.encryptSafeUrl(cadena, "ExS5PCUw1OFc73+dRteTexH+ML7DUatr");
			log.info("La cadena cifrada en el componente validaCel es: " + cadenaCifrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadenaCifrada;
	}
	
	private String generaCadenaLogin(String tokenApp, String nip) {
		String nipCifrado = new CifradoPublico().cifraNipV2(nip);
		String cadena = "{\"tokenApp\":\""+tokenApp+"\",\"accessToken\":null,\"aplicacion\":null,\"icuCliente\":null,\"sistema\":\"WALLETSISTEMA\",\"llaveSistema\":null,\"ip\":\"10.51.113.30\",\"nip\":\""+nipCifrado+"\",\"celular\":null,\"clavePais\":null,\"infoHashWallet\":null,\"isMobile\":false,\"mobile\":false}";
		String cadenaCifrada = null;
		try {
			cadenaCifrada = serializador.encryptSafeUrl(cadena, "ExS5PCUw1OFc73+dRteTexH+ML7DUatr");
			log.info("La cadena cifrada en el componente login es: " + cadenaCifrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadenaCifrada;
	}
	
	private String generaCadenaPeticion(String cadena) {
		String cadenaCifrada = null;
		try {
			cadenaCifrada = serializador.encryptSafeUrl(cadena, "ExS5PCUw1OFc73+dRteTexH+ML7DUatr");
			log.info("La cadena cifrada en el componente peticion es: " + cadenaCifrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadenaCifrada;
	}
	
	private String MapToString(Map<String, Object> request) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(request);
			log.info("El request en json es: " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}
