package edu.upc.dsa.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.FAQ;
import edu.upc.dsa.proyecto.models.Ingrediente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterFAQ adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    CookWithMeAPI gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = findViewById(R.id.my_swipe_refresh);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapterFAQ();
        recyclerView.setAdapter(adapter);
        gitHub= Client.getClient().create(CookWithMeAPI.class);
        doApiCall(null);
        //flecha
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

    private void doApiCall(SwipeRefreshLayout swipeRefreshLayout) {
        Call<List<FAQ>> call = gitHub.getAllFAQ();

        call.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                // set the results to the adapter
                Log.d("FAQ","codigo: "+response.code());
                Log.d("FAQ","body: "+response.body());

                adapter.setData(response.body()); //le paso el body al adapter para que la muestre

                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false); //desactiva la animación, no hace falta si no hay swipe
            }
            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) { //error en las comunicaciones
                if(swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent main= new Intent (FAQActivity.this, MainActivity.class);
                startActivity(main);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}