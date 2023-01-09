package edu.upc.dsa.proyecto.models;

public class Register {

    String nombre;
    String password;
    String email;
    String pais;

    public Register (String nombre, String password, String email, String pais){
        this.nombre=nombre;
        this.password=password;
        this.email = email;
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String username) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
