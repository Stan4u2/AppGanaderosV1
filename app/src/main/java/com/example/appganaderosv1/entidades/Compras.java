package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Compras implements Serializable {
    private Integer id_compras;
    private Integer persona_compro;
    private String fecha_compra;
    private Integer cantidad_animales_compra;
    private Integer cantidad_pagar;
    private Integer respaldo;

    public Compras() {

    }

    public Compras(Integer id_compras, Integer persona_compro, String fecha_compra, Integer cantidad_animales_compra, Integer cantidad_pagar, Integer respaldo) {
        this.id_compras = id_compras;
        this.persona_compro = persona_compro;
        this.fecha_compra = fecha_compra;
        this.cantidad_animales_compra = cantidad_animales_compra;
        this.cantidad_pagar = cantidad_pagar;
        this.respaldo = respaldo;
    }

    public Integer getId_compras() {
        return id_compras;
    }

    public void setId_compras(Integer id_compras) {
        this.id_compras = id_compras;
    }

    public Integer getPersona_compro() {
        return persona_compro;
    }

    public void setPersona_compro(Integer persona_compro) {
        this.persona_compro = persona_compro;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public Integer getCantidad_animales_compra() {
        return cantidad_animales_compra;
    }

    public void setCantidad_animales_compra(Integer cantidad_animales_compra) {
        this.cantidad_animales_compra = cantidad_animales_compra;
    }

    public Integer getCantidad_pagar() {
        return cantidad_pagar;
    }

    public void setCantidad_pagar(Integer cantidad_pagar) {
        this.cantidad_pagar = cantidad_pagar;
    }

    public Integer getRespaldo() {
        return respaldo;
    }

    public void setRespaldo(Integer respaldo) {
        this.respaldo = respaldo;
    }
}
