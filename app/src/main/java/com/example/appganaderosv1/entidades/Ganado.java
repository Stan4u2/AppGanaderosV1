package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Ganado implements Serializable {

    private Integer id_ganado;
    private String tipo_ganado;
    private Integer raza;

    public Integer getId_ganado() {
        return id_ganado;
    }

    public void setId_ganado(Integer id_ganado) {
        this.id_ganado = id_ganado;
    }

    public String getTipo_ganado() {
        return tipo_ganado;
    }

    public void setTipo_ganado(String tipo_ganado) {
        this.tipo_ganado = tipo_ganado;
    }

    public Integer getRaza() {
        return raza;
    }

    public void setRaza(Integer raza) {
        this.raza = raza;
    }
}
