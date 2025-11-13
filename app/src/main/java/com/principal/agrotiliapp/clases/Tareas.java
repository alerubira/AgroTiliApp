package com.principal.agrotiliapp.clases;

import java.io.Serializable;

public class Tareas implements Serializable {
    /*{
        "id_tarea": 1,
        "id_tipo_tarea": 1,
        "id_campo": 1,
        "id_maquina_agraria": 1,
        "id_empleado": 2,
        "fecha_inicio": "07-11-2025",
        "fecha_fin": "07-11-2025",
        "realizada": true,
        "observaciones": "\"finalizada con exito\"",
        "tipo_Tarea": {
            "id_tipo_tarea": 1,
            "nombre_tipo_tarea": "Sembrar"
        },
        "campo": {
            "id_campo": 1,
            "nombre_campo": "La Celestina",
            "id_empleado": 1,
            "superficie": 800,
            "latitud": 33,
            "longitud": -32,
            "activo": true
        },
        "maquina_Agraria": {
            "id_maquina_agraria": 1,
            "id_tipo_tarea": 1,
            "patente": "afe234rg",
            "ocupado": false,
            "activo": true,
            "tipos_Tareas": {
                "id_tipo_tarea": 1,
                "nombre_tipo_tarea": "Sembrar"
            }
        },
        "empleado": {
            "id_empleado": 2,
            "id_role": 3,
            "apellido": "Perez",
            "nombre": "Juan",
            "email": "arubira50@gmail.com",
            "ocupado": false,
            "fecha_ingreso": "20-05-2013",
            "fecha_egreso": null,
            "activo": true,
            "nombre_role": null
        }
    },*/
    private int id_tarea;
    private int id_tipo_tarea;
    private int id_maquina_agraria;
    private int id_empleado;
    private String fecha_inicio;
    private String fecha_fin;
    private boolean realizada;
    private String observaciones;
    private Campos campo;
    private Maquinas_Agrarias maquina_Agraria;
    private Empleados empleado;

    public Tareas(int id_tarea, int id_tipo_tarea, int id_maquina_agraria, int id_empleado, String fecha_inicio, String fecha_fin, boolean realizada, String observaciones, Campos campo, Maquinas_Agrarias maquina_Agraria, Empleados empleado) {
        this.id_tarea = id_tarea;
        this.id_tipo_tarea = id_tipo_tarea;
        this.id_maquina_agraria = id_maquina_agraria;
        this.id_empleado = id_empleado;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.realizada = realizada;
        this.observaciones = observaciones;
        this.campo = campo;
        this.maquina_Agraria = maquina_Agraria;
        this.empleado = empleado;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public int getId_tipo_tarea() {
        return id_tipo_tarea;
    }

    public void setId_tipo_tarea(int id_tipo_tarea) {
        this.id_tipo_tarea = id_tipo_tarea;
    }

    public int getId_maquina_agraria() {
        return id_maquina_agraria;
    }

    public void setId_maquina_agraria(int id_maquina_agraria) {
        this.id_maquina_agraria = id_maquina_agraria;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Campos getCampo() {
        return campo;
    }

    public void setCampo(Campos campo) {
        this.campo = campo;
    }

    public Maquinas_Agrarias getMaquina_Agraria() {
        return maquina_Agraria;
    }

    public void setMaquina_Agraria(Maquinas_Agrarias maquina_Agraria) {
        this.maquina_Agraria = maquina_Agraria;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }
}
