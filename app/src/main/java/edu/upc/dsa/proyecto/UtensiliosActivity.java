package edu.upc.dsa.proyecto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.Utensilio;
import edu.upc.dsa.proyecto.models.UtensiliosComprados;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtensiliosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterUtensilios adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    public int idJugador;
    CookWithMeAPI gitHub;
    ProgressBar progressBar;
    List<UtensiliosComprados> utensiliosComprados;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utensilios);
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
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        idJugador = Integer.parseInt(sharedPref.getString(KEY_ID,null));
        // define an adapter
        adapter = new MyAdapterUtensilios();
        recyclerView.setAdapter(adapter);
        gitHub= Client.getClient().create(CookWithMeAPI.class);
        //define progressbar
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //flecha
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        doApiCall(null); //hace las llamadas
        doApiCallUtensiliosComprados();

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
    private void doApiCallUtensiliosComprados() {

        progressBar.setVisibility(View.VISIBLE);
        Call<List<UtensiliosComprados>> call = gitHub.getUtensiliosComprados(idJugador);
        Log.d("idJugador", String.valueOf(idJugador));
        call.enqueue(new Callback<List<UtensiliosComprados>>() {
            @Override
            public void onResponse(Call<List<UtensiliosComprados>> call, Response<List<UtensiliosComprados>> response) {
                // set the results to the adapter
                utensiliosComprados=response.body();
                adapter.getUtensiliosComprados(utensiliosComprados);
                adapter.setIdJugador(idJugador);
                for(UtensiliosComprados i : utensiliosComprados){
                    Log.d("RESPUESTA", String.valueOf(i.getIdUtensilio()));
                }
            }
            @Override
            public void onFailure(Call<List<UtensiliosComprados>> call, Throwable t) { //error en las comunicaciones
                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    private void doApiCall(SwipeRefreshLayout swipeRefreshLayout) {
        Call<List<Utensilio>> call = gitHub.getAllUtensilios();

        call.enqueue(new Callback<List<Utensilio>>() {
            @Override
            public void onResponse(Call<List<Utensilio>> call, Response<List<Utensilio>> response) {
                // set the results to the adapter
                adapter.setData(response.body()); //le paso el body al adapter para que la muestre

                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false); //desactiva la animación, no hace falta si no hay swipe
            }
            @Override
            public void onFailure(Call<List<Utensilio>> call, Throwable t) { //error en las comunicaciones
                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent main= new Intent (UtensiliosActivity.this, TiendaActivity.class);
                startActivity(main);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}