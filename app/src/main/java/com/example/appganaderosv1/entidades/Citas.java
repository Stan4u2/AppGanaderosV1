package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Citas implements Serializable {

    private Integer id_citas;
    private Integer cantidad_ganado;
    private String datos;
    private String fecha;
    private Integer persona_cita;
    private Integer respaldo;

    public Citas() {

    }

    public Citas(Integer id_citas, Integer cantidad_ganado, String datos, String fecha, Integer persona_cita, Integer respaldo) {
        this.id_citas = id_citas;
        this.cantidad_ganado = cantidad_ganado;
        this.datos = datos;
        this.fecha = fecha;
        this.persona_cita = persona_cita;
        this.respaldo = respaldo;
    }

    public Integer getId_citas() {
        return id_citas;
    }

    public void setId_citas(Integer id_citas) {
        this.id_citas = id_citas;
    }

    public Integer getCantidad_ganado() {
        return cantidad_ganado;
    }

    public void setCantidad_ganado(Integer cantidad_ganado) {
        this.cantidad_ganado = cantidad_ganado;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPersona_cita() {
        return persona_cita;
    }

    public void setPersona_cita(Integer persona_cita) {
        this.persona_cita = persona_cita;
    }

    public Integer getRespaldo() {
        return respaldo;
    }

    public void setRespaldo(Integer respaldo) {
        this.respaldo = respaldo;
    }
}
