package edu.upc.dsa.proyecto.models;

public class LogIn {

    String nombre;
    String password;

    public LogIn (String username, String password){
        this.nombre=username;
        this.password=password;
    }

    public String getUsername() {
        return nombre;
    }

    public void setUsername(String username) {
        this.nombre = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
