package com.bancoazteca.bdm.cliente.entity;

public class DatosEntradaBilletera {
	
	private String ipServer;
	private String puertoServer;
	private String request;
	private String pathServicio;
	private String telefono;
	private String nipWallet;
	private String cookie;
	
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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getPathServicio() {
		return pathServicio;
	}
	public void setPathServicio(String pathServicio) {
		this.pathServicio = pathServicio;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getNipWallet() {
		return nipWallet;
	}
	public void setNipWallet(String nipWallet) {
		this.nipWallet = nipWallet;
	}
	
	
}
