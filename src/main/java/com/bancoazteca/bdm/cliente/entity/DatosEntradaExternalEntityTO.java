package com.bancoazteca.bdm.cliente.entity;

public class DatosEntradaExternalEntityTO {
	
	private String ipServer;
	private String portServer;
	private String usuario;
	private String password;
	private String pathServicio;
	private String request;
	private String cookie;
	private String tokenCode;	
	
	public String getIpServer() {
		return ipServer;
	}
	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}
	public String getPortServer() {
		return portServer;
	}
	public void setPortServer(String portServer) {
		this.portServer = portServer;
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
	public String getPathServicio() {
		return pathServicio;
	}
	public void setPathServicio(String pathServicio) {
		this.pathServicio = pathServicio;
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
