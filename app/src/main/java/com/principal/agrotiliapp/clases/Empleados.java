package com.principal.agrotiliapp.clases;

public class Empleados {
    /*
    "id_empleado": 1,
    "id_role": 2,
    "apellido": "Rubira",
    "nombre": " Alejandro",
    "email": "arubira60@gmail.com",
    "ocupado": false,
    "fecha_ingreso": "20-10-2000",
    "fecha_egreso": null,
    "activo": true,
    "nombre_role": "Capataz"*/
    private int id_empleado;
    private int id_role;
    private String apellido;
    private String nombre;
    private String email;
    private boolean ocupado;
    private String fecha_ingreso;
    private String fecha_egreso;
    private boolean activo;
    private String nombre_role;
    public Empleados(int id_empleado, int id_role, String apellido, String nombre, String email, boolean ocupado, String fecha_ingreso, String fecha_egreso, boolean activo, String nombre_role) {
        this.id_empleado = id_empleado;
        this.id_role = id_role;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.ocupado = ocupado;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_egreso = fecha_egreso;
        this.activo = activo;
        this.nombre_role = nombre_role;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_egreso() {
        return fecha_egreso;
    }

    public void setFecha_egreso(String fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre_role() {
        return nombre_role;
    }

    public void setNombre_role(String nombre_role) {
        this.nombre_role = nombre_role;
    }
}
