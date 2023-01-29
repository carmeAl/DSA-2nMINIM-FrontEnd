package edu.upc.dsa.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.FAQ;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;

public class MyAdapterFAQ extends RecyclerView.Adapter<MyAdapterFAQ.ViewHolder> {

    private List<FAQ> listaFAQ;
    public FAQ faq;

    public class ViewHolder extends RecyclerView.ViewHolder { // inner class , se encarga de guardar las referencias
        // each data item is just a string in this case
        public TextView preguntaText, respuestaText;
        public View layout;

        public ViewHolder(View v) { //le pasamos la vista reciclada y nos la guardamos
            super(v);
            layout = v;
            preguntaText = (TextView) v.findViewById(R.id.preguntaText);
            respuestaText = (TextView) v.findViewById(R.id.respuestaText);
        }
    }
    public void setData(List<FAQ> myDataset) { //muestro los contributors que me llegan
        listaFAQ = myDataset;
        notifyDataSetChanged(); //avisamos al android que algo ha cambiado
    }

    public void add(int position, FAQ item) {
        listaFAQ.add(position, item);
        notifyItemInserted(position); //avisamos al android de que ha cambiado la posición
    }

    public void remove(int position) {
        listaFAQ.remove(position);
        notifyItemRemoved(position); //avisamos que hemos eliminado en esa posicion
    }

    public MyAdapterFAQ() {
        listaFAQ = new ArrayList<>();
    }

    public MyAdapterFAQ(List<FAQ> myDataset) {
        listaFAQ = myDataset;
    }

    @NonNull
    @Override
    public MyAdapterFAQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view, convierte xml en layout
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout2, parent, false); //pasamos el nombre del layout y lo colgamos en el parent

        // set the view's size, margins, paddings and layout parameters
        MyAdapterFAQ.ViewHolder vh = new MyAdapterFAQ.ViewHolder(v); //creamos un viewholder pasandole la vista
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        faq = listaFAQ.get(position); //me guardo la posición

        final String pregunta = faq.getPregunta(); //recupero el login del contributor
        holder.preguntaText.setText(pregunta);

        final String respuesta = faq.getRespuesta(); //recupero el login del contributor
        holder.respuestaText.setText(respuesta);
    }

    @Override
    public int getItemCount() {
        return listaFAQ.size();
    }


}
