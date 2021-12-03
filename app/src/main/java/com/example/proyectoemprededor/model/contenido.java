package com.example.proyectoemprededor.model;

import java.io.Serializable;

public class contenido implements Serializable {

    String descripcion;
    String imagen;
    String name;

    private boolean expanded;

    public contenido(){}

    public contenido(String descripcion, String imagen, String name) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.name = name;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
