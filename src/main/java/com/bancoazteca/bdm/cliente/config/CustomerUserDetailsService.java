/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.bancoazteca.bdm.cliente.entity.Usuario;

/**
 * Created by gkatzioura on 9/27/16.
 */
@Repository
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = mongoTemplate.findOne(new Query(Criteria.where("noEmpleado").is(username)), Usuario.class);
        if (null == usuario) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new User(usuario.getNoEmpleado(), usuario.getClave(), true,
                true, true, true, getGrantedAuthorities(usuario));
    }

    private List<org.springframework.security.core.GrantedAuthority> getGrantedAuthorities(Usuario user) {
        List<org.springframework.security.core.GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().getRol()));
        return authorities;
    }

}
