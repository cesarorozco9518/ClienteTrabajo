/**
 * @author Cesar M Orozco R
 *
 */

var app = angular.module('cliente', ['blockUI', 'ngRoute', 'angular.filter']).config(function(blockUIConfig){
	blockUIConfig.message = 'Espere un momento, estamos trabajando en ello....'; 
});

app.controller('myCtrl', function($scope, $http, $window, blockUI) {
	
	$http({url: '/ClienteBAZDigital/controllerCliente/obtieneip', method: "GET"}).then(function(response) {
		$scope.ip = response.data;
		$scope.puerto = 8080;
		$scope.ipCoreA = response.data;
		$scope.puertoCoreA = 8080;
		$scope.usuario = "elizandro135";
		$scope.pass = "contra1245";
		$scope.alerta1 = false;
		$scope.alerta2 = false;
		$scope.alerta3 = false;
		$scope.ipMicro = response.data;
		$scope.puertoMicro = 8080;
		$scope.ipServerInst = response.data;
		$scope.portServerInst = 8080;
	});	
	
	
	$http({url: '/ClienteBAZDigital/controllerCliente/obtieneContratos', method: "GET"}).then(function(response){		
		$scope.ipJVC = "10.51.53.90";
		$scope.ipExternals = "10.51.53.90";
		$scope.puertoExternals = 8443;
		$scope.puertoJVC = 8443;
		$scope.portServerInst = 8080;
		$scope.userExternals = "JVCiOS";
		$scope.passExternals = "t8M0sd71+PcENVuqnBeMPzXnMuIliGJoK1oy436XJCo=";		
		$scope.icuCliente = "cede8c790070447abbf52962616e7e8e";
		$scope.usuarioInst = "elizandro135";
		$scope.passInst = "portal19"; 
		$scope.contratosInterfazCore = response.data.contratosCore;
		$scope.contratosInterfazInternals = response.data.contratosInternals;
		$scope.contratosInterfazMicro = response.data.contratosMicro;
		$scope.errorServer = false;
		$scope.errorServerExternals = false;
		$scope.alertaAnotacion = false;
		$scope.error = false;
		$scope.error1 = false;
	});
	
	
	$scope.datosLogin = function(){
		$scope.alerta1 = false;
		$scope.alerta2 = false;
		$scope.alerta3 = false;
		var json = "{\"ipServer\":\""+$scope.ip+"\","+
		 "\"puertoServer\":\""+$scope.puerto+"\","+
			 "\"usuario\":\""+$scope.usuario+"\","+
			 "\"password\":\""+$scope.pass+"\","+
			 "\"infoHash\":\""+"\","+
			 "\"pathService\":\""+"\","+
			"\"cookie\":\""+"\","+
			"\"tokeCode\":\""+"\","+
			 "\"request\":\""+"\""+
			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/loginBAZ',
	        method: "POST",
	        data: json
	    }).success(function(response) {	    	
	    	switch(response.codigoOperacion){
		    	case '0':
		    		alert("Login realizado con exito.\nBienvenido");
		    		$window.location.href = "/ClienteBAZDigital/cliente";
		    			break;
		    	case '-1':
		    		$scope.alerta1 = true;
		    		$scope.folioOperacion = response.folio;
		    			break;
		    	case '-1119':
		    		$scope.alerta2 = true;
		    		$scope.folioOperacion = response.folio;
		    		break;
		    	case '-1069':
		    		$scope.alerta3 = true;
		    		$scope.folioOperacion = response.folio;
		    			break;
		    	default:
		    		$scope.alerta1 = true;
					$scope.folioOperacion = response.folio;
						break;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.recuperaRequestCore = function(e, myValue){
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			var json = "{\"pathCore\":\""+$scope.pathServicio+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerCliente/obtieneRequestCore',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.request = jsonResponse;
	    		$scope.responseFront = "";
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};

	
	$scope.recojeDatos = function(){		
		var json = "{\"ipServer\":\""+$scope.ipServer+"\","+
			 "\"puertoServer\":\""+$scope.portServer+"\","+
   			 "\"usuario\":\""+"\","+
   			 "\"password\":\""+"\","+
   			 "\"infoHash\":\""+"\","+
   			"\"sinSesion\":\""+$scope.sinSesion+"\","+
   			 "\"pathService\":\""+$scope.pathServicio+"\","+
   			"\"cookie\":\""+"\","+
   			"\"tokeCode\":\""+$scope.tokencode+"\","+
   			 "\"request\":"+reemplaza($scope.request)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/ejecutaPeticion',
	        method: "POST",
	        data: json
	    }).success(function(response) {
	    	if(response.codigoOperacion === "100"){
	    		alert("Lo sentimos, su sesion a expirado.");
	    		$window.location.href = "/ClienteBAZDigital/login";
	    	}else{
	    		var jsonResponse = JSON.stringify(response, null, '\t');
		    	$scope.folioOperacion = response.folio;
		    	$scope.responseFront = jsonResponse;
	    	}    		
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.recuperaRequestInst = function(e, myValue){
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			var json = "{\"pathCore\":\""+$scope.pathServicioInst+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerCliente/obtieneRequestCore',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.requestInst = jsonResponse;
	    		$scope.responseFront = "";
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};
	
	$scope.recojeDatosInstanataneo = function(){		
		var json = "{\"ipServer\":\""+$scope.ipServerInst+"\","+
			 "\"puertoServer\":\""+$scope.portServerInst+"\","+
   			 "\"usuario\":\""+$scope.usuarioInst+"\","+
   			 "\"password\":\""+$scope.passInst+"\","+
   			 "\"pathService\":\""+$scope.pathServicioInst+"\","+
   			 "\"request\":"+reemplaza($scope.requestInst)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/intantaneo/ejecutaPeticion',
	        method: "POST",
	        data: json
	    }).success(function(responseInst) {
	    	if(responseInst === ""){
	    		alert("Estimado usuario, no hemos podido establecer conexion con su servidor.\nIntenetelo mas tarde o verifique su servidor");
	    		$scope.responseInst = "{\"descError\":\"Estimado usuario, no hemos podido establecer conexion con su servidor. Intenetelo mas tarde o verifique su servidor\"}";
	    	}else{
	    		$scope.folioInst = responseInst.folio;
		    	var jsonResponse = JSON.stringify(responseInst, null, '\t');
		    	$scope.responseInst = jsonResponse;
	    	}	    	
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.recuperaRequestInternal = function(e, myValue){
		$scope.errorServer = false;
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			var json = "{\"pathInternal\":\""+$scope.pathServicioInternal+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerCliente/internal/obtieneRequestInternal',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.requestInternal = jsonResponse;
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};
	
	$scope.recojeDatosInternal = function(){
		$scope.errorServer = false;
		var json = "{\"ipServerJVC\":\""+$scope.ipJVC+"\","+
			 "\"portServerJVC\":\""+$scope.puertoJVC+"\","+
   			 "\"usuario\":\""+"\","+
   			 "\"password\":\""+"\","+
   			 "\"infoHash\":\""+"\","+
   			 "\"pathInternal\":\""+$scope.pathServicioInternal+"\","+
   			 "\"requestInternal\":"+reemplaza($scope.requestInternal)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/internal/ejecutaPeticion',
	        method: "POST",
	        data: json
	    }).success(function(responseInternal) {
	    	if(responseInternal === ""){
	    		$scope.errorServer = true;
	    		$scope.responseFrontInternal = "{\"timeOut\":\"Lo sentimos, no se pudo establecer conexión con el servidor.\"}";
	    	}else{
	    		$scope.errorServer = false;
	    		$scope.folioOperacionInternal = responseInternal.folio;
	    		var jsonResponseInternal = JSON.stringify(responseInternal, null, '\t');
		    	$scope.responseFrontInternal = jsonResponseInternal;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.recojeDatosExternal = function(){
		$scope.errorServerExternals = false;
		var json = "{\"ipServer\":\""+$scope.ipExternals+"\","+
			 "\"portServer\":\""+$scope.puertoExternals+"\","+
   			 "\"usuario\":\""+$scope.userExternals+"\","+
   			 "\"password\":\""+$scope.passExternals+"\","+
   			 "\"pathServicio\":\""+$scope.pathServicioExternals+"\","+
   			 "\"request\":"+reemplaza($scope.requestExternals)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/external/ejecutapeticion',
	        method: "POST",
	        data: json
	    }).success(function(responseExternals) {
	    	if(responseExternals === ""){
	    		$scope.errorServerExternals = true;
	    		$scope.responseFrontExternals = "{\"timeOut\":\"Lo sentimos, no se pudo establecer conexión con el servidor.\"}";
	    	}else{
	    		$scope.errorServerExternals = false;
	    		$scope.folioExternals = responseExternals.folio;
	    		var jsonResponseExternals = JSON.stringify(responseExternals, null, '\t');
		    	$scope.responseFrontExternals = jsonResponseExternals;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	
	$scope.recuperaRequestMicro = function(e, myValue){
		$scope.errorMicro = false;
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			var json = "{\"pathMicro\":\""+$scope.pathServicioMicro+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerCliente/micro/obtieneRequestMicro',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.requestMicro = jsonResponse;
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};
	
	$scope.recojeDatosMicroservicio = function(){
		var json = "{\"ipServerMicro\":\""+$scope.ipMicro+"\","+
			 "\"portServerMicro\":\""+$scope.puertoMicro+"\","+
   			 "\"usuario\":\""+"\","+
   			 "\"password\":\""+"\","+
   			 "\"peticionBilletera\":\""+$scope.peticionBilletera+"\","+
   			 "\"pathMicro\":\""+$scope.pathServicioMicro+"\","+
   			 "\"icuCliente\":\""+$scope.icuCliente+"\","+
   			 "\"requestMicro\":"+reemplaza($scope.requestMicro)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/micro/ejecutaPeticion',
	        method: "POST",
	        data: json
	    }).success(function(responseMicro) {
	    	if(responseMicro === ""){
	    		$scope.errorMicro = true;
	    		$scope.responseFrontMicro = "{\"timeOut\":\"Lo sentimos, no se pudo establecer conexión con el servidor.\"}";
	    	}else{
	    		$scope.errorMicro = false;
	    		$scope.folioOperacionMicro = responseMicro.folio;
	    		var jsonResponseMicro = JSON.stringify(responseMicro, null, '\t');
		    	$scope.responseFrontMicro = jsonResponseMicro;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.recuperaRequestCoreA = function(e, myValue){
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			$scope.error1 = false;
			var json = "{\"pathCore\":\""+$scope.pathServicioCoreA+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerCliente/obtieneRequestCore',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.requestCoreA = jsonResponse;
	    		$scope.alertaAnotacion = true;
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};
	
	$scope.recojeDatosCoreA = function(){
		$scope.alertaAnotacion = false;
		$scope.error = false;
		$scope.error1 = false;
		var json = "{\"ipServerAsyn\":\""+$scope.ipCoreA+"\","+
			 "\"portServerAsyn\":\""+$scope.puertoCoreA+"\","+
   			 "\"pathAsyn\":\""+$scope.pathServicioCoreA+"\","+
   			 "\"requestAsyn\":"+reemplaza($scope.requestCoreA)+
   			"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/ejecutaPeticionAsyncrona',
	        method: "POST",
	        data: json
	    }).success(function(responseAsync) {
	    	if(responseAsync === ""){
	    		$scope.error = true;
	    	}else if(responseAsync.codigoOperacion === "100"){
	    		$scope.error1 = true;
	    		$scope.folioOperacionMicro = responseAsync.folio;
	    		var jsonResponseAsync = JSON.stringify(responseAsync, null, '\t');
		    	$scope.responseCoreA = jsonResponseAsync;
	    	}else{
	    		$scope.folioOperacionMicro = responseAsync.folio;
	    		var jsonResponseAsync = JSON.stringify(responseAsync, null, '\t');
		    	$scope.responseCoreA = jsonResponseAsync;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};	
	
	$scope.encriptarDatos = function(){
		var json = $scope.cadenaACifrar;
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/encriptaCadena',
	        method: "POST",
        	data: json
	    }).success(function(response) {    		
	    	$scope.cadenaProcesada = response.cadenaCifrada;
	    	$scope.cadenaACifrar = "";
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	
	$scope.desencriptarDatos = function(){
		var json = $scope.cadenaADesencriptar;
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/desencriptaCadena',
	        method: "POST",
        	data: json
	    }).success(function(response) {    		
	    	$scope.cadenaProcesada = response.cadenaSinCifrar;
	    	$scope.cadenaADesencriptar = "";
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});		
	};
	
	$scope.cerrarSesion = function(){
		var json = "{\"tokenCode\":\""+"\","+
		 "\"aplicacion\":\""+"\","+
			 "\"ip\":\""+"\","+
			 "\"puerto\":\""+"\","+
			 "\"infoHash\":\""+"\"}";
		$http({
	        url: '/ClienteBAZDigital/controllerCliente/cerrarsesion',
	        method: "POST",
        	data: json
	    }).success(function(response) {    		
	    	if(response.codigoOperacion === "0"){
	    		$window.location.href = "/ClienteBAZDigital/login"	
	    	}else{
	    		alert("Ocurrio un error al procesar la peticion, se redirigira a la pagina principal.");
	    		$window.location.href = "/ClienteBAZDigital/inicio"
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, se redirigira a la pagina principal.");
	    	$window.location.href = "/ClienteBAZDigital/inicio"
	    	console('Error: '+response);
		});	
	}
	
	$scope.cambiarIP = function(){
    	$window.location.href = "/ClienteBAZDigital/coreLogin"			
	};	
	
	$scope.cambiaIpHome = function(){
		$window.location.href = "/ClienteBAZDigital/inicio"
	};
	
	$scope.cambioWallet = function(){
		$window.location.href = "/ClienteBAZDigital/billetera"
	};
	
	$scope.cambioAgenda = function(){
		$window.location.href = "/ClienteBAZDigital/agenda"
	};
	
	$scope.limpiaCamposModalInternal = function(){
		$scope.pathServicioInternal = "";
		$scope.requestInternal = "";
		$scope.responseFrontInternal = "";
		$scope.pathServicioMicro = "";
		$scope.folioOperacionMicro = "";
		$scope.requestMicro = "";
		$scope.responseFrontMicro = "";
		$scope.folioOperacionInternal = "";
		$scope.pathServicioCoreA = "";
		$scope.requestCoreA = "";
		$scope.responseCoreA = "";
	}

});


function reemplaza(entrada){
	entrada = entrada.replace(/\t/g, "");
	entrada=entrada.replace(/\s*"/g, "\"");
	entrada=entrada.replace(/\n/g, "");
	entrada=entrada.replace(/"/g, "\\\"");
	return "\""+entrada+"\"";
}

