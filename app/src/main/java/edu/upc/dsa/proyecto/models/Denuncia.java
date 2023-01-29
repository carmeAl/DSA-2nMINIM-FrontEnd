package edu.upc.dsa.proyecto.models;

public class Denuncia {

    String date;
    String informer;
    String message;
    public Denuncia() {

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) {
        this.informer = informer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Denuncia (String date, String informer, String message) {
        this();
        this.setDate(date);
        this.setInformer(informer);
        this.setMessage(message);
    }
}
