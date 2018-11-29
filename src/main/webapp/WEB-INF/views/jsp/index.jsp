<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page session="false"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Cliente BazDigital</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.css" />
	
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-beta.2/angular-route.min.js"></script>
	<script src="https://code.angularjs.org/1.4.8/angular-resource.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-filter/0.5.8/angular-filter.min.js"></script>
	<script src="<c:url value='/resources/js/ClienteScript.js' />"></script>
	<script src="<c:url value='/resources/js/copiarResponse.js' />"></script>
	
	</head>
<body data-ng-app="cliente" data-ng-controller="myCtrl">

	<div class="alert alert-success">
		<h4>
			<center>
				<strong>Cliente</strong> Banco Azteca Movil
			</center>
		</h4>
	</div>
	<div class="container">
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#core">Core</a></li>
		</ul>
		
		<!-- Tab de Cliente Core -->
		<div class="tab-content">
			<!-- Tab de Cliente Core -->
			<div id="core" class="tab-pane fade in active">
				<div class="container-fluid">
					<br />
					<div class="form-group">
						<div class="col-xs-4">
							<label for="path">Path Servico:</label> 
							<input list="listaPathsCore" class="form-control" data-ng-model="pathServicio" data-ng-keypress="recuperaRequestCore($event, pathServicio)" placeholder="Path Service" required autofocus>
							 <datalist id="listaPathsCore">
			                	<option data-ng-repeat="pathCore in contratosInterfazCore" value={{pathCore.path}}/>
			                </datalist>
						</div>
						<div class="col-xs-4">
							<label for="folio">Folio de operación:</label> 
							<input type="text" class="form-control" id="folioOperacion" data-ng-model="folioOperacion" disabled>
						</div>
						<div class="col-xs-4">
							<label for="folio">Cerrar Sesion:</label>
							<button type="button" class="btn btn-dafault btn-lg btn-block" data-ng-click="cerrarSesion()"><span class="glyphicon glyphicon-remove-sign"></span> Cerrar Sesion</button>
						</div>
						<br />
					</div>
					<br/><br/> 
					<input type="checkbox" data-ng-model="sinSesion" value="false">Peticion sin OTP 
					<br/><br/>
					<div class="alert alert-danger" data-ng-if="sinSesion">
					  <center><strong>Atencion!</strong> usted va a realizar una peticion sin sesión, esto quiere decir que no va a validar OTP.</center>
					</div>
					
					<div class="col-xs-12">
						<div class="form-group">
							<center><label for="request">Request:</label></center>
							<textarea class="form-control" rows="8" data-ng-model="request"></textarea>
						</div>
					</div>

					<div class="col-xs-12">
						<div class="form-group">
							<center><label for="response">Response:</label></center>
							<textarea class="form-control" rows="8" id="responseFront" data-ng-model="responseFront" readonly="readonly"></textarea>
						</div>
					</div>					
					<button type="button" class="btn btn-success btn-lg btn-block" data-ng-click="recojeDatos()">Enviar Peticion</button>
					<button type="button" class="btn btn-default btn-lg btn-block" onclick="copiaResponseCore()">Copiar ResponseTO</button>
				</div>
			</div>

			<!-- Tab de Cliente Internal -->
			<div id="internal" class="tab-pane fade">
				<br/>
				<div class="form-group">
						<div class="col-xs-4">
							<label for="ipJVC">Ip JVC:</label>
							<input type="text" class="form-control" data-ng-model="ip">
						</div>
						<div class="col-xs-2">
							<label for="puertoJVC">Puerto JVC:</label> 
							<input type="number" class="form-control" id="puertoJVC" data-ng-model="puerto">
						</div>
						<div class="col-xs-5">
						<label for="folio">Folio de operación:</label> 
						<input type="text" class="form-control" id="folioOperacionInternal" data-ng-model="folioOperacionInternal" disabled>
					</div>
						<br />
					</div>
					<br/>
				<div class="form-group">
				<br/>
					<div class="col-xs-6">
						<label for="pathInternal">Path Servico Internal:</label> 
						<input list="listaPathsInternals" class="form-control" data-ng-model="pathServicioInternal" data-ng-keypress="recuperaRequestInternal($event, pathServicioInternal)" placeholder="/internal/***" required autofocus>
						 <datalist id="listaPathsInternals">
		                	<option data-ng-repeat="pathInternals in contratosInterfazInternals" value={{pathInternals.path}} />
		               	 </datalist>
					</div>
					<div class="col-xs-4">
<!-- 						<label for="folio">Cerrar Sesion o Cambiar IP:</label> -->
<!-- 						<button type="button" class="btn btn-danger btn-lg btn-block" -->
<!-- 							data-ng-click="cambiarIP()">Click Aqui</button> -->
					</div>
				</div>
				<br/><br/><br/><br/><br/>
				<div class="col-xs-12">
					<div class="form-group">
						<label for="request">Request:</label>
						<textarea class="form-control" rows="8" data-ng-model="requestInternal"></textarea>
					</div>
				</div>

				<div class="col-xs-12">
					<div class="form-group">
						<label for="response">Response:</label>
						<textarea class="form-control" rows="8" id="responseFrontInternal" data-ng-model="responseFrontInternal" disabled></textarea>
					</div>
				</div>
				<button type="button" class="btn btn-success btn-lg btn-block" data-ng-click="recojeDatosInternal()">Enviar Peticion Internal</button>
			</div>

			<!-- Tab de Cliente Micro -->
			<div id="micro" class="tab-pane fade">
				<br/>
				<div class="form-group">
						<div class="col-xs-3">
							<label for="ipJVC">Ip Micro:</label> <input type="text"
								class="form-control" data-ng-model="ip">
						</div>
						<div class="col-xs-2">
							<label for="puertoJVC">Puerto Micro:</label> <input
								type="number" class="form-control" id="puertoMicro"
								data-ng-model="puerto">
						</div>
						<div class="col-xs-4">
						<label for="path">Path de Microservicio:</label>
						<input list="listaPathsMicro" class="form-control" data-ng-model="pathServicioMicro" data-ng-keypress="recuperaRequestMicro($event, pathServicioMicro)" placeholder="/retiros/***" required autofocus>
						 <datalist id="listaPathsMicro">
		                	<option data-ng-repeat="pathMicros in contratosInterfazMicro" value={{pathMicros.path}} />
		               	 </datalist>
					</div>
						<br />
					</div>
					<br/>
				<div class="form-group">
				<br/>
				<div class="col-xs-4">
						<label for="icu">ICU Cliente:</label> <input
							type="text" class="form-control" id="icuCliente"
							data-ng-model="icuCliente">
					</div>
					<div class="col-xs-4">
						<label for="folioOperacion">Folio de Operacion:</label> 
						<input type="text" class="form-control"	data-ng-model="folioOperacionMicro" disabled>
					</div>
					</div>
					<br/><br/><br/><br/><br/>
					<div class="form-group">
						<div class="col-xs-12">
					<div class="form-group">
						<label for="request">Request:</label>
						<textarea class="form-control" rows="8" data-ng-model="requestMicro"></textarea>
					</div>
				</div>

				<div class="col-xs-12">
					<div class="form-group">
						<label for="response">Response:</label>
						<textarea class="form-control" rows="8" id="responseFrontMicro"
							data-ng-model="responseFrontMicro" disabled></textarea>
					</div>
				</div>
				<button type="button" class="btn btn-success btn-lg btn-block" data-ng-click="recojeDatosMicroservicio()" >Enviar Peticion Microservicios</button>
					</div>
					
			</div>
			
			
			<!-- Tab de Encripcion y Desencripcion -->
			<div id="encriptar" class="tab-pane fade">
				<br />
				<div class="form-group">
					<div class="col-xs-8">
						<label for="cadenaACifrar">Cadena a Encriptar:</label> <input type="text"
							class="form-control" data-ng-model="cadenaACifrar">
					</div>
					<div class="col-xs-4">
						<label for="">........................</label>
						<button type="button" class="btn btn-success btn-lg btn-block"
							data-ng-click="encriptarDatos()">Encriptar Cadena</button>
					</div>
					<br />
				</div>
				<div class="form-group">
					<div class="col-xs-8">
						<label for="cadenaADesencriptar">Cadena a Desencriptar:</label> <input type="text"
							class="form-control" data-ng-model="cadenaADesencriptar">
					</div>
					<div class="col-xs-4">
						<label for="">........................</label>
						<button type="button" class="btn btn-success btn-lg btn-block"
							data-ng-click="desencriptarDatos()">Desencriptar Cadena</button>
					</div>
					<br />
				</div>
				<br />
				<div class="col-xs-12">
					<br />
					<div class="form-group">
						<label for="response">Resultado:</label>
						<textarea class="form-control" rows="6" id="cadenaProcesada"
							data-ng-model="cadenaProcesada" disabled></textarea>
					</div>
				</div>
			</div>
		</div>
		<br/><br/><br/><br/>
		<p> © 2018 version: <strong>6.5.0 Wallas</strong></p>
</body>

</html>