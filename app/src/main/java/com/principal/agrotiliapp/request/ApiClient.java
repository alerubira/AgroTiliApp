package com.principal.agrotiliapp.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.principal.agrotiliapp.clases.Empleados;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    // IP de tu PC + puerto donde corre la API HTTP
    //public static final String URLBASE = "http://192.168.1.108:5164/";
    //public static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";//virtual

   // public static final String URLBASE = "http://192.168.1.108:5294/";//en el negocio
    public static final String URLBASE = "http://192.168.1.104:5294/";//en casa



    public static AgroTiliService getApiAgroTili() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(URLBASE)

                .addConverterFactory(ScalarsConverterFactory.create())

                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();

        return retrofit.create(AgroTiliService.class);
    }
    public static void guardarToken(Context context, String token){
        SharedPreferences sp=context.getSharedPreferences("token.xml",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("token","Bearer "+ token);
        editor.apply();
    }
    public static String leerToken(Context context){
        SharedPreferences sp=context.getSharedPreferences("token.xml",Context.MODE_PRIVATE);
        return sp.getString("token",null);
    }
    public static void borrarToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token"); // elimina solo la clave "token"
        editor.apply();
    }
    public interface AgroTiliService{
        @FormUrlEncoded
        @POST("api/Empleados/login")
        Call<String> login(@Field("Usuario") String u, @Field("Clave") String c);

        @GET("api/Empleados")
        Call<Empleados>obtenerEmpleado(@Header("Authorization")String token);

        @PUT("api/Empleados/actualizar")
        Call<Empleados>actualizarEmpleado(@Header("Authorization")String token,@Body Empleados e);

        @FormUrlEncoded
        @PUT("api/Empleados/cambiarClave")
        Call<Void>cambiarClave(@Header("Authorization")String token,
                               @Field("claveActual")String claveActual,
                               @Field("claveNueva")String claveNueva);

        @FormUrlEncoded
        @POST("api/Empleados/EnviarMail")
        Call<String>enviarEmail(
                               @Field("Usuario")String email);

        @FormUrlEncoded
        @PUT("api/Empleados/RecuperarClave")
        Call<String>recuperarClave(
                               @Field("email")String email,
                               @Field("claveEmail")String claveEmail,
                               @Field("claveNueva")String claveNueva);
    }
}
