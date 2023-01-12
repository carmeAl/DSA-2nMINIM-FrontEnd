package edu.upc.dsa.proyecto.api;

import java.util.List;

import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;
import edu.upc.dsa.proyecto.models.Jugador;
import edu.upc.dsa.proyecto.models.LogIn;
import edu.upc.dsa.proyecto.models.Register;
import edu.upc.dsa.proyecto.models.Utensilio;
import edu.upc.dsa.proyecto.models.UtensiliosComprados;
import retrofit2.Call;
import retrofit2.http.*;

public interface CookWithMeAPI { //MyService

    //String URL = "http://147.83.7.207:8080/";
    String URL = "http://10.0.2.2:8080/";

    @POST("/dsaApp/jugador/Login")
    Call<Void> logIn(@Body LogIn logIn);

    @GET("/dsaApp/jugador/ranking")
    Call<List<Jugador>> getRanking();

    @POST("/dsaApp/jugador/Register")
    Call<Void> register(@Body Register register);

    @GET("/dsaApp/ingrediente/getAllIngredientes")
    Call<List<Ingrediente>> getAllIngredientes();

    @GET("/dsaApp/utensilio/getAllUtensilios")
    Call<List<Utensilio>> getAllUtensilios();

    //Le pasamos nombre de usuario y devuelve el Jugador
    @GET("/dsaApp/jugador/{nombreJugador}")
    Call<Jugador> getJugadorByNombre(@Path("nombreJugador") String nombreJugador);

    @GET("/dsaApp/ingrediente/getLista/{idJugador}")
    Call<List<Ingrediente>> getIngredientesComprados(@Path("idJugador") int idJugador);

    @POST("/dsaApp/ingrediente/postIngredienteComprado")
    Call<Void> postIngredienteComprado(@Body IngredientesComprados ic);

    @PUT("/dsaApp/utensilio/postUtensilioComprado/{idJugador}{idUtensilio}")
    Call<Void> postUtensilioComprado(@Path("idJugador") int idJugador, @Path("idUtensilio") int idUtensilio);

    @GET("/dsaApp/utensilio/getLista/{idJugador}")
    Call<List<UtensiliosComprados>> getUtensiliosComprados(@Path("idJugador") int idJugador);
}