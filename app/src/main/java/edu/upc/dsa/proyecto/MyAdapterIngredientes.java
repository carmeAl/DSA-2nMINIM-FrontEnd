package edu.upc.dsa.proyecto;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterIngredientes extends RecyclerView.Adapter<MyAdapterIngredientes.ViewHolder> {

    private List<Ingrediente> listaIngredientes;
    private List<Ingrediente> ingredientesComprados;
    public Ingrediente c;
    public IngredientesComprados ic;
    public int idJugador;
    CookWithMeAPI gitHub;
    ProgressBar progressBar6;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder { // inner class , se encarga de guardar las referencias
        // each data item is just a string in this case
        public TextView TextViewNombreIngrediente, TextViewPrecioIngrediente, TextViewNivelIngrediente;
        public ImageView urlImagen;
        public View layout;
        public Button comprarBtn;

        public ViewHolder(View v) { //le pasamos la vista reciclada y nos la guardamos
            super(v);
            layout = v;
            TextViewNombreIngrediente = (TextView) v.findViewById(R.id.nombreObjeto);
            TextViewPrecioIngrediente = (TextView) v.findViewById(R.id.precioObjeto);
            TextViewNivelIngrediente = (TextView) v.findViewById(R.id.nivelObjeto1);
            urlImagen = (ImageView) v.findViewById(R.id.icon);
            comprarBtn = (Button) v.findViewById(R.id.comprarBtn);
            progressBar6 = (ProgressBar)v.findViewById(R.id.progressBar7);
            progressBar6.setVisibility(View.INVISIBLE);
            gitHub= Client.getClient().create(CookWithMeAPI.class);

        }
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setIngredientesComprados(List<Ingrediente> listaComprados){
        ingredientesComprados = listaComprados;
    }

    public void setData(List<Ingrediente> myDataset) { //muestro los contributors que me llegan
        listaIngredientes = myDataset;
        notifyDataSetChanged(); //avisamos al android que algo ha cambiado
    }

    public void add(int position, Ingrediente item) {
        listaIngredientes.add(position, item);
        notifyItemInserted(position); //avisamos al android de que ha cambiado la posición
    }

    public void remove(int position) {
        listaIngredientes.remove(position);
        notifyItemRemoved(position); //avisamos que hemos eliminado en esa posicion
    }

    public MyAdapterIngredientes() {
        listaIngredientes = new ArrayList<>();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterIngredientes(List<Ingrediente> myDataset) {
        listaIngredientes = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterIngredientes.ViewHolder onCreateViewHolder(ViewGroup parent, //onCreate al hacer scroll
                                                               int viewType) {
        // create a new view, convierte xml en layout
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false); //pasamos el nombre del layout y lo colgamos en el parent

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v); //creamos un viewholder pasandole la vista
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        progressBar6.setVisibility(View.VISIBLE);

        c = listaIngredientes.get(position); //me guardo la posición
        //recorrido para ver si está comprado
        for(Ingrediente i : ingredientesComprados){

            if(c.getNombre().equals(i.getNombre())){
                holder.comprarBtn.setVisibility(View.GONE);
            }
        }
        final String name = c.nombre; //recupero el login del contributor
        holder.TextViewNombreIngrediente.setText(name);

        final double precio = c.precio; //recupero el login del contributor
        holder.TextViewPrecioIngrediente.setText("Precio: "+toString().valueOf(precio) + "€");

        final double nivel = c.nivelDesbloqueo; //recupero el login del contributor
        holder.TextViewNivelIngrediente.setText("Nivel: "+ toString().valueOf(nivel));

        Glide.with(holder.urlImagen.getContext()).load(CookWithMeAPI.URL+c.urlImagen).into(holder.urlImagen); //imagen que quiero cargar
        progressBar6.setVisibility(View.INVISIBLE);

        //COMPRAR
        holder.comprarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                c=listaIngredientes.get(pos);
                int idIngrediente = c.getId();
                progressBar6.setVisibility(View.VISIBLE);
                gitHub.postIngredienteComprado(new edu.upc.dsa.proyecto.models.IngredientesComprados(idIngrediente,idJugador)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("COMPRA","codigo: "+response.code());
                        Log.d("compra", String.valueOf(idIngrediente));
                        Log.d("compra", String.valueOf(idJugador));

                        if(response.code() == 201){
                            holder.comprarBtn.setVisibility(View.GONE);
                            Toast.makeText(v.getContext(),"Comprado correctamente", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.code()==501) {
                            Toast.makeText(v.getContext(),"No tienes suficiente dinero", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.code()==502) {
                            Toast.makeText(v.getContext(),"No tienes suficiente nivel", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        String msg = "Error in retrofit: " + t.toString();
                        Log.d("RECIPE",msg);
                    }
                });
                progressBar6.setVisibility(View.INVISIBLE);

            }
        });

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

}