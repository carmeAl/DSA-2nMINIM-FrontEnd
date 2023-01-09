package edu.upc.dsa.proyecto.models;

public class Ingrediente {

    int id;
    public String nombre;
    public int nivelDesbloqueo;
    public double precio;
    public String urlImagen;

    public Ingrediente (String nombreIngrediente, int idIngrediente, int nivelDesbloqueoIngrediente, double precioIngrediente, String imagen) {
        this.setNombre(nombreIngrediente);
        this.setId(idIngrediente);
        this.setNivelDesbloqueo(nivelDesbloqueoIngrediente);
        this.setPrecio(precioIngrediente);
        this.setUrlImagen(imagen);

    }
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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

    public int getNivelDesbloqueo() {
        return nivelDesbloqueo;
    }

    public void setNivelDesbloqueo(int nivelDesbloqueo) {
        this.nivelDesbloqueo = nivelDesbloqueo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
