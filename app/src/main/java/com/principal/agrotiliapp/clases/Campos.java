package com.principal.agrotiliapp.clases;

import java.io.Serializable;

public class Campos implements Serializable {
    /*{
        "id_campo": 1,
        "nombre_campo": "La Celestina",
        "id_empleado": 1,
        "superficie": 800,
        "latitud": 33,
        "longitud": -32,
        "activo": true
    },*/
    private int id_campo;
    private String nombre_campo;
    private int id_empleado;
    private double superficie;
    private double latitud;
    private double longitud;
    private boolean activo;

    public Campos(int id_campo, String nombre_campo, int id_empleado, double superficie, double latitud, double longitud, boolean activo) {
        this.id_campo = id_campo;
        this.nombre_campo = nombre_campo;
        this.id_empleado = id_empleado;
        this.superficie = superficie;
        this.latitud = latitud;
        this.longitud = longitud;
        this.activo = activo;
    }

    public int getId_campo() {
        return id_campo;
    }

    public void setId_campo(int id_campo) {
        this.id_campo = id_campo;
    }

    public String getNombre_campo() {
        return nombre_campo;
    }

    public void setNombre_campo(String nombre_campo) {
        this.nombre_campo = nombre_campo;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
