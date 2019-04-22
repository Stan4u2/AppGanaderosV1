package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class VentaDetalle implements Serializable {

    private Integer id_venta_detalle;
    private Integer id_ganado;
    private Integer precio_venta;
    private Integer tara_venta;
    private Integer total_venta;
    private Integer id_venta;


    public VentaDetalle(){

    }

    public VentaDetalle(Integer id_venta_detalle, Integer id_ganado, Integer precio_venta, Integer tara_venta, Integer total_venta, Integer id_venta) {
        this.id_venta_detalle = id_venta_detalle;
        this.id_ganado = id_ganado;
        this.precio_venta = precio_venta;
        this.tara_venta = tara_venta;
        this.total_venta = total_venta;
        this.id_venta = id_venta;
    }

    public Integer getId_venta_detalle() {
        return id_venta_detalle;
    }

    public void setId_venta_detalle(Integer id_venta_detalle) {
        this.id_venta_detalle = id_venta_detalle;
    }

    public Integer getId_ganado() {
        return id_ganado;
    }

    public void setId_ganado(Integer id_ganado) {
        this.id_ganado = id_ganado;
    }

    public Integer getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Integer precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Integer getTara_venta() {
        return tara_venta;
    }

    public void setTara_venta(Integer tara_venta) {
        this.tara_venta = tara_venta;
    }

    public Integer getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(Integer total_venta) {
        this.total_venta = total_venta;
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }
}
