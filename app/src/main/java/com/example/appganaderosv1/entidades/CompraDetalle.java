package com.example.appganaderosv1.entidades;

import java.io.Serializable;

public class CompraDetalle implements Serializable {
    private Integer id_compra_detalle;
    private Integer ganado;
    private Integer raza;
    private Double peso_pie_compra;
    private Double peso_canal_compra;
    private Double precio;
    private Integer tara;
    private Double total;
    private String numero_arete;
    private Integer compra;

    public CompraDetalle(){

    }

    public CompraDetalle(Integer id_compra_detalle, Integer ganado, Integer raza, Double peso_pie_compra, Double peso_canal_compra, Double precio, Integer tara, Double total, String numero_arete, Integer compra) {
        this.id_compra_detalle = id_compra_detalle;
        this.ganado = ganado;
        this.raza = raza;
        this.peso_pie_compra = peso_pie_compra;
        this.peso_canal_compra = peso_canal_compra;
        this.precio = precio;
        this.tara = tara;
        this.total = total;
        this.numero_arete = numero_arete;
        this.compra = compra;
    }

    public Integer getId_compra_detalle() {
        return id_compra_detalle;
    }

    public void setId_compra_detalle(Integer id_compra_detalle) {
        this.id_compra_detalle = id_compra_detalle;
    }

    public Integer getGanado() {
        return ganado;
    }

    public void setGanado(Integer ganado) {
        this.ganado = ganado;
    }

    public Integer getRaza() {
        return raza;
    }

    public void setRaza(Integer raza) {
        this.raza = raza;
    }

    public Double getPeso_pie_compra() {
        return peso_pie_compra;
    }

    public void setPeso_pie_compra(Double peso_pie_compra) {
        this.peso_pie_compra = peso_pie_compra;
    }

    public Double getPeso_canal_compra() {
        return peso_canal_compra;
    }

    public void setPeso_canal_compra(Double peso_canal_compra) {
        this.peso_canal_compra = peso_canal_compra;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getTara() {
        return tara;
    }

    public void setTara(Integer tara) {
        this.tara = tara;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNumero_arete() {
        return numero_arete;
    }

    public void setNumero_arete(String numero_arete) {
        this.numero_arete = numero_arete;
    }

    public Integer getCompra() {
        return compra;
    }

    public void setCompra(Integer compra) {
        this.compra = compra;
    }
}
