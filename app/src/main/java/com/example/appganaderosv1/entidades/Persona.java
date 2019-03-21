package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Persona implements Serializable {

    private Integer id_persona;
    private String nombre;
    private String telefono;
    private String domicilio;
    private String datos_extras;

    public Persona(){

    }

    public Persona(Integer id_persona, String nombre, String telefono, String domicilio, String datos_extras) {
        this.id_persona = id_persona;
        this.nombre = nombre;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.datos_extras = datos_extras;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDatos_extras() {
        return datos_extras;
    }

    public void setDatos_extras(String datos_extras) {
        this.datos_extras = datos_extras;
    }
}
