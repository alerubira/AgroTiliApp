package com.principal.agrotiliapp.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.request.ApiClient;

import java.lang.invoke.MutableCallSite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {
   private MutableLiveData<String>mMensage=new MutableLiveData<>();
   private MutableLiveData<String>mExito=new MutableLiveData<>();
   private Context context;
    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<String>getMExito(){
        return mExito;
    }

    public void corroborarClaves(String claveActual,String claveNueva,String claveRepetida){
        if(claveActual==null||claveActual.isEmpty()||claveNueva==null||claveNueva.isEmpty()
           ||claveRepetida==null||claveRepetida.isEmpty()){
            mMensage.setValue("Debe completar todos los campos");
        } else if (!claveNueva.equals(claveRepetida)) {
            mMensage.setValue("La clave nueva debe ser igual a la clave repetida");

        }else{
            cambiarClave(claveActual,claveNueva);
        }
    }
    private void cambiarClave(String claveActual,String claveNueva){
        String token=ApiClient.leerToken(context);
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<Void> llamada = api.cambiarClave(token,claveActual,claveNueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mExito.postValue("La clave fue cambiada con exito");
                }else{
                    mMensage.postValue(("Error al cambiar la clave: "+response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
               mMensage.postValue("Error en el servidor: "+t.getMessage());
            }
        });
    }

}