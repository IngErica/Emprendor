package com.example.proyectoemprededor.model;

import java.io.Serializable;

public class persona implements Serializable {

    private String nombre;
    private String correo;
    private String foto;
    private String id;
    private String estadoUnidad;

    public persona(){}
    public persona(String nombre, String correo, String foto, String id, String estadounidad) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.id = id;
        this.estadoUnidad = estadounidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }
}
