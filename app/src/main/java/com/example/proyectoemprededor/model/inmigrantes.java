package com.example.proyectoemprededor.model;

import java.io.Serializable;
import java.util.List;

public class inmigrantes implements Serializable {

    String actividaurl;
    String documentourl;
    String materialurl;
    String titulo;
    String video;
    String imagenurl;
    List<contenido> contenido;

    public inmigrantes(){}

    public inmigrantes(String actividaurl, String documentourl, String materialurl, String titulo, String video, String imagenurl, List<contenido> contenido) {
        this.actividaurl = actividaurl;
        this.documentourl = documentourl;
        this.materialurl = materialurl;
        this.titulo = titulo;
        this.video = video;
        this.imagenurl = imagenurl;
        this.contenido = contenido;
    }

    public String getImagenurl() {
        return imagenurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    public String getActividaurl() {
        return actividaurl;
    }

    public void setActividaurl(String actividaurl) {
        this.actividaurl = actividaurl;
    }

    public String getDocumentourl() {
        return documentourl;
    }

    public void setDocumentourl(String documentourl) {
        this.documentourl = documentourl;
    }

    public String getMaterialurl() {
        return materialurl;
    }

    public void setMaterialurl(String materialurl) {
        this.materialurl = materialurl;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<contenido> getContenido() {
        return contenido;
    }

    public void setContenido(List<contenido> contenido) {
        this.contenido = contenido;
    }

}

