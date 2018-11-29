package com.bancoazteca.bdm.cliente.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="AdministradorContratoInterfaz")
public class ContratoInterfazEntityTO {
	
	@Id
	private String id;
	
	private String plataforma;
	private String area;
	private String modulo;
	private String flujo;
	private String servicio;	
	
	private String metodoType;
	private String contentType;
	private String path;
			
	private String jsonRequest;
	private String jsonResponse;
	
	private String claseRequest;
	private String claseResponse;
	
	private List<Integer> errores;
	private List<FormatoTO> request;
	private List<FormatoTO> response;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getFlujo() {
		return flujo;
	}
	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getMetodoType() {
		return metodoType;
	}
	public void setMetodoType(String metodoType) {
		this.metodoType = metodoType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getJsonRequest() {
		return jsonRequest;
	}
	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}
	public String getJsonResponse() {
		return jsonResponse;
	}
	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	public String getClaseRequest() {
		return claseRequest;
	}
	public void setClaseRequest(String claseRequest) {
		this.claseRequest = claseRequest;
	}
	public String getClaseResponse() {
		return claseResponse;
	}
	public void setClaseResponse(String claseResponse) {
		this.claseResponse = claseResponse;
	}
	public List<Integer> getErrores() {
		return errores;
	}
	public void setErrores(List<Integer> errores) {
		this.errores = errores;
	}
	public List<FormatoTO> getRequest() {
		return request;
	}
	public void setRequest(List<FormatoTO> request) {
		this.request = request;
	}
	public List<FormatoTO> getResponse() {
		return response;
	}
	public void setResponse(List<FormatoTO> response) {
		this.response = response;
	}
	

}
