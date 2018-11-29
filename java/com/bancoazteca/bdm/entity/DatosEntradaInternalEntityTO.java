package com.bancoazteca.bdm.entity;

public class DatosEntradaInternalEntityTO {
	
	private String ipServerJVC;
	private String portServerJVC;
	private String pathInternal;
	private String requestInternal;
	private String usuario;
	private String password;
	private String infoHash;
	
	
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
	public String getInfoHash() {
		return infoHash;
	}
	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}
	public String getIpServerJVC() {
		return ipServerJVC;
	}
	public void setIpServerJVC(String ipServerJVC) {
		this.ipServerJVC = ipServerJVC;
	}
	public String getPortServerJVC() {
		return portServerJVC;
	}
	public void setPortServerJVC(String portServerJVC) {
		this.portServerJVC = portServerJVC;
	}
	public String getPathInternal() {
		return pathInternal;
	}
	public void setPathInternal(String pathInternal) {
		this.pathInternal = pathInternal;
	}
	public String getRequestInternal() {
		return requestInternal;
	}
	public void setRequestInternal(String requestInternal) {
		this.requestInternal = requestInternal;
	}
	
	

}
