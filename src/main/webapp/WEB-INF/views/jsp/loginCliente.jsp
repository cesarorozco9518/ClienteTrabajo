<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page session="false"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cliente BazDigital</title>		 
		
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
		
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.css"/>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.css"/>		  	
		<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
		<script	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-beta.2/angular-route.min.js"></script>		
        <script	src="https://code.angularjs.org/1.4.8/angular-resource.min.js"></script> 
		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-block-ui/0.2.2/angular-block-ui.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-filter/0.5.8/angular-filter.min.js"></script>
		<script src="<c:url value='/resources/js/ClienteScript.js' />"></script>
		
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
            <div class="col-sm-4">            
                <div class="form-group">
                    <label for="ip">Ip Servidor:</label>
                    <input type="text" class="form-control" id="ip" data-ng-model='ip'>
                </div>                
            </div>
            <div class="col-sm-2">            
                <div class="form-group">
                    <label for="port">Puerto Servidor:</label>
                    <input type="number" class="form-control" id="ip" data-ng-model="puerto">
                </div>            
            </div>
            <div class="col-sm-5">
                <div class="form-group">
                    <label for="folio">Folio Del Servidor:</label>
                    <input type="text" class="form-control" id="ip" data-ng-model='folioOperacion' readonly="readonly">
                </div> 
            </div>
        </div>        
        <div class="alert alert-danger alert-dismissable" data-ng-show="alerta1">
          <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
          <center><strong>Lo sentimos, ha ocurrido un error al realizar la operacion.</strong> No hemos podido establecer comunicacion con el servidor.</center>
        </div>
        <div class="alert alert-warning alert-dismissable" data-ng-show="alerta2">
          <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
          <center><strong>Lo sentimos.</strong> El usuario se encuentra bloqueado.</center>
        </div>
        <div class="alert alert-warning alert-dismissable" data-ng-show="alerta3">
          <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
          <center><strong>Lo sentimos.</strong> El usuario y/o contraseña son incorrectas, recuerde que despues de tres intentos el usuario sera bloqueado.</center>
        </div>
        
        
        <center>
  		<div class="col-sm-6">
        <div class="card-body">
            <center><img id="profile-img" class="profile-img-card" src="http://cakrawalaproteksi.com/Content/images/avatar.jpg" style="width:50%"/></center>
            <p id="profile-name" class="profile-name-card"></p>
            <form class="form-signin">
                <span id="reauth-email" class="reauth-email"></span>
                <input type="text" id="user" class="form-control" data-ng-model='usuario' required autofocus>
                <br/>
                <input type="password" id="inputPassword" class="form-control" data-ng-model='pass' required>
                <br/>
                <button class="btn btn-lg btn btn-outline-success btn-block btn-signin" type="submit" data-ng-click="datosLogin()">Iniciar Sesion</button>
                <button class="btn btn-lg btn btn-outline-dark btn-block btn-signin" type="submit" data-ng-click="cambiaIpHome()">Inicio</button>
            </form><!-- /form -->
        </div><!-- /card-container -->
    </div><!-- /container -->
    </center>
    </div>
</body>
</html>