package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterJugador extends RecyclerView.Adapter<MyAdapterJugador.ViewHolder>{

    private List<Jugador> listaJugadores;
    public Jugador j;
    public int idJugador;
    CookWithMeAPI gitHub;
    ProgressBar progressBar66;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder { // inner class , se encarga de guardar las referencias
        // each data item is just a string in this case
        public TextView TextViewNombreJugador, TextViewPaisJugador, TextViewNivelJugador;
        public View layout;


        public ViewHolder(View v) { //le pasamos la vista reciclada y nos la guardamos
            super(v);
            layout = v;
            TextViewNombreJugador = (TextView) v.findViewById(R.id.nombreJugador);
            TextViewPaisJugador = (TextView) v.findViewById(R.id.paisJugador);
            TextViewNivelJugador = (TextView) v.findViewById(R.id.nivelJugador);
            progressBar66 = (ProgressBar)v.findViewById(R.id.progressBar7);
            progressBar66.setVisibility(View.INVISIBLE);
            gitHub= Client.getClient().create(CookWithMeAPI.class);

        }
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    /*
    public void setIngredientesComprados(List<Ingrediente> listaComprados){
        ingredientesComprados = listaComprados;
    }*/

    public void setData(List<Jugador> myDataset) { //muestro los contributors que me llegan
        listaJugadores = myDataset;

        notifyDataSetChanged(); //avisamos al android que algo ha cambiado
    }

    public void add(int position, Jugador item) {
        listaJugadores.add(position, item);
        notifyItemInserted(position); //avisamos al android de que ha cambiado la posición
    }

    public void remove(int position) {
        listaJugadores.remove(position);
        notifyItemRemoved(position); //avisamos que hemos eliminado en esa posicion
    }

    public MyAdapterJugador() {
        listaJugadores = new ArrayList<>();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterJugador(List<Jugador> myDataset) {
        listaJugadores = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterJugador.ViewHolder onCreateViewHolder(ViewGroup parent, //onCreate al hacer scroll
                                                               int viewType) {
        // create a new view, convierte xml en layout
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.ranking_layout, parent, false); //pasamos el nombre del layout y lo colgamos en el parent

        // set the view's size, margins, paddings and layout parameters
        MyAdapterJugador.ViewHolder vh = new MyAdapterJugador.ViewHolder(v); //creamos un viewholder pasandole la vista
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyAdapterJugador.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        progressBar66.setVisibility(View.VISIBLE);

        j = listaJugadores.get(position); //me guardo la posición
        //recorrido para ver si está comprado

        final String name = j.nombre; //recupero el login del contributor
        holder.TextViewNombreJugador.setText(name);

        final String pais = j.pais; //recupero el login del contributor
        holder.TextViewPaisJugador.setText(pais);

        final double nivel = j.nivel; //recupero el login del contributor
        holder.TextViewNivelJugador.setText("Nivel: " + toString().valueOf(nivel));

        progressBar66.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }
}