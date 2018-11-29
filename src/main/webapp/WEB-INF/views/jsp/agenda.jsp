<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
    <head>
        <meta charset="utf-8" /> 
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link href='<c:url value='/resources/css/fullcalendar.min.css'/>' rel='stylesheet' />
        <link href='<c:url value='/resources/css/fullcalendar.print.min.css'/>' rel='stylesheet' media='print' />
        <link href='<c:url value='/resources/css/bootstrap-combobox.css'/>' rel='stylesheet' media='print' />
        <link href='<c:url value='/resources/css/gijgo.min.css'/>' rel='stylesheet'/>
        <link href='<c:url value='/resources/css/agenda.css'/>' rel='stylesheet' />
    </head>
    <body>
        <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
        <script>
            var loginValid = "<c:url value='/'/>";
        </script>
        <c:choose>
            <c:when test="${isAdmin}">
                <b>${user}</b>
                <button id="btn-logout-agenda" type="button" class="btn btn-primary">logout</button>
            </c:when>
            <c:otherwise>
                <button id="btn-login-agenda" type="button" class="btn btn-primary" style="display:none">login</button>
            </c:otherwise>
        </c:choose>
        <div id='calendar'></div>
        <!-- Modal -->
        <div class="modal fade" id="modaAddEvent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Registrar guardia</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group row">
                            <label for="example-text-input" class="col-2 col-form-label">Nombre</label>
                            <div class="col-10">
                                <select class="combobox input-large form-control" name="normal">
                                    <option value="" selected="selected">Selecionar empleado</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="example-text-input" class="col-2 col-form-label">Fecha</label>
                            <div class="col-10">
                                <input class="form-control" type="text" id="start" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="example-text-input" class="col-2 col-form-label">Reposición</label>
                            <div class="col-10">
                                <input class="form-control" id="reposicion" readonly=""/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="guardar-guardia">Guardar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Cargando...</p>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <script>
        var contextName = "<c:url value='/'/>";
    </script>
    <script src='<c:url value='/resources/js/lib/moment.min.js'/>'></script>
    <script src='<c:url value='/resources/js/lib/jquery.min.js'/>'></script>
    <script src='<c:url value='/resources/js/lib/bootstrap-combobox.js'/>'></script>
    <script src='<c:url value='/resources/js/fullcalendar.min.js'/>'></script>
    <script src='<c:url value='/resources/js/locale/es-do.js'/>' charset="UTF-8"></script>
    <script src='<c:url value='/resources/js/gijgo.min.js'/>'></script>
    <script src="https://npmcdn.com/tether@1.2.4/dist/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script src='<c:url value='/resources/js/agenda.js'/>' charset="UTF-8"></script>

</html>
