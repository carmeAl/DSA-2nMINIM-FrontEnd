package edu.upc.dsa.proyecto;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    Button ingredientesBtn, utensiliosBtn;
    TextView textViewDinero, textViewNivel;
    Jugador jugador;
    String nombreJugador;
    SharedPreferences sharedPref;
    CookWithMeAPI gitHub;
    ProgressBar progressBar6;
    //SharedPreferences
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DINERO = "dinero";
    private static final String KEY_NIVEL = "nivel";
    //Carousel
    //carousel1.registerLifecycle(getLifecycle());
    List<CarouselItem> list1 = new ArrayList<>();
    List<CarouselItem> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        ingredientesBtn = (Button)findViewById(R.id.ingredientesBtn);
        utensiliosBtn = (Button)findViewById(R.id.utensiliosBtn);
        textViewDinero = (TextView)findViewById(R.id.textViewDinero);
        textViewNivel = (TextView)findViewById(R.id.textViewNivel);
        progressBar6 = (ProgressBar)findViewById(R.id.progressBar6);
        //shared preferences
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String dinero = sharedPref.getString(KEY_DINERO,null);
        textViewDinero.setText(dinero);
        String nivel = sharedPref.getString(KEY_NIVEL,null);
        textViewNivel.setText(nivel);
        nombreJugador = sharedPref.getString(KEY_NOMBRE,null);
        //github
        gitHub= Client.getClient().create(CookWithMeAPI.class);
        doApiCall();
        //flecha
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Carousel
        ImageCarousel carousel1 = findViewById(R.id.carousel1);
        list1.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Ketchup.png", "Ketchup"));
        list1.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Hamburguesa.png", "Hamburguesa"));
        list1.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Lechuga.png", "Lechuga"));
        list1.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Queso.png", "Queso"));
        carousel1.setData(list1);
        ImageCarousel carousel2 = findViewById(R.id.carousel2);
        list2.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Cuchillo.png", "Cuchillo"));
        list2.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Plancha.png", "Plancha"));
        list2.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Plato.png", "Plato"));
        list2.add(new CarouselItem(CookWithMeAPI.URL+"/ingredientesUtensilios/Freidora.png", "Freidora"));
        carousel2.setData(list2);
        progressBar6.setVisibility(View.INVISIBLE);

    }

    private void doApiCall(){ //para ir actualizando el dinero
        progressBar6.setVisibility(View.VISIBLE);

        Call<Jugador> call = gitHub.getJugadorByNombre(nombreJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                // set the results to the adapter
                jugador = response.body();
                editor.putString(KEY_DINERO,toString().valueOf(jugador.getDinero()));
                editor.apply();
                textViewDinero.setText(String.valueOf(jugador.getDinero()));
                Log.d("RESPONSE","respuesta: "+jugador.getDinero());
            }
            @Override
            public void onFailure(Call<Jugador> call, Throwable t) { //error en las comunicaciones

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
        progressBar6.setVisibility(View.GONE);
    }

    public void ingredientesBtn(View v){
        Intent tienda1= new Intent (TiendaActivity.this, IngredientesActivity.class);
        startActivity(tienda1);
    }
    public void utensiliosBtn(View v){
        Intent tienda2= new Intent (TiendaActivity.this, UtensiliosActivity.class);
        startActivity(tienda2);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent main= new Intent (TiendaActivity.this, MainActivity.class);
                startActivity(main);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}