package edu.upc.dsa.proyecto;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import edu.upc.dsa.proyecto.models.LogIn;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    String nombre;
    private static int SPLASH_TIEMPO=2000;
    private static final String SHARED_PREF_NAME = "datosLogIn";
    private static final String KEY_NOMBRE = "nombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPref = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        nombre = sharedPref.getString(KEY_NOMBRE,null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (nombre!=null){
                    Toast.makeText(getApplicationContext(),"USUARIO ACTIVO", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"USUARIO INACTIVO", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(SplashScreenActivity.this, LogInActivity.class);
                    startActivity(intent2);
                }
            }
        }, SPLASH_TIEMPO);
    }

}