package edu.upc.dsa.proyecto.models;

public class UtensiliosComprados {

    int idUtensilio;
    int idJugador;
    int nivel;

    public UtensiliosComprados(){}

    public UtensiliosComprados(int idUtensilioComprado, int idJugador, int nivel){
        this.idUtensilio = idUtensilioComprado;
        this.idJugador = idJugador;
        this.nivel = nivel;
    }

    public int getIdUtensilio() {
        return idUtensilio;
    }

    public void setIdUtensilio(int idUtensilio) {
        this.idUtensilio = idUtensilio;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}

