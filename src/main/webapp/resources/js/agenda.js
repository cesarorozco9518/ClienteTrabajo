/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var classNameReposicion = "reposition-day";
var reproPrefix = "repo_";

$(document).ready(function () {
    var initialLocaleCode = 'es-do';
    
    var loginNow = function(){
        return $("#btn-logout-agenda").length === 1;
    };
    
    var showLoginForm = function(){
        $('#modalLogin').modal('show').find('.modal-body').load(contextName + "login");
    };
    
    $("#btn-login-agenda").click(function () {
        showLoginForm();
    });

    $("#btn-logout-agenda").click(function () {
        $.ajax({
            method: "POST",
            url: contextName + "logout"
        }).done(function (members) {
            location.reload();
        });
    });

    $.ajax({
        method: "POST",
        url: contextName + "agenda/getMembers",
        contentType: 'application/json'
    }).done(function (members) {
        $.each(members, function (i, item) {
            $('.combobox')
                    .append($("<option></option>")
                            .attr("value", item.id)
                            .text(item.nombre + ' ' + item.apellido));
        });
        $('.combobox').combobox({
            bsVersion: '4',
            menu: '<ul class="typeahead typeahead-long dropdown-menu"></ul>',
            item: '<li><a href="#" class="dropdown-item"></a></li>'
        });
    });

    $('#reposicion').datepicker({
//                    locale: 'es-es',
        uiLibrary: 'bootstrap4',
//                    icons: {
//                        rightIcon: '<span class="glyphicon glyphicon-calendar"></span>'
//                    },
        format: 'yyyy-mm-dd'
    });

    var obtenerClassName = function (date) {
        var dateMom = moment(date);
        var now = moment();
        if (dateMom.diff(now, 'days') < 0) {
            return 'past-days';
        }

        var dia = moment(date).isoWeekday();
        if (dia === 5) {
            return 'fridays';
        } else if (dia === 6 || dia === 7) {
            return 'weekends';
        }
        return '';
    };

    var isEditable = function (date) {
        var dateMom = moment(date);
        var now = moment();
        return (dateMom.diff(now, 'days') >= 0);
    };

    var eliminarEvento = function (event) {
        $.ajax({
            method: "POST",
            url: contextName + "agenda/deleteEvent",
            data: JSON.stringify({
                'id': event.id
            }),
            contentType: 'application/json',
            success: function (msg) {
                if (validarSesionActiva(msg)) {
                    $('#calendar').fullCalendar('removeEvents', event.id);
                    $('#calendar').fullCalendar('removeEvents', reproPrefix + event.id);
                    console.log("Se elimina el registro: " + msg);
                }
            },
            error: function () {
                alert('there was an error while fetching events!');
            }
        });
    };

    var validarSesionActiva = function (resp) {
        console.log('respuesta:  ' + resp);
        if (resp.includes("<html>")) {
            console.log("Se requiere el login previamente.");
            $('#modalLogin').modal('show').find('.modal-body').load(contextName + "login");
            return false;
        } else {
            return true;
        }
    };

    var agregarEventoLocal = function (id, title, start, reposicion) {
        var events = new Array();
        var event = {
            id: id,
            title: title,
            allDay: true,
            description: 'description for All Day Event',
            editable: isEditable(start),
            start: start,
            className: obtenerClassName(start)
        };
        events.push(event);
        if (reposicion !== undefined && reposicion !== '' && reposicion !== null) {
            event = {
                id: reproPrefix + id,
                title: '<span>' + title + '</span><br><span style="font-style: italic;margin-right: 5px;">' + start + '</span>',
                allDay: true,
                editable: false,
                start: reposicion,
                className: classNameReposicion
            };
            events.push(event);
        }
        $('#calendar').fullCalendar('renderEvents', events, true);
    };

    var cambiarEvento = function (event, newDate, reposicion, newStart) {
        $.ajax({
            method: "POST",
            url: contextName + "agenda/updateEvent",
            data: JSON.stringify({
                'id': event.id,
                'className': obtenerClassName(newDate),
                'start': newDate,
                'reposicion': reposicion
            }),
            contentType: 'application/json'
        }).done(function (msg) {
            if (validarSesionActiva(msg)) {
                if (newStart) {//evento principal
                    event.start = newDate;
                    event.className = [obtenerClassName(newDate)];
                    var idUpdate = new Array();
                    idUpdate.push(event);
                    var eventUpdate = $('#calendar').fullCalendar('clientEvents', reproPrefix + event.id);
                    if (eventUpdate.length > 0) {
                        eventUpdate[0].title = '<span>' + event.title + '</span><br><style="font-style: italic;margin-right: 5px;">' + newDate + '</span>';
                        idUpdate.push(eventUpdate[0]);
                    }
                    $('#calendar').fullCalendar('updateEvents', idUpdate);
                }
                console.log("Se actualiza el dia de la guardia: " + msg);
            }
        });
    };

    var agregarEvento = function (title, start, reposicion) {
        $.ajax({
            method: "POST",
            url: contextName + "agenda/addEvent",
            data: JSON.stringify({
                'title': title,
                'start': start,
                'className': obtenerClassName(start),
                'reposicion': reposicion
            }),
            contentType: 'application/json'
        }).done(function (response) {
            if (validarSesionActiva(response)) {
                console.log("Se agrega la guardia: " + response);
                agregarEventoLocal(JSON.parse(response).id, title, start, reposicion);
                $('#modaAddEvent').modal('hide');
            }
        });
    };

    $('#modaAddEvent').modal({
        show: false
    });

    $('#modaAddEvent').on('hidden.bs.modal', function () {
        $(".combobox").prop('selectedIndex', 0);
        $(".combobox-container input").val('');
        $("#reposicion").val('');
    });

    $("#guardar-guardia").click(function () {
        var start = $("#start").val();
        var title = $(".combobox option:selected").text();
        if (title !== "" && $(".combobox option:selected").index() !== 0) {
            agregarEvento(title, start, $("#reposicion").val());
        } else {
            alert("Favor de agregar el nombre del empleado.");
        }
    });

    $('#calendar').fullCalendar({
        themeSystem: 'bootstrap4',
        locale: initialLocaleCode,
        titleFormat: 'MMMM YYYY',
        defaultView: 'month',
        selectable: true,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month'
        },
        navLinks: false, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        eventClick: function (calEvent, jsEvent, view) {
//                        console.log('Event: ' + calEvent.title);
        },
        dayClick: function (date, jsEvent, view) {
            $('.popover').popover('hide');
            prevTime = typeof currentTime === 'undefined' || currentTime === null
                    ? new Date().getTime() - 1000000
                    : currentTime;
            currentTime = new Date().getTime();
            if (currentTime - prevTime < 500) {
                $("#start").val(date.format());
                if(loginNow()){
                    $('#modaAddEvent').modal('show');
                }
                else{
                    showLoginForm();
                }
            }
        },
        eventRender: function (event, element) {
            element.bind('click', function () {
                $('.popover').popover('hide');
                if (event.className.indexOf(classNameReposicion) > -1) {
                    return;
                }
                if (event.className.indexOf('past-days') > -1) {
                    return;
                }
                var eventUpdate = $('#calendar').fullCalendar('clientEvents', reproPrefix + event.id);
                if (!$(element).hasClass("eventPopover")) {
                    //Se cierran los popups abiertos
                    var eventUpdate = $('#calendar').fullCalendar('clientEvents', reproPrefix + event.id);
                    $(element).popover({
                        placement: 'top',
                        container: 'body',
                        title: event.title,
                        trigger: 'click',
                        html: true,
                        content: function () {
                            return  '<div>' +
                                    '    <form role="form"' +
                                    '        <div class="form-group">' +
                                    '            <div class="row">' +
                                    '                <div class="col-md-12" style="margin-bottom: 15px;margin-top: 10px;"><b>Reposición</b></div>' +
                                    '                <div class="col-md-12">' +
                                    '                    <input id="newReposicion" class="form-control" value="' + ((eventUpdate.length > 0) ? eventUpdate[0].start.format() : '') + '" readonly=""/>' +
                                    '                </div>' +
                                    '            </div>' +
                                    '            <br>' +
                                    '        </div>' +
                                    '        <div class="form-group">' +
                                    '            <div class="row">' +
                                    '                <div class="col-md-5">' +
                                    '                    <input id="btn-actualiza-reposicion" class="btn btn-sm btn-primary form-control" type="button" value="Actualizar"/>' +
                                    '                </div>' +
                                    '                <div class="col-md-5 offset-md-2">' +
                                    '                    <input id="btn-elimina-guardia" class="btn btn-sm btn-danger form-control" type="button" value="Eliminar" />' +
                                    '                </div>' +
                                    '            </div>' +
                                    '        </div>' +
                                    '    </form>' +
                                    '</div>';
                        }
                    }).on('inserted.bs.popover', function () {
                        $('#newReposicion').datepicker({
                            uiLibrary: 'bootstrap4',
                            format: 'yyyy-mm-dd'
                        });
                        $("#btn-actualiza-reposicion").click(function () {
                            var eventUpdate = $('#calendar').fullCalendar('clientEvents', reproPrefix + event.id);
                            if (eventUpdate.length > 0) {
                                eventUpdate[0].start = $('#newReposicion').val();
                                $('#calendar').fullCalendar('updateEvent', eventUpdate[0]);
                            } else {
                                //se agrega nuevo evento
                                var myNewEvent = {
                                    id: reproPrefix + event.id,
                                    title: '<span>' + event.title + '</span><br><style="font-style: italic;margin-right: 5px;">' + event.start.format() + '</span>',
                                    allDay: true,
                                    editable: false,
                                    start: $('#newReposicion').val(),
                                    className: classNameReposicion
                                };
                                $('#calendar').fullCalendar('renderEvent', myNewEvent, true);
                            }
                            cambiarEvento(event, event.start.format(), $('#newReposicion').val(), false);
                            $('.popover').popover('hide');
                        });
                        $("#btn-elimina-guardia").click(function () {
                            eliminarEvento(event);
                            $('.popover').popover('hide');
                        });
                    }).on('hidden.bs.popover', function (elem) {
                        $('#newReposicion').datepicker('destroy');
                    });
                    $(element).popover('show');
                    $(element).addClass("eventPopover");
                }
            });
            //Se agrega popup
            //render de las reposiciones de guardia
            if (event.id.startsWith(reproPrefix)) {
                element.find('.fc-title, .fc-list-item-title').html(event.title);
            }
        },
        eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
            if(loginNow()){
                if (!confirm("¿Estás seguro de cambiar de fecha?")) {
                    revertFunc();
                } else {
                    var eventUpdate = $('#calendar').fullCalendar('clientEvents', reproPrefix + event.id);
                    cambiarEvento(event, event.start.format(), (eventUpdate.length > 0) ? eventUpdate[0].start.format() : '', true);
                }
            }
            else{
                revertFunc();
                showLoginForm();
            }
        },
        viewRender: function (view, element) {
            var b = $('#calendar').fullCalendar('getDate');
            $('#calendar').fullCalendar('removeEvents');
            $.ajax({
                method: "POST",
                url: contextName + 'agenda/data',
                data: {
                    'fecha': b.format("YYYY-MM-DD")
                },
                success: function (doc) {
                    $(doc.events).each(function () {
                        agregarEventoLocal($(this).attr('id'),
                                $(this).attr('title'),
                                $(this).attr('start'),
                                $(this).attr('reposicion'));
                    });
                },
                error: function () {
                    alert('there was an error while fetching events!');
                }
            });
        }
    });


});