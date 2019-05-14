package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class Ventas implements Serializable {

    private Integer id_ventas;
    private Integer persona_venta;
    private String fecha;
    private Integer cantidad_animales;
    private Integer cantidad_cobrar;
    private Integer ganancias;
    private Integer respaldo;

    public Ventas() {

    }

    public Ventas(Integer id_ventas, Integer persona_venta, String fecha, Integer cantidad_animales, Integer cantidad_cobrar, Integer ganancias, Integer respaldo) {
        this.id_ventas = id_ventas;
        this.persona_venta = persona_venta;
        this.fecha = fecha;
        this.cantidad_animales = cantidad_animales;
        this.cantidad_cobrar = cantidad_cobrar;
        this.ganancias = ganancias;
        this.respaldo = respaldo;
    }

    public Integer getId_ventas() {
        return id_ventas;
    }

    public void setId_ventas(Integer id_ventas) {
        this.id_ventas = id_ventas;
    }

    public Integer getPersona_venta() {
        return persona_venta;
    }

    public void setPersona_venta(Integer persona_venta) {
        this.persona_venta = persona_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad_animales() {
        return cantidad_animales;
    }

    public void setCantidad_animales(Integer cantidad_animales) {
        this.cantidad_animales = cantidad_animales;
    }

    public Integer getCantidad_cobrar() {
        return cantidad_cobrar;
    }

    public void setCantidad_cobrar(Integer cantidad_cobrar) {
        this.cantidad_cobrar = cantidad_cobrar;
    }

    public Integer getGanancias() {
        return ganancias;
    }

    public void setGanancias(Integer ganancias) {
        this.ganancias = ganancias;
    }

    public Integer getRespaldo() {
        return respaldo;
    }

    public void setRespaldo(Integer respaldo) {
        this.respaldo = respaldo;
    }
}
