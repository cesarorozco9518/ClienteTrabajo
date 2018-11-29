<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page session="false"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ClienteBazDigital Inicio</title>		
	        
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
	        
		<script src="<c:url value='/resources/js/ClienteScript.js' />"></script>
		<script src="<c:url value='/resources/js/copiarResponse.js' />"></script>
	</head>
    <body data-ng-app="cliente" data-ng-controller="myCtrl">   
         
        <!-- Inicia Alerta de titulo -->
       <div class="card bg-success text-white">
            <div class="card-body"><strong><h1><center>Cliente BAZDigital (Area Servicios)</center></h1></strong></div>
       </div>
        <!-- Termina Alerta de titulo -->
        <br/><br/>
        <div class="container">            
            <div class="row">
                <div class="col-sm-6">
                    <div class="card bg-light text-dark" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion con Login</h4>
                          <p class="card-text">Hace login y mantiene sesion a cada peticion que realizes.</p>
                          <center><button type="button" class="btn btn-outline-dark" data-ng-click="cambiarIP()">Iniciar Sesion</button></center>
                        </div>
                    </div>                
                </div>                
                <div class="col-sm-6">
                    <div class="card" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion con login Instantanea</h4>
                          <p class="card-text">Cada peticion que realizes, se generara un login nuevo.</p>
                          <center><button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalInstantaneo">Realizar Peticion</button></center>
                        </div>
                    </div>                
                </div>          
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-6">
                    <div class="card" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion a Servicios Internals</h4>
                          <p class="card-text">Realiza Peticiones a Servicios Internals.</p>
                          <center><button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalInternals">Realizar Peticion</button></center>
                        </div>
                  </div>
                </div>
                <div class="col-sm-6">
                    <div class="card bg-light text-dark" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion a Servicios Externals</h4>
                          <p class="card-text">Realiza Peticiones a Servicios Externals.</p>
                          <center><button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalExternals">Realizar Peticion</button></center>
                        </div>
                  </div>
                </div>                
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-6">
                    <div class="card bg-light text-dark" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion a Microservicios</h4>
                          <p class="card-text">Realiza peticiones directo al jar de Microservicios.</p>
                          <center><button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalMicro">Realizar Peticion</button></center>
                        </div>
                  </div>
                </div>
                <div class="col-sm-6">
                    <div class="card" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Peticion Asyncrona</h4>
                          <p class="card-text">Realiza peticion a los servicios que no validan "Sesion" ni "OTP".</p>
                          <center><button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalNotSession">Realizar Peticion</button></center>
                        </div>
                  </div>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-6">
                    <div class="card" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">Cliente Wallet</h4>
                          <p class="card-text">Realiza peticiones del sistema Wallet.</p>
                          <center><button type="button" class="btn btn-outline-info" data-ng-click="cambioWallet()">Ingresar al cliente para Wallet</button></center>
                        </div>
                  </div>
                </div>
                <div class="col-sm-6">
                    <div class="card bg-light text-dark" style="width:600px">
                        <div class="card-body">
                          <h4 class="card-title">¿ Que dia te toca guardia...?</h4>
                          <p class="card-text">Aqui podras consultar que dia te toca guardia.</p>
                          <center><button type="button" class="btn btn-outline-info" data-ng-click="cambioAgenda()">Ir a consultar el calendario</button></center>
                        </div>
                  </div>
                </div>
            </div>
        
        <!-- --------------------------------------------------- Inicia Modal de Internals ------------------------------------------------------- -->
        <div class="modal fade" id="modalInternals">
            <div class="modal-dialog modal-lg">
            <div class="modal-content">
                
                <div class="modal-header">
                    <strong><center><h3 class="modal-title">Peticion a Servicios Internals</h3></center></strong>                    
                    <button type="button" class="close" data-dismiss="modal" data-ng-click="limpiaCamposModalInternal()">&times;</button>
                </div>
                
                <div class="modal-body">
                    
                    <div class="form-group row">
                        <div class="col-4">
                          <label for="request">Ip JVC:</label>
                          <input class="form-control" type="text" data-ng-model="ipJVC">
                        </div>
                        <div class="col-2">
                          <label for="request">Puerto JVC:</label>
                          <input class="form-control" type="number" data-ng-model="puertoJVC">
                        </div>
                        <div class="col-6">
                          <label for="pathInternal">Path Servico Internal:</label> 
                            <input list="listaPathsInternals" class="form-control" data-ng-model="pathServicioInternal" data-ng-keypress="recuperaRequestInternal($event, pathServicioInternal)" placeholder="/internal/***" required autofocus>
                            <datalist id="listaPathsInternals">
                                <option data-ng-repeat="pathInternals in contratosInterfazInternals" value={{pathInternals.path}} />
                            </datalist>
                        </div>
                    </div>
                    
                    <div class="form-group row">
                        <div class="col-6">
                          <label for="request">Folio de operación:</label>
                          <input class="form-control" type="text" data-ng-model="folioOperacionInternal" readonly="readonly">
                        </div>
                    </div>
                    
                    <div class="alert alert-danger alert-dismissable" data-ng-show="errorServer">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <center><strong>Lo sentimos!</strong> Error al realizar la operacion, porfavor revise su servidor de JVC.</center>
                    </div>
                    
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="request">Request:</label>
						  <textarea class="form-control" rows="6" data-ng-model="requestInternal"></textarea>
					   </div>
				    </div>
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="response">Response:</label>
						  <textarea class="form-control" rows="6" id="responseInternal" data-ng-model="responseFrontInternal" readonly="readonly"></textarea>
					   </div>
				    </div>
				    <button type="button" class="btn btn-outline-success btn-lg btn-block" data-ng-click="recojeDatosInternal()">Realizar Peticion</button>
				    <button type="button" class="btn btn-outline-dark btn-lg btn-block" onclick="copiaResponseInternal()">Copiar ResponseTO</button>                    
                </div>
            </div>
            </div>
        </div>
        <!-- --------------------------------------------------- Termina Modal de Internlas ----------------------------------------------------- -->        
        
        <!-- --------------------------------------------------- Inicia Modal de Externals ------------------------------------------------------- -->
        <div class="modal fade" id="modalExternals">
            <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <strong><center><h3 class="modal-title">Peticion a Servicios Externals</h3></center></strong>                    
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    
                     <div class="form-group row">
                        <div class="col-4">
                            <label for="request">Ip Servidor:</label>
                            <input class="form-control" type="text" data-ng-model="ipExternals">
                        </div>
                        <div class="col-2">
                            <label for="request">Puerto:</label>
                            <input class="form-control" type="number" data-ng-model="puertoExternals">
                        </div>
                        <div class="col-6">
                            <label for="pathInternal">Path Servicio External:</label> 
                            <input class="form-control" data-ng-model="pathServicioExternals" placeholder="/external/***">
                        </div>
                    </div>
                    
                
				    <div class="form-group row">
                        <div class="col-4">
                            <label for="request">Usuario:</label>
                            <input class="form-control" type="text" data-ng-model="userExternals">
                        </div>
                        <div class="col-4">
                            <label for="request">Password:</label>
                            <input class="form-control" type="text" data-ng-model="passExternals">
                        </div>
                        <div class="col-4">
                            <label for="pathInternal">Folio de Operacion:</label> 
                            <input class="form-control" type="text" data-ng-model="folioExternals" readonly="readonly">
                        </div>
                    </div>
                    

                    <div class="alert alert-danger alert-dismissable" data-ng-show="errorServerExternals">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <center><strong>Lo sentimos!</strong> Error al realizar la operacion, porfavor revise su servidor de JVC.</center>
                    </div>
                    
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="request">Request:</label>
						  <textarea class="form-control" rows="6" data-ng-model="requestExternals"></textarea>
					   </div>
				    </div>
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="response">Response:</label>
						  <textarea class="form-control" rows="6" id="responseExternals" data-ng-model="responseFrontExternals" readonly="readonly"></textarea>
					   </div>
				    </div>
				    <button type="button" class="btn btn-outline-success btn-lg btn-block" id="responseCopied" data-ng-click="recojeDatosExternal()">Realizar Peticion</button>
				    <button type="button" class="btn btn-outline-dark btn-lg btn-block" onclick="copiaResponseExternal()">Copiar ResponseTO</button>                    
                </div>
            </div>
            </div>
        </div>
        <!-- --------------------------------------------------- Termina Modal de Externals ----------------------------------------------------- -->
        
        <!-- --------------------------------------------------- Inicia Modal de Micro ---------------------------------------------------------- -->
        <div class="modal fade" id="modalMicro">
            <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <strong><center><h3 class="modal-title">Peticion a los Microservicios</h3></center></strong>                    
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    
                    <div class="form-group row">
                        <div class="col-4">
                          <label for="request">Ip Micro:</label>
                          <input class="form-control" type="text" data-ng-model="ipMicro">
                        </div>
                        <div class="col-2">
                          <label for="request">Puerto Micro:</label>
                          <input class="form-control" type="number" data-ng-model="puertoMicro">
                        </div>
                        <div class="col-6">
                          <label for="pathInternal">Path Microservicio:</label> 
                          <input list="listaPathsMicro" class="form-control" data-ng-model="pathServicioMicro" data-ng-keypress="recuperaRequestMicro($event, pathServicioMicro)" placeholder="/retiros/***" required autofocus>
                            <datalist id="listaPathsMicro">
                                <option data-ng-repeat="pathMicros in contratosInterfazMicro" value={{pathMicros.path}} />
                            </datalist>
                        </div>
                    </div>
                    

				    <div class="form-group row">
                        <div class="col-5">
                          <label for="request">Icu del Cliente:</label>
                          <input class="form-control" type="text" data-ng-model="icuCliente">
                        </div>
                        <div class="col-5">
                          <label for="request">Folio de Operacion:</label>
                          <input class="form-control" type="text" data-ng-model="folioOperacionMicro" readonly="readonly">
                        </div>
                        <div class="form-check form-check-inline">
                          <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" data-ng-model="peticionBilletera">Billetera
                          </label>
                        </div>
                    </div>
                    

					<div class="alert alert-danger alert-dismissable" data-ng-show="errorMicro">
                      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                      <center><strong>Lo sentimos!</strong> Error al realizar la operacion, porfavor verifique su servidor.</center>
                    </div>
                    
					<div class="form-group">
						<div class="col-xs-12">
					       <div class="form-group">
						      <label for="request">Request:</label>
						      <textarea class="form-control" rows="6" data-ng-model="requestMicro"></textarea>
					       </div>
				        </div>
				        <div class="col-xs-12">
					       <div class="form-group">
						      <label for="response">Response:</label>
                              <textarea class="form-control" rows="6" id="responseMicro" data-ng-model="responseFrontMicro" readonly="readonly"></textarea>
                           </div>
				        </div>
				    <button type="button" class="btn btn-outline-success btn-lg btn-block" data-ng-click="recojeDatosMicroservicio()" > Realizar Peticion</button>
				    <button type="button" class="btn btn-outline-dark btn-lg btn-block" onclick="copiaResponseMicro()">Copiar ResponseTO</button>
					</div>                    
                </div>
            </div>
            </div>
        </div>
        <!-- ----------------------------------------------------- Termina Modal de Micro ----------------------------------------------------- -->
        
        
        
        <!-- ----------------------------------------------------- Inicia Modal de Asyncrono -------------------------------------------------- -->
        <div class="modal fade" id="modalNotSession">
            <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <strong><center><h3 class="modal-title">Peticion a Servicios sin Sesión</h3></center></strong>                    
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="alert alert-danger">
                  <center><strong>Atencion!</strong> Esta peticion es para la anotacion <b>@InterceptorNotSessionNotOTPBussiness</b>.</center>
                </div>
                <div class="modal-body">
                    
                     <div class="form-group row">
                        <div class="col-4">
                          <label for="request">Ip Core:</label>
                          <input class="form-control" type="text" data-ng-model="ipCoreA">
                        </div>
                        <div class="col-2">
                          <label for="request">Puerto Core:</label>
                          <input class="form-control" type="number" data-ng-model="puertoCoreA">
                        </div>
                        <div class="col-6">
                          <label for="pathInternal">Path de Servicio:</label> 
                          <input list="listaPathsCoreA" class="form-control" data-ng-model="pathServicioCoreA" data-ng-keypress="recuperaRequestCoreA($event, pathServicioCoreA)" placeholder="/ejemplo/***" required autofocus>
                            <datalist id="listaPathsCoreA">
                              <option data-ng-repeat="pathCoreA in contratosInterfazCore" value={{pathCoreA.path}}/>
                            </datalist>
                        </div>
                    </div>
                    
                    <div class="alert alert-danger alert-dismissable" data-ng-show="alertaAnotacion">
                      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                      <center><strong>Porfavor, verifique su anotacion en su metodo, si manda sesion expirada su anotacion no es @InterceptorNotSessionNotOTPBussiness.</strong></center>
                    </div>
                    <div class="alert alert-danger alert-dismissable" data-ng-show="error">
                      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                      <strong>Lo sentimos!</strong> Error al realizar la operacion, porfavor verifique su servidor.
                    </div>
                    <div class="alert alert-danger alert-dismissable" data-ng-show="error1">
                      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                      <strong>Upps!</strong> La anotacion de su metodo no es @InterceptorNotSessionNotOTPBussiness, porfavor vuelva a verificar su metodo.
                    </div>
                    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="request">Request:</label>
						  <textarea class="form-control" rows="6" data-ng-model="requestCoreA"></textarea>
					   </div>
				    </div>
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="response">Response:</label>
						  <textarea class="form-control" rows="6" id="responseAsync" data-ng-model="responseCoreA" readonly="readonly"></textarea>
					   </div>
				    </div>
                    <br/>
				    <button type="button" class="btn btn-outline-success btn-lg btn-block" data-ng-click="recojeDatosCoreA()">Realizar Peticion</button>
				    <button type="button" class="btn btn-outline-dark btn-lg btn-block" onclick="copiaResponseAsyncrona()">Copiar ResponseTO</button> 
                </div>
            </div>
            </div>
        </div>
        <!-- ---------------------------------------------------- Termina Modal de Asyncrono -------------------------------------------------- -->
        
        <!-- ----------------------------------------------------- Inicia Modal de peticion con login instantanea -------------------------------------------------- -->
        <div class="modal fade" id="modalInstantaneo">
            <div class="modal-dialog modal-lg">
            <div class="modal-content">
                
                <div class="modal-header">
                    <strong><center><h3 class="modal-title">Petición Instantanea a Core</h3></center></strong> 
                    <button type="button" class="close" data-dismiss="modal">&times;</button>                
                </div>
                
                <div class="modal-body">
                    
                    <div class="form-group row">
                        <div class="col-4">
                          <label for="request">Ip Core:</label>
                          <input class="form-control" type="text" data-ng-model="ipServerInst">
                        </div>
                        <div class="col-2">
                          <label for="request">Puerto Core:</label>
                          <input class="form-control" type="number" data-ng-model="portServerInst">
                        </div>
                        <div class="col-6">
                          <label for="request">Path de Servicio:</label>
                          <input class="form-control" type="text" list="listaPathsCore" data-ng-model="pathServicioInst" data-ng-keypress="recuperaRequestInst($event, pathServicio)">
                           <datalist id="listaPathsCore">
                             <option data-ng-repeat="pathCore in contratosInterfazCore" value={{pathCore.path}}/>
                          </datalist>
                        </div>
                    </div>
                    
                    
                    <div class="form-group row">
                        <div class="col-4">
                          <label for="request">Usuario:</label>
                          <input class="form-control" type="text" data-ng-model="usuarioInst">
                        </div>
                        <div class="col-4">
                          <label for="request">Password:</label>
                          <input class="form-control" type="password" data-ng-model="passInst">
                        </div>
                        <div class="col-4">
                          <label for="request">Folio:</label>
                          <input class="form-control" type="text" data-ng-model="folioInst" readonly="readonly">
                        </div>
                    </div>
                    
                    <div class="alert alert-danger alert-dismissable" data-ng-show="error">
                      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                      <strong>Lo sentimos!</strong> Error al realizar la operacion, porfavor verifique su servidor.
                    </div>
                    
                    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="request">Request:</label>
						  <textarea class="form-control" rows="6" data-ng-model="requestInst"></textarea>
					   </div>
				    </div>
                    
				    <div class="col-xs-12">
					   <div class="form-group">
						  <label for="response">Response:</label>
						  <textarea class="form-control" rows="6" id="responseInsta"  data-ng-model="responseInst" readonly="readonly"></textarea>
					   </div>
				    </div>
            
				    <button type="button" class="btn btn-outline-success btn-lg btn-block" data-ng-click="recojeDatosInstanataneo()">Realizar Peticion</button>
				    <button type="button" class="btn btn-outline-dark btn-lg btn-block" onclick="copiaResponseInsta()">Copiar ResponseTO</button>
                    
                </div>
            </div>
            </div>
        </div>
        <!-- ---------------------------------------------------- Termina Modal de peticion con login instantanea -------------------------------------------------- -->
        
        <script>
			
		</script>
        
    </body>
</html>