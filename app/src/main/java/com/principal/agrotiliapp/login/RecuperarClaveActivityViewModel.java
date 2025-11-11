package com.principal.agrotiliapp.login;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarClaveActivityViewModel extends AndroidViewModel {
    private String email;
    private MutableLiveData<String> mMensage=new MutableLiveData<>();
    private MutableLiveData<String> mExito=new MutableLiveData<>();
    public RecuperarClaveActivityViewModel(@NonNull Application application) {
        super(application);

    }
    LiveData<String>getMMensage(){
        return mMensage;
    }
    LiveData<String>getMExito() {
        return mExito;
    }
    public void recibirIntent(Intent intent){
        email=intent.getStringExtra("email");
    }
    private void resetearClave(String claveEmail,String claveNueva){

        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<String> llamada = api.recuperarClave(email,claveEmail,claveNueva);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mExito.setValue("rr");
                }else{
                    mMensage.postValue("Error al resetear la clave: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               mMensage.postValue("Error del servidor: "+t.getMessage());
            }
        });
    }
    public void corroborarDatos(String claveEmail,String claveNueva,String claverepetida){
        if(claveNueva==null||claveNueva.isEmpty()||claveEmail==null||claveEmail.isEmpty()
        ||claverepetida==null||claverepetida.isEmpty()){
            mMensage.setValue("Los datos son obligatorios");
        }else if(claveNueva!=claverepetida){

               mMensage.setValue("La clave nueva debe ser igual a la clave repetida");
            }else{
                resetearClave(claveEmail,claveNueva);
            }
        }
    }

