<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="false"%>
<html lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cliente BazDigital WALLET</title>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.css" />
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.css" />
	    <script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
	    <script	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-beta.2/angular-route.min.js"></script>
	    <script src="https://code.angularjs.org/1.4.8/angular-resource.min.js"></script>
	    <script	src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.js"></script>
	    <script	src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-filter/0.5.8/angular-filter.min.js"></script>
	    <script src="<c:url value='/resources/js/copiarResponse.js' />"></script>
	    <script src="<c:url value='/resources/js/clienteWallet.js' />"></script>	
	</head>
    
<body data-ng-app="clienteWALLET" data-ng-controller="myWallet">
    
    <div class="alert alert-info">
        <h4><center><strong>AztekWallet</strong> Cliente Area Servicios.</center></h4>
    </div>
    
    <div class="container">
        <div class="row">
            <div class="col-xl-4">
                <div class="form-group">
                    <label>Path de Servicio: </label>
                     <input list="listaPathsWallet" class="form-control" placeholder="/wallet/***" data-ng-model="pathServicio" data-ng-keypress="recuperaRequestWallet($event, pathServicio)">
                     <datalist id="listaPathsWallet">
                         <option data-ng-repeat="pathWalllets in contratosInterfazWallet" value={{pathWalllets.path}} />
                     </datalist>
                </div>
            </div>
            <div class="col-xl-4">
                <div class="form-group">
                    <label>Folio de Peticion: </label>
                    <input type="text" class="form-control" data-ng-model="folioError" readonly="readonly">
                </div>
            </div>
            <div class="col-2">
                <div class="form-group">
                    <label>Seleccione la opcion: </label>
                    <div class="btn-group">
                      <button type="button" class="btn btn-success" data-ng-click="lanzaPeticion()">Lanzar Peticion</button>
                      <button type="button" class="btn btn-warning" disabled>Peticion sin sesion</button>
                      <button type="button" class="btn btn-danger" data-ng-click="cerrarSesion()">Cerrar Sesion</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-xl-12">
                <div class="form-group">
                  <label for="request">RequestTO:</label>
                  <textarea class="form-control" rows="10" id="request" data-ng-model="requestWallet"></textarea>
                </div>
                <div class="alert alert-warning" data-ng-show="errorJsonWallet">
                  <center><strong>Estimado Usuario!</strong> Detectamos que a ingresado de forma erronea el json, porfavor verifiquelo y vuelvalo a intentar.</center>
                </div>
                <div class="form-group">
                  <label for="response">ResponseTO:</label>
                  <textarea class="form-control" rows="10" id="responseWallet" readonly="readonly" data-ng-model="responseWallet">{}</textarea>
                </div>                
            </div>
        </div>
        <button type="button" class="btn btn btn-outline-dark btn-block" onclick="copiaResponseWallet()">Copiar ResponseTO</button>
    </div>

</body>
</html>