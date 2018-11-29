var app = angular.module('clienteWALLET', ['blockUI', 'ngRoute', 'angular.filter']).config(function(blockUIConfig){
	blockUIConfig.message = 'Espere un momento, estamos trabajando en ello....'; 
});

app.controller('myWallet', function($scope, $http, $window, blockUI) {
	
	$scope.telefono=5535552151;
	$scope.nipWallet="111111";
	$scope.errorToken = false;
	$scope.errorServer = false;
	$scope.errorGeneral = false;
	$scope.errorJsonWallet = false;
	$scope.errorBloqueado = false;
	$scope.errorSMS = false;
	
	$http({url: '/ClienteBAZDigital/controllerClienteWallet/obtieneip', method: "GET"}).then(function(response) {
		$scope.ipWallet = response.data;
		$scope.puertoWallet = 8080;
	});	
	
	$http({url: '/ClienteBAZDigital/controllerClienteWallet/obtieneContratos', method: "GET"}).then(function(response){		
		$scope.contratosInterfazWallet = response.data;
	});
	
	$scope.lanzaLogin = function(){
		$scope.errorToken = false;
		$scope.errorServer = false;
		$scope.errorGeneral = false;
		$scope.errorBloqueado = false;
		$scope.errorSMS = false;
		$scope.contenedorPeticiones = false;
		var json = "{\"ipServer\":\""+$scope.ipWallet+"\","+
		 "\"puertoServer\":\""+$scope.puertoWallet+"\","+
		 "\"telefono\":\""+$scope.telefono+"\","+
		 "\"nipWallet\":\""+$scope.nipWallet+"\"}";
		$http({
	        url: '/ClienteBAZDigital/controllerClienteWallet/loginWallet',
	        method: "POST",
	        data: json
	    }).success(function(response) {
	    	if(response.codigoOperacion === "0"){
	    		alert("Login realizado con exito\n Bienvenido")
	    		$window.location.href = "/ClienteBAZDigital/indexBilletera"
	    	}else if(response.codigoOperacion === "-8505"){
	    		$scope.errorToken = true;
	    		$scope.folioError = response.folio;
	    	}else if(response.codigoOperacion === "-1"){
	    		$scope.folioError = response.folio;
	    		$scope.errorGeneral = true;
	    	}else if(response.codigoOperacion === "-8513"){
	    		$scope.folioError = response.folio;
	    		$scope.errorBloqueado = true;
	    	}else if(response.codigoOperacion === "2"){
	    		$scope.folioError = response.folio;
	    		$scope.errorSMS = true;
	    	}
	    }).error(function(response) {
	    	$scope.errorServer = true;
		});	
	};
	
	
	$scope.recuperaRequestWallet = function(e, myValue){
		var charCode = (e.which) ? e.which : e.keyCode;
		if(charCode === 13){
			var json = "{\"pathWallet\":\""+$scope.pathServicio+"\"}";			
			$http({
		        url: '/ClienteBAZDigital/controllerClienteWallet/obtieneRequestWallet',
		        method: "POST",
		        data: json
		    }).success(function(response) {
	    		var jsonResponse = JSON.stringify(response, null, '\t');
	    		$scope.requestWallet = jsonResponse;
		    }).error(function(response) {
		    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
		    	console('Error: '+response);
			});		
		}
	};
	
	$scope.lanzaPeticion = function(){
		$scope.checkedIp = true; 
		$scope.checkedPuerto = true;
		$scope.contenedorPeticiones = true;
		var json = "{\"pathServicio\":\""+$scope.pathServicio+"\",\"request\":"+reemplaza($scope.requestWallet)+"}";
		$http({
	        url: '/ClienteBAZDigital/controllerClienteWallet/lanzaPeticionWallet',
	        method: "POST",
	        data: json
	    }).success(function(response) {
	    	if(response.codigoOperacion==="100"){
	    		alert("Estimado usuario su sesion a expirado, sera redirigido a la pantalla de login.");
	    		$http({
	    	        url: '/ClienteBAZDigital/controllerClienteWallet/cerrarSesionWallet',
	    	        method: "GET"
	    	    }).success(function(response) {
	    	    	if(response.codigoOperacion==="0"){
	    	    		$scope.checkedIp = false; 
	    				$scope.checkedPuerto = false;
	    				$scope.contenedorPeticiones = false;
	    				$window.location.href = "/ClienteBAZDigital/billetera"
	    	    	}
	    	    }).error(function(response) {
	    	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	    	console('Error: '+response);
	    		});
	    		$window.location.href = "/ClienteBAZDigital/billetera"
	    	}else{
	    		var jsonResponse = JSON.stringify(response, null, '\t');
		    	$scope.responseWallet = jsonResponse;
		    	$scope.folioError = response.folio;
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});	
	};
	
	$scope.cerrarSesion = function(){
		$http({
	        url: '/ClienteBAZDigital/controllerClienteWallet/cerrarSesionWallet',
	        method: "GET"
	    }).success(function(response) {
	    	if(response.codigoOperacion==="0"){
	    		$scope.checkedIp = false; 
				$scope.checkedPuerto = false;
				$scope.contenedorPeticiones = false;
				$window.location.href = "/ClienteBAZDigital/billetera"
	    	}
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});	
	};
	
	$scope.lanzaPeticionSinSesion = function(){
		var json = "{\"ipServer\":\""+$scope.ipWalletSinSesion+"\","+
		 "\"puertoServer\":\""+$scope.puertoWalletSinSesion+"\","+
		 "\"pathServicio\":\""+$scope.pathServicioSinSesion+"\","+
		 "\"request\":"+reemplaza($scope.requestWalletSinSesion)+"\}";
		$http({
	        url: '/ClienteBAZDigital/controllerClienteWallet/lanzaPeticionWalletSinLogin',
	        method: "POST",
	        data: json
	    }).success(function(response) {
	    	var jsonResponse = JSON.stringify(response, null, '\t');
	    }).error(function(response) {
	    	alert("Estimado usuario ha ocurrido un error inesperado, intentelo de nuevo.");
	    	console('Error: '+response);
		});	
	};
	
});

function reemplaza(entrada){
	entrada = entrada.replace(/\t/g, "");
	entrada=entrada.replace(/\s*"/g, "\"");
	entrada=entrada.replace(/\n/g, "");
	entrada=entrada.replace(/"/g, "\\\"");
	return "\""+entrada+"\"";
}
