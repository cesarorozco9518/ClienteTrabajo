/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.entity;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author acruzb
 */
@Document(collection = "BAZEventObject")
public class EventObject {
    
    @Id
    private String id;
    private String title;
    private boolean allDay;
    private String start;
    private String end;
    private String className;
    private String reposicion;
    private boolean editable;
    
    public EventObject() {
        allDay = true;
        editable = true;
    }

    public EventObject(String title, String start, String end, String className) {
        this();
        this.title = title;
        this.start = start;
        this.end = end;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getReposicion() {
        return reposicion;
    }

    public void setReposicion(String reposicion) {
        this.reposicion = reposicion;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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
