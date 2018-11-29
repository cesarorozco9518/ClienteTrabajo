package com.bancoazteca.bdm.cliente.entity;

import java.util.List;

public class FiltroContratosEntityTO {
	
	private List<ContratoInterfazEntityTO> contratosCore;
	private List<ContratoInterfazEntityTO> contratosInternals;
	private List<ContratoInterfazEntityTO> contratosMicro;
	
	
	public List<ContratoInterfazEntityTO> getContratosCore() {
		return contratosCore;
	}
	public void setContratosCore(List<ContratoInterfazEntityTO> contratosCore) {
		this.contratosCore = contratosCore;
	}
	public List<ContratoInterfazEntityTO> getContratosInternals() {
		return contratosInternals;
	}
	public void setContratosInternals(List<ContratoInterfazEntityTO> contratosInternals) {
		this.contratosInternals = contratosInternals;
	}
	public List<ContratoInterfazEntityTO> getContratosMicro() {
		return contratosMicro;
	}
	public void setContratosMicro(List<ContratoInterfazEntityTO> contratosMicro) {
		this.contratosMicro = contratosMicro;
	}
	
		
	

}
