package com.bancoazteca.bdm.cliente.entity;

/**
 * @author Cesar M Orozco R
 *
 */
public class DatosEntradaInternalEntityTO {
	
	private String ipServerJVC;
	private String portServerJVC;
	private String pathInternal;
	private String requestInternal;
	
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
