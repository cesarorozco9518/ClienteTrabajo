<%-- 
    Document   : login
    Created on : 15/05/2018, 10:02:49 AM
    Author     : acruzb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
    <head>
        <title>Guardias BAZ</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-md5/2.10.0/js/md5.js"></script>
        <style>
            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .checkbox {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
        </style>
        <script>
            function mySubmit() {
                document.getElementById("password").value = md5(document.getElementById("password").value);
            }
        </script>
    </head>
    <body class="text-center">
        <form class="form-signin" onsubmit="mySubmit()" action="<c:url value='/login'/>" method="post">
            <div class="text-center"><img class="mb-4" src="https://myslu.slu.edu/res/images/cas-padlock-icon.png" alt="" width="72" height="72"></div>
            <h1 class="h3 mb-3 font-weight-normal">Por favor iniciar sesión</h1>
            <c:if test="${param.error ne null}">
                <div style="color: red">Credenciales inválidas.</div>
            </c:if>
            <label for="username" class="sr-only">Usuario</label>
            <input name="username" type="input" id="username" class="form-control" placeholder="Nombre usuario" required="" autofocus="">
            <label for="inputPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="password" class="form-control" placeholder="Contraseña" required="">
            <button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-top: 30px;">Iniciar</button>
        </form>
    </body>
</html>
