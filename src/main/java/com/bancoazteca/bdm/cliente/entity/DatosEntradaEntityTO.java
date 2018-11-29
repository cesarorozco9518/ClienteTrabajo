package com.bancoazteca.bdm.cliente.entity;

/**
 * @author Cesar M Orozco R
 *
 */
public class DatosEntradaEntityTO {
	
	private String ipServer;
	private String puertoServer;
	private String usuario;
	private String password;
	private String infoHash;
	private String pathService;
	private String request;
	private String aplicacion;
	private String cookie;
	private String tokenCode;
	private String idSession;
	private String sinSesion;	

	
	public String getSinSesion() {
		return sinSesion;
	}

	public void setSinSesion(String sinSesion) {
		this.sinSesion = sinSesion;
	}

	public String getIdSession() {
		return this.idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getTokenCode() {
		return this.tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public String getIpServer() {
		return this.ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public String getPuertoServer() {
		return this.puertoServer;
	}

	public void setPuertoServer(String puertoServer) {
		this.puertoServer = puertoServer;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInfoHash() {
		return this.infoHash;
	}

	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}

	public String getPathService() {
		return this.pathService;
	}

	public void setPathService(String pathService) {
		this.pathService = pathService;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
}
