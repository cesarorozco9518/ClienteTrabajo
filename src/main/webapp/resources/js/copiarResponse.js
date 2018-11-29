function copiaResponseInsta() {	
	var validacionCampo = document.getElementById("responseInsta").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseInsta");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}

function copiaResponseAsyncrona() {
	var validacionCampo = document.getElementById("responseAsync").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseAsync");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}


function copiaResponseMicro() {
	var validacionCampo = document.getElementById("responseMicro").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseMicro");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}


function copiaResponseExternal() {
	var validacionCampo = document.getElementById("responseExternals").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseExternals");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}


function copiaResponseInternal() {
	var validacionCampo = document.getElementById("responseInternal").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseInternal");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}

function copiaResponseCore() {
	var validacionCampo = document.getElementById("responseFront").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseFront");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}

function copiaResponseWallet() {
	var validacionCampo = document.getElementById("responseWallet").value;
	if(validacionCampo === ""){
		alert("Lo sentimos no hay nada que copiar");
	}else{
		var copyText = document.getElementById("responseWallet");
		copyText.select();
		document.execCommand("Copy");
		alert("El texto se ha copiado correctamente.");
	}
}