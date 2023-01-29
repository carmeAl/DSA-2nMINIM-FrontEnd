package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Denuncia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DenunciaActivity extends AppCompatActivity {

    EditText nombreText, fechaText, mensajeText;
    Button enviarBtn;
    String nombre,fecha,mensaje;
    CookWithMeAPI gitHub;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        nombreText = (EditText)findViewById(R.id.nombreText);
        fechaText = (EditText)findViewById(R.id.fechaText);
        mensajeText = (EditText)findViewById(R.id.mensajeText);
        enviarBtn = (Button)findViewById(R.id.enviarBtn);
        fechaText.setText(date);
        gitHub= Client.getClient().create(CookWithMeAPI.class);
    }
    public void denunciaBtn(View v) {

        nombre = nombreText.getText().toString();
        mensaje = mensajeText.getText().toString();
        fecha = date;
        gitHub.postDenuncia(new Denuncia(fecha,nombre,mensaje)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DENUNCIA",""+response.code());

                if(response.code() == 201){
                    Intent main= new Intent (DenunciaActivity.this, MainActivity.class);
                    startActivity(main);
                    Toast.makeText(getApplicationContext(),"Enviado correctamente", Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==500){
                    Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String msg = "Error in retrofit: " + t.toString();
                Log.d("DENUNCIA",msg);
                Toast.makeText(getApplicationContext(),"msg", Toast.LENGTH_SHORT).show();
            }

        });

    }
}