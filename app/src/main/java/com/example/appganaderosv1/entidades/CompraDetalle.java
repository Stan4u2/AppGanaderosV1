package com.example.appganaderosv1.entidades;

public class CompraDetalle {
    private Integer id_compra_detalle;
    private Integer ganado;
    private Double peso;
    private Double precio;
    private Integer tara;
    private Double total;
    private Integer numero_arete;
    private Integer compra;

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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
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

    public Integer getNumero_arete() {
        return numero_arete;
    }

    public void setNumero_arete(Integer numero_arete) {
        this.numero_arete = numero_arete;
    }

    public Integer getCompra() {
        return compra;
    }

    public void setCompra(Integer compra) {
        this.compra = compra;
    }
}
