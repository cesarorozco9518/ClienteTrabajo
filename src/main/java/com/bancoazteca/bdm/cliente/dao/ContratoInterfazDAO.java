package com.bancoazteca.bdm.cliente.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.bancoazteca.bdm.cliente.commons.ValidacionesUtils;
import com.bancoazteca.bdm.cliente.entity.ContratoInterfazEntityTO;
import com.bancoazteca.bdm.cliente.entity.FiltroContratosEntityTO;

@Component
public class ContratoInterfazDAO {
	
	private static final Logger log = Logger.getLogger(ContratoInterfazDAO.class);
	
	private static final String FIELD_PATH = "path";
	private static final String FIELD_PLATAFORMA = "plataforma";
	private static final String FIELD_PLATAFORMA_CORE = "BAZ DIGITAL";
	private static final String FIELD_PLATAFORMA_INTERNAL = "Servicios Internos";
	private static final String FIELD_PLATAFORMA_MICRO = "MICROSERVICIOS";
	private static final String FIELD_PLATAFORMA_WALLET = "WALLET";
	
	@Autowired
	@Qualifier("primaryMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	/**
	 * @return
	 */
	public FiltroContratosEntityTO mostrar(){
		FiltroContratosEntityTO filtroContratosEntityTO = new FiltroContratosEntityTO();
		List<ContratoInterfazEntityTO> listaContratos = mongoTemplate.findAll(ContratoInterfazEntityTO.class);
		List<ContratoInterfazEntityTO> contratosCoreList = new ArrayList<>();
		List<ContratoInterfazEntityTO> contratosMicroList = new ArrayList<>();
		List<ContratoInterfazEntityTO> contratosInternalsList = new ArrayList<>();
		for (ContratoInterfazEntityTO contratoInterfazEntityTO : listaContratos) {
			switch (contratoInterfazEntityTO.getPlataforma()) {
			case "BAZ DIGITAL":
				contratosCoreList.add(contratoInterfazEntityTO);
				break;
			case "Servicios Internos":
				contratosInternalsList.add(contratoInterfazEntityTO);
				break;
			case "MICROSERVICIOS":
				contratosMicroList.add(contratoInterfazEntityTO);
				break;
			default:
				break;
			}
		}
		filtroContratosEntityTO.setContratosCore(contratosCoreList);
		filtroContratosEntityTO.setContratosInternals(contratosInternalsList);
		filtroContratosEntityTO.setContratosMicro(contratosMicroList);
		return filtroContratosEntityTO;
	}
	
	public List<ContratoInterfazEntityTO> mostrarWallet(){
		List<ContratoInterfazEntityTO> listaWallet = new ArrayList<>();
		List<ContratoInterfazEntityTO> listaContratos = mongoTemplate.findAll(ContratoInterfazEntityTO.class);
		for (ContratoInterfazEntityTO contratoInterfazEntityTO : listaContratos) {
			if(contratoInterfazEntityTO.getPlataforma().equalsIgnoreCase("WALLET")) {
				listaWallet.add(contratoInterfazEntityTO);
			}else {
				continue;
			}
		}
		return listaWallet;
	}
	
	
	/**
	 * @param path
	 * @return
	 */
	public String buscaRequestCore(String path) {
		ContratoInterfazEntityTO entityTO = new ContratoInterfazEntityTO();
		String jsonRequestCore = null;
		if(!ValidacionesUtils.isNullOrEmpty(path)) {
			Query query = Query.query(Criteria.where(FIELD_PATH).is(path).and(FIELD_PLATAFORMA).is(FIELD_PLATAFORMA_CORE));
			entityTO = mongoTemplate.findOne(query, ContratoInterfazEntityTO.class);
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				jsonRequestCore = entityTO.getJsonRequest();
			}else {
				jsonRequestCore = "No existe Request";
			}
		}
		return jsonRequestCore;
	}
	
	
	/**
	 * @param path
	 * @return
	 */
	public String buscaRequestInternal(String path) {
		ContratoInterfazEntityTO entityTO = new ContratoInterfazEntityTO();
		String jsonRequestCore = null;
		if(!ValidacionesUtils.isNullOrEmpty(path)) {
			Query query = Query.query(Criteria.where(FIELD_PATH).is(path).and(FIELD_PLATAFORMA).is(FIELD_PLATAFORMA_INTERNAL));
			entityTO = mongoTemplate.findOne(query, ContratoInterfazEntityTO.class);
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				jsonRequestCore = entityTO.getJsonRequest();
			}else {
				jsonRequestCore = "No existe Request";
			}
		}
		return jsonRequestCore;
	}
	
	
	/**
	 * @param path
	 * @return
	 */
	public String buscaRequestMicro(String path) {
		ContratoInterfazEntityTO entityTO = new ContratoInterfazEntityTO();
		String jsonRequestCore = null;
		if(!ValidacionesUtils.isNullOrEmpty(path)) {
			Query query = Query.query(Criteria.where(FIELD_PATH).is(path).and(FIELD_PLATAFORMA).is(FIELD_PLATAFORMA_MICRO));
			entityTO = mongoTemplate.findOne(query, ContratoInterfazEntityTO.class);
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				jsonRequestCore = entityTO.getJsonRequest();
			}else {
				jsonRequestCore = "No existe Request";
			}
		}
		return jsonRequestCore;
	}
	
	public String buscaRequestWallet(String path) {
		ContratoInterfazEntityTO entityTO = new ContratoInterfazEntityTO();
		String jsonRequestCore = null;
		if(!ValidacionesUtils.isNullOrEmpty(path)) {
			Query query = Query.query(Criteria.where(FIELD_PATH).is(path).and(FIELD_PLATAFORMA).is(FIELD_PLATAFORMA_WALLET));
			entityTO = mongoTemplate.findOne(query, ContratoInterfazEntityTO.class);
			if(!ValidacionesUtils.isNullOrEmpty(entityTO)) {
				jsonRequestCore = entityTO.getJsonRequest();
			}else {
				jsonRequestCore = "No existe Request";
			}
		}
		return jsonRequestCore;
	}

}
