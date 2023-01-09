package edu.upc.dsa.proyecto.models;

public class Utensilio {

    public int id;
    public String nombre;
    public double precio;
    public int tiempoNivel1, tiempoNivel2, tiempoNivel3;
    public String urlImagen;

    public Utensilio (String nombreUtensilio, int idUtensilio, int tiempoNivel1, int tiempoNivel2, int tiempoNivel3, double precioUtensilio, String imagenUtensilio) {
        this.setNombre(nombreUtensilio);
        this.setId(idUtensilio);
        this.setTiempoNivel1(tiempoNivel1);
        this.setTiempoNivel2(tiempoNivel2);
        this.setTiempoNivel3(tiempoNivel3);
        this.setPrecio(precioUtensilio);
        this.setUrlImagen(imagenUtensilio);
    }
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String imagen) {
        this.urlImagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoNivel1() {
        return tiempoNivel1;
    }

    public void setTiempoNivel1(int tiempoNivel1) {
        this.tiempoNivel1 = tiempoNivel1;
    }

    public int getTiempoNivel2() {
        return tiempoNivel2;
    }

    public void setTiempoNivel2(int tiempoNivel2) {
        this.tiempoNivel2 = tiempoNivel2;
    }

    public int getTiempoNivel3() {
        return tiempoNivel3;
    }

    public void setTiempoNivel3(int tiempoNivel3) {
        this.tiempoNivel3 = tiempoNivel3;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
