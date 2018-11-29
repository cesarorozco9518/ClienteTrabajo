package com.bancoazteca.bdm.cliente.controller;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bancoazteca.bdm.bussiness.ClienteLoginComponent;
import com.bancoazteca.bdm.commons.ValidacionesUtils;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.bancoazteca.bdm.entity.DatosEntradaEntityTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/controllerCliente")
public class DatosMaquinaController {
	
//	private final static Logger log = Logger.getLogger(DatosMaquinaController.class);
	
	@Autowired
	private ClienteLoginComponent clienteLoginComponent;
	
	private static String ipServer = null;
	private static String portServer = null;
	private static String infoHash = null;
	private static String idSession = null;
	
	
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
	
	@RequestMapping(value= {"/login"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String haceLogin(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {
		System.out.println("Entra al controller de haceLogin::: DatosMaquinaController");
		datosEntradaEntityTO = agregaDatosEnDuro(datosEntradaEntityTO, request);
		ipServer = datosEntradaEntityTO.getIpServer();
		portServer = datosEntradaEntityTO.getPuertoServer();
		idSession = datosEntradaEntityTO.getIdSession();
		infoHash = datosEntradaEntityTO.getInfoHash();
		ResponseLoginAuthTO authTO = clienteLoginComponent.generaLogin(datosEntradaEntityTO);
		if(!authTO.getCodigoOperacion().equals("0")) {			
			request.getSession().invalidate();
		}
		return clienteLoginComponent.objToStrJson(authTO);
	}
	
	@RequestMapping(value={"/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionURL(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {		 
		String resultado = clienteLoginComponent.lanzaPathEspecifico(agregaDatosEnDuro(datosEntradaEntityTO, request));
		return resultado;
	}
	
	
	@RequestMapping(value={"/cerrarSesion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public void cerrarSesionURL(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {		 
		request.getSession().invalidate();
	}
	
	private DatosEntradaEntityTO agregaDatosEnDuro(DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {
		datosEntradaEntityTO.setAplicacion("Android-7.0-1000-BD");			
		if(!ValidacionesUtils.isNullOrEmpty(ipServer)) {
			datosEntradaEntityTO.setIpServer(ipServer);
		}
		if(!ValidacionesUtils.isNullOrEmpty(portServer)) {
			datosEntradaEntityTO.setPuertoServer(portServer);
		}
		if(!ValidacionesUtils.isNullOrEmpty(idSession)) {
			datosEntradaEntityTO.setIdSession(idSession);
		}else {
			datosEntradaEntityTO.setIdSession(request.getSession(true).getId());	
		}
		if(!ValidacionesUtils.isNullOrEmpty(infoHash)) {
			datosEntradaEntityTO.setInfoHash(infoHash);
		}else {
			datosEntradaEntityTO.setInfoHash("kasdfofgh4567dfgh234cfsdfsdfsdfsdfw34");
		}
		
		return datosEntradaEntityTO;
	}

}
