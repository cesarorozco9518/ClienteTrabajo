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
    
    <script src="<c:url value='/resources/js/clienteWallet.js' />"></script>
	
	</head>
<body data-ng-app="clienteWALLET" data-ng-controller="myWallet">

	<div class="alert alert-success">
      <h1><center><strong>AzteKWallet</strong> Cliente area de servicios.</center></h1>
    </div>
    
    <div class="container">
        <div class="form-group row">
            <div class="col-3">
                <label for="request">Ip del servidor:</label>
                <input class="form-control" type="text" data-ng-model="ipWallet" data-ng-readonly="checkedIp">
            </div>
            <div class="col-2">
                <label for="request">Puerto del servidor:</label>
                <input class="form-control" type="number" data-ng-model="puertoWallet" data-ng-readonly="checkedPuerto">
            </div>
            <div class="col-4">
                <label for="request">Folio de operacion:</label>
                <input class="form-control" type="text" data-ng-model="folioError" readonly="readonly">
            </div>            
            <!--<div class="col-12">
                <br/>
                <button type="button" class="btn btn-outline-danger btn-block" data-ng-click="lanzaLogin()" data-ng-show="!contenedorPeticiones">Iniciar Sesion </button>
                <button type="button" class="btn btn-outline-danger btn-block" data-ng-click="" data-toggle="modal" data-target="#sinSesion" data-ng-show="!contenedorPeticiones">Peticiones sin sesion </button>
            	<label for="request" data-ng-show="contenedorPeticiones">Seleccione la opcion deseada: </label>              
			  	<button type="button" class="btn btn-outline-info btn-block" data-ng-click="lanzaPeticion()" data-ng-show="contenedorPeticiones">Peticion </button>
				<button type="button" class="btn btn-outline-warning btn-block" data-ng-show="contenedorPeticiones" data-ng-click="cerrarSesion()">Cerrar Sesion </button>
            </div>-->
        </div>
    </div>
    <center>        
    <div class="col-3">
        <br/><br/><br/>        
        <div class="card">
            <div class="alert alert-danger" data-ng-show="errorToken">Lo sentimos el Token es invalido.</div>
            <div class="alert alert-danger" data-ng-show="errorGeneral">Lo sentimos a ocurrido un error al realizar la operacion.</div>
            <div class="alert alert-warning" data-ng-show="errorServer">Lo sentimos no se pudo establecer conexion con el servidor.</div>
            <div class="alert alert-warning" data-ng-show="errorBloqueado">Estimado cliente, su usuario se encuentra bloqueado.</div>
            <div class="alert alert-warning" data-ng-show="errorSMS">Lo sentimos, el codigo SMS no se ha verificado, no puede continuar.</div>
            <img src="http://10.112.29.45:8081/efectivomovil/resources/imgs/aztk_wallet_logo_splash.png" class="rounded" alt="Cinque Terre" width="200" height="210">
            <div class="card-body">
                <div class="form-inline">
                    <label for="telefono">Telefono Wallet:</label>
                    <input class="form-control" type="number" data-ng-model="telefono">
                </div>
                <br/>
                <div class="form-inline">
                    <label for="nipWallet">Nip AztKWallet:</label>
                    <input class="form-control" type="text" data-ng-model="nipWallet">
                </div>
            </div>
            <button type="button" class="btn btn-outline-success btn-block" data-ng-click="lanzaLogin()" data-ng-show="!contenedorPeticiones">Iniciar Sesion </button>
        </div>          
    </div>
    </center>
    
   <!-- <div class="container" data-ng-show="contenedorPeticiones">
    	<div class="form-group row">
            <div class="col-8">
                <label for="pathWallet">Path Servico Wallet:</label> 
                <input list="listaPathsWallet" class="form-control" placeholder="/wallet/***" data-ng-model="pathServicio" data-ng-keypress="recuperaRequestWallet($event, pathServicio)">
                 <datalist id="listaPathsWallet">
                     <option data-ng-repeat="pathWalllets in contratosInterfazWallet" value={{pathWalllets.path}} />
                 </datalist>
            </div> 
        </div>
        <label for="comment">Request:</label>
        <textarea class="form-control" rows="8" data-ng-model="requestWallet"></textarea>
        <label for="comment">Response:</label>
        <textarea class="form-control" rows="8" data-ng-model="responseWallet" readonly="readonly"></textarea>
    </div> -->
    
     <!-- The Modal -->
    <div class="modal fade" id="sinSesion">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Peticiones sin sesion</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>        
        <!-- Modal body -->
        <div class="modal-body">
            <div class="form-group row">
                <div class="col-3">
                    <label for="request">Ip Jboss:</label>
                    <input class="form-control" type="text" data-ng-model="ipWalletSinSesion">
                </div>
                <div class="col-2">
                    <label for="request">Puerto Jboss:</label>
                    <input class="form-control" type="number" data-ng-model="puertoWalletSinSesion">
                </div>
                <div class="col-6">
                <label for="pathWallet">Path Servico Wallet:</label> 
                <input list="listaPathsWallet" class="form-control" placeholder="/wallet/***" data-ng-model="pathServicioSinSesion" data-ng-keypress="recuperaRequestWallet($event, pathServicio)">
                 <datalist id="listaPathsWallet">
                     <option data-ng-repeat="pathWalllets in contratosInterfazWallet" value={{pathWalllets.path}} />
                 </datalist>
                </div> 
            </div>
            <label for="comment">Request:</label>
            <textarea class="form-control" rows="8" data-ng-model="requestWalletSinSesion"></textarea>
            <label for="comment">Response:</label>
            <textarea class="form-control" rows="8" data-ng-model="responseWalletSinSesion" readonly="readonly"></textarea>
            <br/>
            <button type="button" class="btn btn-outline-success btn-block" data-ng-click="lanzaPeticionSinSesion()">Peticion </button>
            <button type="button" class="btn btn-outline-dark btn-block">Copiar Response </button>
        </div>        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
        </div>        
      </div>
    </div>
  </div>
	
</body>

</html>