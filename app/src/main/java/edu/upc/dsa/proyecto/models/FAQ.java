package edu.upc.dsa.proyecto.models;

public class FAQ {

    String pregunta;
    String respuesta;

    public FAQ (String pregunta, String respuesta) {
        this.setPregunta(pregunta);
        this.setRespuesta(respuesta);


    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
