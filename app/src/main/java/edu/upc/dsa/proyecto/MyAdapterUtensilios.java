package edu.upc.dsa.proyecto;

import android.graphics.Typeface;
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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;
import edu.upc.dsa.proyecto.models.Utensilio;
import edu.upc.dsa.proyecto.models.UtensiliosComprados;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterUtensilios extends RecyclerView.Adapter<MyAdapterUtensilios.ViewHolder> {

    private List<Utensilio> listaUtensilios;
    private List<UtensiliosComprados> utensiliosComprados;
    public Utensilio c;
    public UtensiliosComprados uc;
    public int idJugador;
    CookWithMeAPI gitHub;
    ProgressBar progressBar6;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder { // inner class , se encarga de guardar las referencias
        // each data item is just a string in this case
        public TextView TextViewNombreUtensilio, TextViewPrecioUtensilio;
        public TextView TextViewTiempoNivel1, TextViewTiempoNivel2, TextViewTiempoNivel3;
        public ImageView icon;
        public View layout;
        public Button comprarBtn;

        public ViewHolder(View v) { //le pasamos la vista reciclada y nos la guardamos
            super(v);
            layout = v;
            TextViewNombreUtensilio = (TextView) v.findViewById(R.id.nombreObjeto);
            TextViewPrecioUtensilio = (TextView) v.findViewById(R.id.precioObjeto);
            TextViewTiempoNivel1 = (TextView) v.findViewById(R.id.nivelObjeto1);
            TextViewTiempoNivel2 = (TextView) v.findViewById(R.id.nivelObjeto2);
            TextViewTiempoNivel3 = (TextView) v.findViewById(R.id.nivelObjeto3);
            icon = (ImageView) v.findViewById(R.id.icon);
            comprarBtn = (Button) v.findViewById(R.id.comprarBtn);
            progressBar6 = (ProgressBar)v.findViewById(R.id.progressBar7);
            progressBar6.setVisibility(View.INVISIBLE);
            gitHub= Client.getClient().create(CookWithMeAPI.class);
        }
    }
    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void getUtensiliosComprados(List<UtensiliosComprados> listaComprados){
        utensiliosComprados = listaComprados;
    }
    public void setData(List<Utensilio> myDataset) { //muestro los contributors que me llegan
        listaUtensilios = myDataset;
        notifyDataSetChanged(); //avisamos al android que algo ha cambiado
    }

    public void add(int position, Utensilio item) {
        listaUtensilios.add(position, item);
        notifyItemInserted(position); //avisamos al android de que ha cambiado la posición
    }

    public void remove(int position) {
        listaUtensilios.remove(position);
        notifyItemRemoved(position); //avisamos que hemos eliminado en esa posicion
    }

    public MyAdapterUtensilios() {
        listaUtensilios = new ArrayList<>();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterUtensilios(List<Utensilio> myDataset) {
        listaUtensilios = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterUtensilios.ViewHolder onCreateViewHolder(ViewGroup parent, //onCreate al hacer scroll
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

        holder.comprarBtn.setText("MEJORAR");
        c = listaUtensilios.get(position); //me guardo la posición

        for(UtensiliosComprados i : utensiliosComprados){
            if(c.getId() == i.getIdUtensilio()){
                Log.d("NIVEL", String.valueOf(i.getNivel()));
                if(i.getNivel()==1){
                    holder.TextViewTiempoNivel1.setTypeface(null, Typeface.BOLD);
                }
                else if(i.getNivel()==2){
                    holder.TextViewTiempoNivel2.setTypeface(null, Typeface.BOLD);
                }
                else if(i.getNivel()==3){
                    holder.TextViewTiempoNivel3.setTypeface(null, Typeface.BOLD);
                    holder.comprarBtn.setVisibility(View.GONE);
                }
            }
        }

        final String name = c.nombre; //recupero el login del contributor
        holder.TextViewNombreUtensilio.setText(name);

        final double precio = c.precio; //recupero el login del contributor
        holder.TextViewPrecioUtensilio.setText(toString().valueOf(precio)+" €");

        final double tiempo1 = c.tiempoNivel1; //recupero el login del contributor
        holder.TextViewTiempoNivel1.setText("Nivel: "+ toString().valueOf(tiempo1));

        final double tiempo2 = c.tiempoNivel2; //recupero el login del contributor
        holder.TextViewTiempoNivel2.setText("Nivel: "+ toString().valueOf(tiempo2));

        final double tiempo3 = c.tiempoNivel3; //recupero el login del contributor
        holder.TextViewTiempoNivel3.setText("Nivel: "+ toString().valueOf(tiempo3));

        Glide.with(holder.icon.getContext())
                .load(CookWithMeAPI.URL+c.urlImagen) //url que quiero cargar
                .into(holder.icon); //imagen que quiero cargar
        progressBar6.setVisibility(View.INVISIBLE);

        //MEJORAR
        holder.comprarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                uc = utensiliosComprados.get(pos);
                int idUtensilio = uc.getIdUtensilio();
                progressBar6.setVisibility(View.VISIBLE);

                gitHub.postUtensilioComprado(idJugador,idUtensilio).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("MEJORA","codigo: "+response.code());
                        Log.d("NIVEL", String.valueOf(uc.getNivel()+1));

                        if(response.code() == 201){
                            uc.setNivel(uc.getNivel()+1);
                            if(uc.getNivel()==2){
                                holder.TextViewTiempoNivel2.setTypeface(null, Typeface.BOLD);
                                holder.TextViewTiempoNivel1.setTypeface(null, Typeface.NORMAL);

                            }
                            else if(uc.getNivel()==3){
                                holder.TextViewTiempoNivel3.setTypeface(null, Typeface.BOLD);
                                holder.TextViewTiempoNivel2.setTypeface(null, Typeface.NORMAL);
                                holder.comprarBtn.setVisibility(View.GONE);
                            }
                            Toast.makeText(v.getContext(),"Mejorado correctamente", Toast.LENGTH_SHORT).show();
                        }
                        else if ((response.code()==404)||(response.code()==501)){
                            Toast.makeText(v.getContext(),"No tienes suficiente dinero/nivel", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.code()==502){
                            Toast.makeText(v.getContext(),"Ya estas en el nivel máximo", Toast.LENGTH_SHORT).show();
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
        return listaUtensilios.size();
    }


}