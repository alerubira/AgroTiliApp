package com.principal.agrotiliapp.clases;

import java.io.Serializable;

public class Maquinas_Agrarias implements Serializable {
    /* {
        "id_maquina_agraria": 7,
        "id_tipo_tarea": 3,
        "patente": "jfd234rg",
        "ocupado": false,
        "activo": true,
        "tipos_Tareas": {
            "id_tipo_tarea": 3,
            "nombre_tipo_tarea": "Fumigar"
        }
    },*/
    private int id_maquina_agraria;
    private int id_tipo_tarea;
    private String patente;
    private boolean ocupado;
    private boolean activo;
    private Tipos_Tareas tipos_Tareas;

    public Maquinas_Agrarias(int id_maquina_agraria, int id_tipo_tarea, String patente, boolean ocupado, boolean activo, Tipos_Tareas tipos_Tareas) {
        this.id_maquina_agraria = id_maquina_agraria;
        this.id_tipo_tarea = id_tipo_tarea;
        this.patente = patente;
        this.ocupado = ocupado;
        this.activo = activo;
        this.tipos_Tareas = tipos_Tareas;
    }

    public int getId_maquina_agraria() {
        return id_maquina_agraria;
    }

    public void setId_maquina_agraria(int id_maquina_agraria) {
        this.id_maquina_agraria = id_maquina_agraria;
    }

    public int getId_tipo_tarea() {
        return id_tipo_tarea;
    }

    public void setId_tipo_tarea(int id_tipo_tarea) {
        this.id_tipo_tarea = id_tipo_tarea;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Tipos_Tareas getTipos_Tareas() {
        return tipos_Tareas;
    }

    public void setTipos_Tareas(Tipos_Tareas tipos_Tareas) {
        this.tipos_Tareas = tipos_Tareas;
    }
}
