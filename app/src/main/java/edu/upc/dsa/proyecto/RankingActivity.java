package edu.upc.dsa.proyecto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.proyecto.MyAdapterIngredientes;
import edu.upc.dsa.proyecto.R;
import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Ingrediente;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity {


    //TextView textViewNivel;
    private RecyclerView recyclerView1;
    private MyAdapterJugador adapter;
    ProgressBar progressBar55;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    CookWithMeAPI gitHub;
    public int idJugador;
    List<Jugador> jugadores;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_ID = "id";
    //private static final String KEY_NIVEL = "nivel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        recyclerView1 = (RecyclerView)findViewById(R.id.my_recycler_view1);
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //textViewNivel = (TextView)findViewById(R.id.textViewNivel);
        progressBar55 = (ProgressBar)findViewById(R.id.progressBar55);
        idJugador = Integer.parseInt(sharedPref.getString(KEY_ID,null));
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(layoutManager);


        // define an adapter

        adapter = new MyAdapterJugador();
        recyclerView1.setAdapter(adapter);
        gitHub= Client.getClient().create(CookWithMeAPI.class);

        doApiCall(null); //hace las llamadas

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
        itemTouchHelper.attachToRecyclerView(recyclerView1);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doApiCall(swipeRefreshLayout);
                    }
                }
        );

    }

    private void doApiCall(SwipeRefreshLayout swipeRefreshLayout) {
        Call<List<Jugador>> call = gitHub.getRanking();

        call.enqueue(new Callback<List<Jugador>>() {
            @Override
            public void onResponse(Call<List<Jugador>> call, Response<List<Jugador>> response) {
                // set the results to the adapter
                adapter.setData(response.body()); //le paso el body al adapter para que la muestre

                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false); //desactiva la animación, no hace falta si no hay swipe
            }
            @Override
            public void onFailure(Call<List<Jugador>> call, Throwable t) { //error en las comunicaciones
                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar55.setVisibility(View.GONE);
    }

    public void atrasBtn(View v){
        Intent atras= new Intent (RankingActivity.this, MainActivity.class);
        startActivity(atras);
    }
}