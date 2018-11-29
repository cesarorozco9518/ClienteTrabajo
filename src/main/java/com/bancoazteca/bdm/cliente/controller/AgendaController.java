/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bancoazteca.bdm.cliente.dao.EventElementsDao;
import com.bancoazteca.bdm.cliente.entity.CatEmpleados;
import com.bancoazteca.bdm.cliente.entity.EventObject;
import com.bancoazteca.bdm.cliente.entity.ListEventObject;

/**
 *
 * @author acruzb
 */
@Controller
@RequestMapping("/agenda")
public class AgendaController {
        
    @Autowired
    private EventElementsDao dao;

    public AgendaController() {
    }
    
    @RequestMapping(value = {"/inicio", ""})
    public String agenda(Map<String, Object> model) {
        return "agenda";
    }
    
    @RequestMapping(value = "/data")
    @ResponseBody
    public ListEventObject data(@RequestParam String fecha) {
        ListEventObject lista = new ListEventObject();
        lista.setEvents(dao.getMonthlyEvents(getRegex(fecha)));
        return lista;
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/updateEvent", consumes = "application/json", produces = "application/json")
    public String update(@RequestBody EventObject event) {
        System.out.println(event);
        dao.updateStartRepo(event);
        return "{\"ok\":\"ok\"}";
    }
    
    @RequestMapping(value = "/getMembers", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<CatEmpleados> add() {
        return dao.getEmpleados();
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/addEvent", consumes = "application/json")
    @ResponseBody
    public String add(@RequestBody EventObject event) {
        System.out.println(event);
        return "{\"id\":\"" + dao.addEvent(event) +"\"}";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/deleteEvent", consumes = "application/json")
    @ResponseBody
    public String delete(@RequestBody EventObject event) {
        System.out.println(event);
        return "{\"id\":\"" + dao.deleteEvent(event) +"\"}";
    }
    
    private String getRegex(String fecha){
        String fec[] = fecha.split("-");
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        c.set(Integer.parseInt(fec[0]), Integer.parseInt(fec[1]), Integer.parseInt(fec[2]));
        c.add(Calendar.MONTH, -2);
        int monthFirst = c.get(Calendar.MONTH);
        int yearFirst = c.get(Calendar.YEAR);
        c.add(Calendar.MONTH, 1);
        int monthMiddle = c.get(Calendar.MONTH);
        c.add(Calendar.MONTH, 1);
        int monthLast = c.get(Calendar.MONTH);
        int yearLast = c.get(Calendar.YEAR);
        String regex = "";
        if(yearFirst == yearLast){
            regex += yearFirst;
        }
        else{
            regex += "("+ yearFirst + "|" + yearLast +")";
        }
        regex += "-";
        regex += "(" + getMonth(monthFirst) + "|"+ getMonth(monthMiddle) +"|" + getMonth(monthLast) + ")";
        regex += ".*";
        return regex;
    }
    
    private String getMonth(int month){
        return String.format("%02d", month + 1);
    }
    
}
