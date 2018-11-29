package com.bancoazteca.bdm.cliente.entity;

public class DatosEntradaInstaEntityTO {
	
	private String ipServer;
	private String puertoServer;
	private String usuario;
	private String password;
	private String pathService;
	private String request;
	private String cookie;
	private String tokenCode;
	private String aplicacion;
	
	
	public String getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	public String getIpServer() {
		return ipServer;
	}
	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}
	public String getPuertoServer() {
		return puertoServer;
	}
	public void setPuertoServer(String puertoServer) {
		this.puertoServer = puertoServer;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPathService() {
		return pathService;
	}
	public void setPathService(String pathService) {
		this.pathService = pathService;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getTokenCode() {
		return tokenCode;
	}
	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}	

}
