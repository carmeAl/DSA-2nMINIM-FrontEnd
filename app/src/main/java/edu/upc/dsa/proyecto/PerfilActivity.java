package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Jugador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private Button button;
    private Locale locale;
    private Configuration config = new Configuration();

    CookWithMeAPI gitHub;
    MainActivity ma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        gitHub= Client.getClient().create(CookWithMeAPI.class);

        button = ((Button)findViewById(R.id.buttonIdioma));

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        showDialog();
                    }});
    }

    private void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.seleccionar_idioma));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {
            String pais;
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                switch(which){
                    case 0:
                        locale = new Locale("en");
                        config.locale =locale;
                        pais="Ingles";
                        break;
                    case 1:
                        locale = new Locale("es");
                        config.locale =locale;
                        pais="España";
                        break;
                }
                gitHub.putJugador(new Jugador(ma.KEY_NOMBRE,ma.KEY_PASSWORD,ma.KEY_EMAIL,pais,14.5,1)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("RECIPE",""+response.code());

                        if(response.code() == 201){
                            Toast.makeText(getApplicationContext(),"Pais actualizado", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        String msg = "Error in retrofit: " + t.toString();
                        Log.d("RECIPE",msg);
                        Toast.makeText(getApplicationContext(),"msg", Toast.LENGTH_SHORT).show();


                    }
                });
                getResources().updateConfiguration(config, null);
                Intent refresh = new Intent(PerfilActivity.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }

        });

        b.show();
    }

}