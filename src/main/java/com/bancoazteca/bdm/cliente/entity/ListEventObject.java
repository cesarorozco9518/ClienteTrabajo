/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author acruzb
 */
public class ListEventObject {
    
    private List<EventObject> events;

    public ListEventObject() {
        events = new ArrayList<>();
    }

    public List<EventObject> getEvents() {
        return events;
    }

    public void setEvents(List<EventObject> events) {
        this.events = events;
    }
    
    public void addEventObject(EventObject event){
        this.events.add(event);
    }
    
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException jpe) {}
        return "";
    }
}
