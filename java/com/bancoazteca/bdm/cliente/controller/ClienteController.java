package com.bancoazteca.bdm.cliente.controller;

import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bancoazteca.bdm.bussiness.ClienteLoginComponent;
import com.bancoazteca.bdm.bussiness.EncripcionCadenaComponent;
import com.bancoazteca.bdm.encryptsecure.entity.beans.auth360.ResponseLoginAuthTO;
import com.bancoazteca.bdm.entity.DatosEntradaEntityTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Cesar M Orozco R
 *
 */
@RestController
@RequestMapping(value="/controllerCliente")
public class ClienteController {
	
	@Autowired
	private ClienteLoginComponent clienteLoginComponent;
	@Autowired
	private EncripcionCadenaComponent cadenaComponent;
	
	private Map<String, Object> datosSesion = new HashMap<String, Object>();
	
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
	 * @param datosEntradaEntityTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value= {"/login"}, produces= {"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String haceLogin(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {
		System.out.println("Entra al controller de haceLogin::: DatosMaquinaController");
		datosEntradaEntityTO.setInfoHash("kasdfofgh4567dfgh234cfsdfsdfsdfsdfw34");
		datosSesion.put("idSession", request.getSession(true).getId());
		datosSesion.put("ipServer", datosEntradaEntityTO.getIpServer());
		datosSesion.put("portServer", datosEntradaEntityTO.getPuertoServer());
		datosSesion.put("infoHash", "kasdfofgh4567dfgh234cfsdfsdfsdfsdfw34");
		datosSesion.put("app", "Android-7.0-1000-BD"); 
		ResponseLoginAuthTO authTO = clienteLoginComponent.generaLogin(datosEntradaEntityTO);
		return clienteLoginComponent.objToStrJson(authTO);
	}
	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/ejecutaPeticion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String ejecutaPeticionURL(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {		
		if(datosSesion.size()>0) {
			datosEntradaEntityTO.setIdSession(datosSesion.get("idSession").toString());
			datosEntradaEntityTO.setIpServer(datosSesion.get("ipServer").toString());
			datosEntradaEntityTO.setPuertoServer(datosSesion.get("portServer").toString());
			datosEntradaEntityTO.setInfoHash(datosSesion.get("infoHash").toString());
			datosEntradaEntityTO.setAplicacion(datosSesion.get("app").toString());
		}
		String resultado = clienteLoginComponent.lanzaPathEspecifico(datosEntradaEntityTO);
		return resultado;
	}
	
	@RequestMapping(value={"/encriptaCadena"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public String encriptaCadenaURL(@RequestBody String dato, HttpServletRequest request) {
		System.out.println("********************************************** cadena de entrada: " + dato);
		String cadenaCifrada = null;
		ObjectMapper mapper = new ObjectMapper();
		try {			
			cadenaCifrada = cadenaComponent.encryptString(dato);
			cadenaCifrada = mapper.convertValue(cadenaCifrada, String.class);
			System.out.println("*********************************************************   cadena encriptada: " + cadenaCifrada);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cadenaCifrada.toString();
	}
	
	
	/**
	 * @param datosEntradaEntityTO
	 * @param request
	 */
	@RequestMapping(value={"/cerrarSesion"}, produces={"application/json"}, method = RequestMethod.POST, consumes= {"application/json"})
	public void cerrarSesionURL(@RequestBody DatosEntradaEntityTO datosEntradaEntityTO, HttpServletRequest request) {		 
		request.getSession().invalidate();
	}
	

}
