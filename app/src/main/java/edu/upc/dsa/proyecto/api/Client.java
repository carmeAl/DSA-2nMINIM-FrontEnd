package edu.upc.dsa.proyecto.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder() //arranco retrofit pero no ejecuto, solo construyo el objeto/clase retrofit
                .baseUrl(CookWithMeAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()).build();

        return retrofit;

    }

}
