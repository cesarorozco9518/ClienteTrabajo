/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bancoazteca.bdm.cliente.entity.CatEmpleados;
import com.bancoazteca.bdm.cliente.entity.EventObject;

/**
 *
 * @author acruzb
 */
@Repository
public class EventElementsDao {
    
    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    public EventElementsDao() {
        
    }
    
    public List<EventObject> getMonthlyEvents(String regex){
        Query query = new Query();
        query.addCriteria(
            Criteria.where("start").regex(regex)
        );
        return mongoTemplate.find(query, EventObject.class);
    }
    
    public String addEvent(EventObject event){
        mongoTemplate.insert(event);
        return event.getId();
    }
    
    public void updateStartRepo(EventObject event){
        Query query = new Query();
        query.addCriteria(
            Criteria.where("_id").is(new ObjectId(event.getId()))
        );
        EventObject obj = mongoTemplate.findOne(query, EventObject.class);
        obj.setStart(event.getStart());
        obj.setReposicion(event.getReposicion());
        obj.setClassName(event.getClassName());
        mongoTemplate.save(obj);
    }
    
    public String deleteEvent(EventObject event){
        mongoTemplate.remove(event);
        return event.getId();
    }
    
    public List<CatEmpleados> getEmpleados(){
        return mongoTemplate.findAll(CatEmpleados.class);
    }
    
}
