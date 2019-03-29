package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Raza implements Serializable {
    private Integer id_raza;
    private String tipo_raza;

    public Integer getId_raza() {
        return id_raza;
    }

    public void setId_raza(Integer id_raza) {
        this.id_raza = id_raza;
    }

    public String getTipo_raza() {
        return tipo_raza;
    }

    public void setTipo_raza(String tipo_raza) {
        this.tipo_raza = tipo_raza;
    }
}
