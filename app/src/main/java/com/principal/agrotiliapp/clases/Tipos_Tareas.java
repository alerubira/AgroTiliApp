package com.principal.agrotiliapp.clases;

import java.io.Serializable;

public class Tipos_Tareas implements Serializable {
    private int id_tipo_tarea;
    private String nombre_tipo_tarea;

    public Tipos_Tareas(int id_tipo_tarea, String nombre_tipo_tarea) {
        this.id_tipo_tarea = id_tipo_tarea;
        this.nombre_tipo_tarea = nombre_tipo_tarea;
    }

    public int getId_tipo_tarea() {
        return id_tipo_tarea;
    }

    public void setId_tipo_tarea(int id_tipo_tarea) {
        this.id_tipo_tarea = id_tipo_tarea;
    }

    public String getNombre_tipo_tarea() {
        return nombre_tipo_tarea;
    }

    public void setNombre_tipo_tarea(String nombre_tipo_tarea) {
        this.nombre_tipo_tarea = nombre_tipo_tarea;
    }
}
