package edu.upc.dsa.proyecto.models;

public class IngredientesComprados {

    int idIngrediente;
    int idJugador;

    public IngredientesComprados(){}

    public IngredientesComprados(int idIngrediente, int idJugador){
        this.idIngrediente = idIngrediente;
        this.idJugador = idJugador;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }
}

