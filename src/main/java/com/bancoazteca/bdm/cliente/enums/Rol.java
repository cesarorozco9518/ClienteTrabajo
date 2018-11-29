/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancoazteca.bdm.cliente.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author acruzb
 */
public enum Rol {
    SUPERADMIN("SUPERADMIN"),
    ADMIN("ADMIN"),
    DESARROLLADOR("DESARROLLADOR");

    private String rol;

    private Rol(String rol) {
        this.rol = rol;
    }

    @JsonCreator
    public static Rol create(String value) {
        for (Rol rol : Rol.values()) {
            if (rol.toString().equals(value) || String.valueOf(rol.getRol()).equals(value)) {
                return rol;
            }
        }
        return null;
    }

    @JsonValue
    public String getRol() {
        return rol;
    }
}
