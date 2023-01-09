package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.IngredientesComprados;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterIngredientes adapter;
    ProgressBar progressBar2;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    CookWithMeAPI gitHub;
    public int idJugador;
    List<Ingrediente> ingredientesComprados;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = findViewById(R.id.my_swipe_refresh);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        idJugador = Integer.parseInt(sharedPref.getString(KEY_ID,null));

        // define an adapter
        adapter = new MyAdapterIngredientes();
        recyclerView.setAdapter(adapter);
        gitHub= Client.getClient().create(CookWithMeAPI.class);

        doApiCall(null); //hace las llamadas
        doApiCallIngredientesComprados();

        // Manage swipe on items, controla los movimientos
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        adapter.remove(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doApiCall(swipeRefreshLayout);
                    }
                }
        );
    }
    private void doApiCallIngredientesComprados() {

        progressBar2.setVisibility(View.VISIBLE);
        Call<List<Ingrediente>> call = gitHub.getIngredientesComprados(idJugador); //CAMBIAR POR LA VARIABLE!!!!!
        Log.d("idJugador", String.valueOf(idJugador));
        call.enqueue(new Callback<List<Ingrediente>>() {
            @Override
            public void onResponse(Call<List<Ingrediente>> call, Response<List<Ingrediente>> response) {
                // set the results to the adapter
                ingredientesComprados=response.body();
                adapter.setIngredientesComprados(ingredientesComprados);
                adapter.setIdJugador(idJugador);
                for(Ingrediente i : ingredientesComprados){
                    Log.d("RESPUESTA", i.getNombre());
                }
            }
            @Override
            public void onFailure(Call<List<Ingrediente>> call, Throwable t) { //error en las comunicaciones

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar2.setVisibility(View.GONE);
    }

    private void doApiCall(SwipeRefreshLayout swipeRefreshLayout) {
        Call<List<Ingrediente>> call = gitHub.getAllIngredientes();

        call.enqueue(new Callback<List<Ingrediente>>() {
            @Override
            public void onResponse(Call<List<Ingrediente>> call, Response<List<Ingrediente>> response) {
                // set the results to the adapter
                adapter.setData(response.body()); //le paso el body al adapter para que la muestre

                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false); //desactiva la animación, no hace falta si no hay swipe
            }
            @Override
            public void onFailure(Call<List<Ingrediente>> call, Throwable t) { //error en las comunicaciones
                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar2.setVisibility(View.GONE);
    }

    public void volverBtn(View v){
        Intent main= new Intent (IngredientesActivity.this, TiendaActivity.class);
        startActivity(main);
    }


}