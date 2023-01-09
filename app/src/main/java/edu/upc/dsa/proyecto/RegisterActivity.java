package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;
import edu.upc.dsa.proyecto.models.Register;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextNombreReg;
    EditText editTextPasswordReg;
    EditText editTextEmail;
    EditText editTextPais;
    Button guardarBtn;
    Button volverBtn;
    ProgressBar progressBar4;
    CookWithMeAPI gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNombreReg = (EditText)findViewById(R.id.editTextNombreReg);
        editTextPasswordReg = (EditText)findViewById(R.id.editTextPasswordReg);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPais = (EditText)findViewById(R.id.editTextPais);
        guardarBtn = (Button)findViewById(R.id.guardarBtn);
        volverBtn = (Button)findViewById(R.id.volverBtn);
        gitHub= Client.getClient().create(CookWithMeAPI.class);
        progressBar4 = (ProgressBar)findViewById(R.id.progressBar4);
        progressBar4.setVisibility(View.INVISIBLE);

    }
    public void volverBtn(View v){
        //Volver a logIn Activity
        Intent volver= new Intent (RegisterActivity.this, LogInActivity.class);
        startActivity(volver);
    }
    public void guardarBtn(View v) {

        String nombre = editTextNombreReg.getText().toString();
        String password = editTextPasswordReg.getText().toString();
        String email = editTextEmail.getText().toString();
        String pais = editTextPais.getText().toString();
        progressBar4.setVisibility(View.VISIBLE);
        gitHub.register(new Register(nombre,password,email,pais)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("RECIPE",""+response.code());

                if(response.code() == 201){
                    Toast.makeText(getApplicationContext(),"Usuario registrado", Toast.LENGTH_SHORT).show();
                    Intent logIn= new Intent (RegisterActivity.this, LogInActivity.class);
                    startActivity(logIn);
                }

                else if (response.code()==404){
                    Toast.makeText(getApplicationContext(),"Parámetros no válidos, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                    //editTextName.setTextColor();
                    //editTextPassword.setTextColor();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String msg = "Error in retrofit: " + t.toString();
                Log.d("RECIPE",msg);
                Toast.makeText(getApplicationContext(),"msg", Toast.LENGTH_SHORT).show();


            }
        });
        progressBar4.setVisibility(View.GONE);
        Log.d("RECIPE", "end login");

    }
}