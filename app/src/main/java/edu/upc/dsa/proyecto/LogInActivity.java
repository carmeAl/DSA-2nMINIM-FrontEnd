package edu.upc.dsa.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.dsa.proyecto.api.Client;
import edu.upc.dsa.proyecto.api.CookWithMeAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity{

    TextView textViewRegister,textViewInicioSesion;
    EditText editTextName, editTextPassword;

    Button registerBtn, logInBtn;

    ProgressBar progressBar3;

    CookWithMeAPI gitHub;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";

    public void logInBtn(View v) {

        String user = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();
        progressBar3.setVisibility(View.VISIBLE);
        gitHub.logIn(new edu.upc.dsa.proyecto.models.LogIn(user,password)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("RECIPE",""+response.code());

                if(response.code() == 201){
                    editor = sharedPref.edit();
                    editor.putString(KEY_NOMBRE, user);
                    editor.apply();
                    Intent main= new Intent (LogInActivity.this, MainActivity.class);
                    startActivity(main);
                    Toast.makeText(getApplicationContext(),"Log In correcto", Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==404){
                    Toast.makeText(getApplicationContext(),"Contraseña/usuario incorrectos", Toast.LENGTH_SHORT).show();
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
        progressBar3.setVisibility(View.GONE);
        Log.d("RECIPE", "end login");

    }

    public void registerBtn (View v){
        Intent intent= new Intent (LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewInicioSesion = (TextView)findViewById(R.id.textViewInicioSesion);
        textViewRegister = (TextView)findViewById(R.id.textViewRegister);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        registerBtn = (Button)findViewById(R.id.registerBtn);
        logInBtn=(Button)findViewById(R.id.logInBtn);

        gitHub= Client.getClient().create(CookWithMeAPI.class);
        sharedPref = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.INVISIBLE);


    }


}

