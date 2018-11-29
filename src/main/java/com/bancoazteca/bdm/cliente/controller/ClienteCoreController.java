package com.bancoazteca.bdm.cliente.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

import com.bancoazteca.bdm.cliente.bussiness.ClienteCoreComponent;
import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.dao.ContratoInterfazDAO;
import com.bancoazteca.bdm.cliente.entity.CierraSesionEntity;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaAsyncronaEntityTO;
import com.bancoazteca.bdm.cliente.entity.DatosEntradaEntityTO;
import com.bancoazteca.bdm.cliente.entity.FiltroContratosEntityTO;
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
@RestController
@Scope("session")
@RequestMapping(value="/controllerCliente")
public class ClienteCoreController {
	
	private static final Logger log = Logger.getLogger(ClienteCoreController.class);
	
	@Autowired
	private ClienteCoreComponent clienteLoginComponent;	
	@Autowired
	private ContratoInterfazDAO contratoInterfazDAO;
	
	
	/**
	 * @param model
	 * @param request
	 * @return
	 */
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
	
	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/obtieneContratos",  method = RequestMethod.GET)
	public FiltroContratosEntityTO obtieneContratos(Model model) {
		FiltroContratosEntityTO interfazEntityTOs = contratoInterfazDAO.mostrar();
		return interfazEntityTOs;
	}	
	
	/**
	 * @param path
	 * @param request
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value= {"/obtieneRequestCore"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String obtieneRequest(@RequestBody String path) throws JsonProcessingException {
		String pathController = null;
		String jsonRequestCore = null;
		Map<String, Object> map = convertoRequestToMap(path);
		pathController = map.get("pathCore").toString().substring(0, map.get("pathCore").toString().length()-1);
		String jsonRequest = contratoInterfazDAO.buscaRequestCore(pathController);
		if(!jsonRequest.equalsIgnoreCase("No existe Request")) {
			Map<String, Object> mapRequestCore = convertoRequestToMap(jsonRequest);
			mapRequestCore.remove("aplicacion");
			mapRequestCore.remove("tokenCode");
			mapRequestCore.remove("timeStamp");
			jsonRequestCore = new ObjectMapper().writeValueAsString(mapRequestCore);
		}else {
			jsonRequestCore = "{\"info\":\"No existe request con ese Path, ingrese manualmente el request\"}";
		}
		return jsonRequestCore;
	}	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping(value= {"/loginBAZ"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String haceLogin(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpSession session) {
		log.info("Entra al controlador haceLogin ::::: ClienteCoreController");
		log.info("El usuario es: "+datosEntradaEntityTO.getUsuario());
		log.info("El password es: "+datosEntradaEntityTO.getPassword());
		session.setAttribute("infohash", generaInfoHasNuevo());
		datosEntradaEntityTO.setInfoHash(session.getAttribute("infohash").toString());
		ResponseLoginAuthTO authTO = clienteLoginComponent.generaLogin(datosEntradaEntityTO);
		llenaCamposSesion(authTO, session, datosEntradaEntityTO);
		return clienteLoginComponent.objToStrJson(authTO);
	}	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value={"/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionURL(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpSession session) throws JsonProcessingException {
		Map<String, Object> respuestaEnMapa = new HashMap<>();
		datosEntradaEntityTO = extraeDatosSesion(datosEntradaEntityTO, session);
		if(datosEntradaEntityTO.getSinSesion().equals("true")) {
			respuestaEnMapa = clienteLoginComponent.lanzaPathEspecificoSinOTP(datosEntradaEntityTO);
		}else {
			respuestaEnMapa = clienteLoginComponent.lanzaPathEspecifico(datosEntradaEntityTO);
		}
		if(!ValidacionesUtils.isNullOrEmpty(respuestaEnMapa.get("tokenCode"))) {
			session.setAttribute("tokenCode", respuestaEnMapa.get("tokenCode"));
		}
		ObjectMapper mapper = new ObjectMapper();
		String respuesta = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respuestaEnMapa);		
		return respuesta;
	}	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value={"/ejecutaPeticionAsyncrona"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionAsyncronaURL(@RequestBody DatosEntradaAsyncronaEntityTO datosEntradaAsyncronaEntityTO, HttpSession session) throws JsonProcessingException {
		Map<String, Object> respuestaEnMapa = new HashMap<>();
		datosEntradaAsyncronaEntityTO.setAplicacion("Android-7.0-1000-BD");
		datosEntradaAsyncronaEntityTO.setCookie(session.getId());
		datosEntradaAsyncronaEntityTO.setPathAsyn(validaPath(datosEntradaAsyncronaEntityTO.getPathAsyn()));
		respuestaEnMapa = clienteLoginComponent.lanzaPathEspecificoSinSesion(datosEntradaAsyncronaEntityTO);
		ObjectMapper mapper = new ObjectMapper();
		String respuesta = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respuestaEnMapa);		
		return respuesta;
	}	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 */
	@RequestMapping(value={"/cerrarsesion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String cerrarSesionURL(@RequestBody CierraSesionEntity cierraSesionEntity, HttpSession session) {	
		log.info("Entra a invalidar la sesion y borrar la sesion :::: ClienteCoreController :::: cerrarSesionURL");
		if(session!=null) {
			log.info("La sesion no viene nula, asi que se mata sesion.");
			session.invalidate();
		}
		return clienteLoginComponent.cierraSesion(cierraSesionEntity);
	}	
	
	private void llenaCamposSesion(ResponseLoginAuthTO loginAuthTO, HttpSession session, DatosEntradaEntityTO datosEntradaEntityTO) {
		log.info("Entra al metodo de llenar la sesion");
		if(!ValidacionesUtils.isNullOrEmpty(loginAuthTO) && loginAuthTO.getTokenCode() != null) {
			log.info("El objeto loginAuthTO y el tokenCode vienen llenos asi que pasamos a setear los datos a sesion");
			log.info("Los campos de sesion son:");
			session.setAttribute("tokenCode", loginAuthTO.getTokenCode());
			log.info("tokenCode: "+loginAuthTO.getTokenCode());
			session.setAttribute("cookie", loginAuthTO.getNombre());
			log.info("cookie: "+loginAuthTO.getNombre());
			session.setAttribute("ipServer", datosEntradaEntityTO.getIpServer());
			log.info("Ip Servidor: "+datosEntradaEntityTO.getIpServer());
			session.setAttribute("puertoServer", datosEntradaEntityTO.getPuertoServer());
			log.info("Puerto Servidor: "+datosEntradaEntityTO.getPuertoServer());
			session.setAttribute("app", "Android-7.0-1000-BD");
		}
	}
	
	private String generaInfoHasNuevo() {
		UUID uuid = UUID.randomUUID();
		String infoHash = "kasdfofgh4567dfgh234cfsdfsdfsdfsd"+uuid.toString().substring(0, 4);
		return infoHash;
	}
	
	private DatosEntradaEntityTO extraeDatosSesion(DatosEntradaEntityTO datosEntradaEntityTO, HttpSession session) {
		datosEntradaEntityTO.setIdSession(session.getId());
		datosEntradaEntityTO.setIpServer(session.getAttribute("ipServer").toString());
		datosEntradaEntityTO.setPuertoServer(session.getAttribute("puertoServer").toString());
		datosEntradaEntityTO.setInfoHash(session.getAttribute("infohash").toString());
		datosEntradaEntityTO.setAplicacion(session.getAttribute("app").toString());
		datosEntradaEntityTO.setPathService(validaPath(datosEntradaEntityTO.getPathService()));
		datosEntradaEntityTO.setTokenCode(session.getAttribute("tokenCode").toString());		
		datosEntradaEntityTO.setCookie(session.getAttribute("cookie").toString());
		return datosEntradaEntityTO;
	}	
	
	private String validaPath(String path) {
		String pathSinEspacios = path.replace(" ", "");
		if(pathSinEspacios.startsWith("/")) {
			return pathSinEspacios.substring(1, pathSinEspacios.length());
		}
		return pathSinEspacios;
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
