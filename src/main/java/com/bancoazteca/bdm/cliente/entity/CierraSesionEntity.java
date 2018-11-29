package com.bancoazteca.bdm.cliente.entity;

public class CierraSesionEntity {
	
	private String tokenCode;
	private String aplicacion;
	private String ip;
	private String puerto;
	private String infoHash;
	private String requestFinSesion;
	private String cookie;
	
	
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
	public String getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getInfoHash() {
		return infoHash;
	}
	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}
	public String getRequestFinSesion() {
		return requestFinSesion;
	}
	public void setRequestFinSesion(String requestFinSesion) {
		this.requestFinSesion = requestFinSesion;
	}
	

}
