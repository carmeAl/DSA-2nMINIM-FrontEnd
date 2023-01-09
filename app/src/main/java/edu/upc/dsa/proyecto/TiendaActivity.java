package edu.upc.dsa.proyecto;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    Button ingredientesBtn, utensiliosBtn;
    TextView textViewDinero;
    Jugador jugador;
    String nombreJugador;
    SharedPreferences sharedPref;
    CookWithMeAPI gitHub;
    ProgressBar progressBar6;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DINERO = "dinero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        ingredientesBtn = (Button)findViewById(R.id.ingredientesBtn);
        utensiliosBtn = (Button)findViewById(R.id.utensiliosBtn);
        textViewDinero = (TextView)findViewById(R.id.textViewDinero);
        progressBar6 = (ProgressBar)findViewById(R.id.progressBar6);
        progressBar6.setVisibility(View.INVISIBLE);
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String dinero = sharedPref.getString(KEY_DINERO,null);
        textViewDinero.setText(dinero);
        nombreJugador = sharedPref.getString(KEY_NOMBRE,null);
        gitHub= Client.getClient().create(CookWithMeAPI.class);
        doApiCall();
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
    public void atrasBtn(View v){
        Intent main= new Intent (TiendaActivity.this, MainActivity.class);
        startActivity(main);
    }

}