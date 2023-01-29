package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView textViewNombre;
    CookWithMeAPI gitHub;
    public String nombreJugador;
    public Jugador jugador;
    ProgressBar progressBar5;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    static final String SHARED_PREF_NAME = "datosLogIn";
    static final String KEY_NOMBRE = "nombre";
    static final String KEY_PAIS = "pais";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";
    static final String KEY_DINERO = "dinero";
    static final String KEY_ID = "id";
    static final String KEY_NIVEL = "nivel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNombre = (TextView)findViewById(R.id.textViewNombre);
        progressBar5 = (ProgressBar)findViewById(R.id.progressBar5);

        gitHub= Client.getClient().create(CookWithMeAPI.class);

        sharedPref = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        nombreJugador = sharedPref.getString(KEY_NOMBRE,null);
        String texto="Bienvenid@ " + nombreJugador;
        textViewNombre.setText(texto);

        doApiCall();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                CerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void CerrarSesion(){
        editor.clear();
        editor.commit();
        Intent login= new Intent (MainActivity.this, LogInActivity.class);
        startActivity(login);
    }

    public void tiendaBtn(View v){
        Intent tienda= new Intent (MainActivity.this, TiendaActivity.class);
        startActivity(tienda);
    }

    public void ayudaBtn(View v){
        Intent ayuda= new Intent (MainActivity.this, AyudaActivity.class);
        startActivity(ayuda);
    }
    public void denunciaBtn(View v){
        Intent denuncia= new Intent (MainActivity.this, DenunciaActivity.class);
        startActivity(denuncia);
    }
    public void faqBtn(View v){
        Intent faq= new Intent (MainActivity.this, FAQActivity.class);
        startActivity(faq);
    }

    public void buttonPerfil(View v){
        Intent perfil= new Intent (MainActivity.this, PerfilActivity.class);
        startActivity(perfil);
    }

    public void rankingBtn(View v){
        Intent ranking= new Intent (MainActivity.this, RankingActivity.class);
        startActivity(ranking);
    }


    private void doApiCall() {
        progressBar5.setVisibility(View.VISIBLE);

        Call<Jugador> call = gitHub.getJugadorByNombre(nombreJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                // set the results to the adapter
                jugador = response.body();
                editor.putString(KEY_ID,toString().valueOf(jugador.getId()));
                editor.putString(KEY_PASSWORD,toString().valueOf(jugador.getPassword()));
                editor.putString(KEY_PAIS,toString().valueOf(jugador.getPais()));
                editor.putString(KEY_DINERO,toString().valueOf(jugador.getDinero()));
                editor.putString(KEY_EMAIL,toString().valueOf(jugador.getEmail()));
                editor.putString(KEY_NIVEL,toString().valueOf(jugador.getNivel()));

                editor.apply();
                Log.d("RESPONSE","respuesta: "+jugador.getNombre());

            }
            @Override
            public void onFailure(Call<Jugador> call, Throwable t) { //error en las comunicaciones

                String msg = "Error in retrofit: "+t.toString();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }

        });
        progressBar5.setVisibility(View.GONE);
    }

}