package com.bancoazteca.bdm.cliente.entity;

/**
 * @author Cesar M Orozco R
 *
 */
public class MantieneSesionEntityTO {

	private String pathDomain;
	private String cookie;
	private String aesKey;
	private String OTP;
	private String postRequest;
	private String infoHash;
	private String userCert;
	private String ip;
	private String tokeCode;
	
	
	public String getPathDomain() {
		return pathDomain;
	}
	public void setPathDomain(String pathDomain) {
		this.pathDomain = pathDomain;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getPostRequest() {
		return postRequest;
	}
	public void setPostRequest(String postRequest) {
		this.postRequest = postRequest;
	}
	public String getInfoHash() {
		return infoHash;
	}
	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}
	public String getUserCert() {
		return userCert;
	}
	public void setUserCert(String userCert) {
		this.userCert = userCert;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTokeCode() {
		return tokeCode;
	}
	public void setTokeCode(String tokeCode) {
		this.tokeCode = tokeCode;
	}

}
