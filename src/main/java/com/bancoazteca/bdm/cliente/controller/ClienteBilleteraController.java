package com.bancoazteca.bdm.cliente.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bancoazteca.bdm.cliente.bussiness.ClienteBilleteraComponent;
import com.bancoazteca.bdm.cliente.commons.CifradoPublico;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.dao.ContratoInterfazDAO;
import com.bancoazteca.bdm.cliente.entity.ContratoInterfazEntityTO;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaBilletera;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@Scope("session")
@RequestMapping(value="/controllerClienteWallet")
public class ClienteBilleteraController {
	
	private static final Logger log = Logger.getLogger(ClienteBilleteraController.class);
	
	private static final String TOKEN_APP = "tokenApp";
	private static final String IP_SERVER = "ipServer";
	private static final String PUERTO_SERVER = "puertoServer";
	private static final String ACCESS_TOKEN= "accessToken";
	private static final String SISTEMA = "sistema";
	private static final String COOKIE = "cookie";
	private static final String COMPLEMENTO_COOKIE = "JSESSIONID=";
	
	@Autowired
	private ContratoInterfazDAO contratoInterfazDAO;
	@Autowired
	private ClienteBilleteraComponent component;

	@RequestMapping(value= "/obtieneip",  method = RequestMethod.GET)
	public String obtieneIp(Model model, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		String ipServer = null;
		try {
			ipServer = request.getRemoteAddr();
			if(ipServer.equals("0:0:0:0:0:0:0:1")) {
				ipServer = InetAddress.getLocalHost().getHostAddress();
			}
			ipServer= mapper.writeValueAsString(ipServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipServer;
	}
	
	@RequestMapping(value= "/obtieneContratos",  method = RequestMethod.GET)
	public List<ContratoInterfazEntityTO> obtieneContratos(Model model) {
		List<ContratoInterfazEntityTO> interfazWallet = contratoInterfazDAO.mostrarWallet();
		return interfazWallet;
	}
	
	@RequestMapping(value= {"/obtieneRequestWallet"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String obtieneRequest(@RequestBody String path) throws JsonProcessingException {
		String pathController = null;
		String jsonRequestWallet = null;
		Map<String, Object> map = convertoRequestToMap(path);
		pathController = map.get("pathWallet").toString();
		String jsonRequest = contratoInterfazDAO.buscaRequestWallet(pathController);
		if(!jsonRequest.equalsIgnoreCase("No existe Request")) {
			Map<String, Object> mapRequestWallet = convertoRequestToMap(jsonRequest);
			mapRequestWallet.remove(TOKEN_APP);
			mapRequestWallet.remove(ACCESS_TOKEN);
			mapRequestWallet.remove(SISTEMA);
			mapRequestWallet.remove("ip");
			jsonRequestWallet = new ObjectMapper().writeValueAsString(mapRequestWallet);
		}else {
			jsonRequestWallet = "{\"info\":\"No existe request con ese Path, ingrese manualmente el request\"}";
		}
		return jsonRequestWallet;
	}
	
	@RequestMapping(value= {"/loginWallet"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String haceLoginWallet(@RequestBody DatosEntradaBilletera datosEntradaBilletera, HttpSession session) {
		log.info("Entra al controlador haceLoginWallet ::::: ClienteBilleteraController");
		String respuestaFinal = null;
		String validaCelular = null;
		Map<String, Object> respuestaValidacionCel = new HashMap<>();
		validaTokenApp(datosEntradaBilletera, session);
		validaCelular = component.validaCelular(datosEntradaBilletera, session.getAttribute("tokenApp").toString());
		respuestaValidacionCel = jsonToMap(validaCelular);
		if(respuestaValidacionCel.get("codigoOperacion").equals("0")) {
			respuestaFinal = realizaLogin(datosEntradaBilletera, session);
		}else if(respuestaValidacionCel.get("codigoOperacion").equals("-8505")) {
			log.info("Entra al if para volver a validar el tokenAPP ya que arrojo un -8505");
			peticionTokenAPP(datosEntradaBilletera, session);
			validaCelular = component.validaCelular(datosEntradaBilletera, session.getAttribute("tokenApp").toString());
			respuestaValidacionCel = jsonToMap(validaCelular);
			if(respuestaValidacionCel.get("codigoOperacion").equals("0")) {
				respuestaFinal = realizaLogin(datosEntradaBilletera, session);
			}
		}
		return respuestaFinal;
	}

	
	@RequestMapping(value= {"/lanzaPeticionWallet"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String lanzaPeticion(@RequestBody DatosEntradaBilletera datosEntradaBilletera, HttpSession session) {
		log.info("Entra al controlador lanzaPeticion ::::: ClienteBilleteraController");
		Map<String, Object> requestMap = convertoRequestToMap(datosEntradaBilletera.getRequest());
		requestMap.put("accessToken", session.getAttribute("accessToken"));
		requestMap.put("tokenApp", session.getAttribute("tokenApp"));
		requestMap.put("sistema", "WALLETSISTEMA");
		requestMap.put("ip", "10.51.113.30");
		if(!ValidacionesUtils.isNullOrEmpty(requestMap.get("nipWallet"))) {
			requestMap.put("nipWallet", cifrarNip(requestMap.get("nipWallet").toString()));
		}
		log.info("El request terminado es: " + requestMap);
		String respuesta = component.generaPeticion(session, requestMap, datosEntradaBilletera.getPathServicio());
		log.info("La respuesta de la peticion es: " + respuesta);
		Map<String, Object> respuestaMapa = convertoRequestToMap(respuesta);
		if(!respuestaMapa.get("codigoOperacion").equals("-1")) {
			session.setAttribute(ACCESS_TOKEN, respuestaMapa.get("accessToken"));
		}
		return respuesta;
	}

	
	@RequestMapping(value= {"/lanzaPeticionWalletSinLogin"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String lanzaPeticionSinLogin(@RequestBody DatosEntradaBilletera datosEntradaBilletera, HttpSession session) {
		log.info("Entra al controlador lanzaPeticionSinLogin ::::: ClienteBilleteraController");
		peticionTokenAPP(datosEntradaBilletera, session);
		validaTokenApp(datosEntradaBilletera, session);
		Map<String, Object> requestMap = convertoRequestToMap(datosEntradaBilletera.getRequest());
		llenaDatosSesion(session, datosEntradaBilletera);
		requestMap.put("tokenApp", session.getAttribute("tokenApp"));
		requestMap.put("sistema", "WALLETSISTEMA");
		requestMap.put("ip", "10.51.113.30");
		log.info("El request terminado es: " + requestMap);
		String respuesta = component.generaPeticion(session, requestMap, datosEntradaBilletera.getPathServicio());
		log.info("La respuesta de la peticion es: " + respuesta);
		return respuesta;
	}
	
	@RequestMapping(value= "/cerrarSesionWallet",  method = RequestMethod.GET)
	public String cerrarSesionWallet(Model model, HttpSession session) {
		log.info("Entra al metodo de cerrarSesionWallet :::: Entra a invalida sesion en ::::: ClienteBilleteraComponent");
		if(session!=null) {
			log.info("La sesion esta llena y se invalida");
			session.invalidate();
		}
		return "{\"codigoOperacion\":\"0\"}";
	}
	
	private String realizaLogin(DatosEntradaBilletera datosEntradaBilletera, HttpSession session) {
		String respuestaFinal = null;
		llenaDatosSesion(session, datosEntradaBilletera);
		respuestaFinal = component.generaLogin(session);
		Map<String, Object> respuestaLogin = jsonToMap(respuestaFinal);
		if(respuestaLogin.get("codigoOperacion").equals("0")) {
			session.setAttribute(ACCESS_TOKEN, respuestaLogin.get("accessToken"));
			log.info("El access token de sesion es: " + session.getAttribute("accessToken"));
		}
		return respuestaFinal;
	}
	
	private String cifrarNip(String nip) {		
		return new CifradoPublico().cifraNipV2(nip); 
	}
	
	private void peticionTokenAPP(DatosEntradaBilletera datosEntradaBilletera,  HttpSession session) {
		Map<String, Object> respuestaTokenAPP = jsonToMap(component.generaTokenAPP(datosEntradaBilletera));
		if(respuestaTokenAPP.get("codigoOperacion").equals("0")) {
			session.setAttribute(TOKEN_APP, respuestaTokenAPP.get("tokenApp"));
		}else {
			log.info("Error al tramitar tokenAPP");
			session.removeAttribute(TOKEN_APP);
		}
	}
	
	private void validaTokenApp(DatosEntradaBilletera datosEntradaBilletera,  HttpSession session) {
		if(ValidacionesUtils.isNullOrEmpty(session.getAttribute("tokenApp"))) {
			log.info("El tokenAPP de sesion viene vacio, asi que se genera uno nuevo.");
			Map<String, Object> respuestaTokenAPP = jsonToMap(component.generaTokenAPP(datosEntradaBilletera));
			if(respuestaTokenAPP.get("codigoOperacion").equals("0")) {
				log.info("El TokenAPP es: " +  respuestaTokenAPP.get("tokenApp"));
				session.setAttribute(TOKEN_APP, respuestaTokenAPP.get("tokenApp"));
			}else {
				log.info("Error al tramitar tokenAPP");
				session.removeAttribute(TOKEN_APP);
			}
		}else {
			log.info("El tokenAPP esta lleno y funcional");
		}
	}
	
	private void llenaDatosSesion(HttpSession session, DatosEntradaBilletera billetera) {
		session.setAttribute(IP_SERVER, billetera.getIpServer());
		session.setAttribute(PUERTO_SERVER, billetera.getPuertoServer());
		session.setAttribute(COOKIE, COMPLEMENTO_COOKIE+billetera.getCookie());
		session.setAttribute("telefono", billetera.getTelefono());
		session.setAttribute("nip", billetera.getNipWallet());
	}
	
	private Map<String, Object> jsonToMap(String json){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;		
	}
	
	private Map<String, Object> convertoRequestToMap(String path){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestInternalTOMap = new HashMap<String, Object>();
		try {
			requestInternalTOMap = mapper.readValue(path, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestInternalTOMap;
	}

}
