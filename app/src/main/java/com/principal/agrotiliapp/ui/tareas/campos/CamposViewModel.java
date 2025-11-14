package com.principal.agrotiliapp.ui.tareas.campos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CamposViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mMensage = new MutableLiveData<>();
    private final MutableLiveData<List<Campos>> mCampos = new MutableLiveData<>();
    public CamposViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<List<Campos>>getMCampos(){
        return mCampos;
    }
    public void obtenerCamposPorCapataz(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
       Call<List<Campos>> llamada=api.obtenerCamposPorCapataz(token);
       llamada.enqueue(new Callback<List<Campos>>() {
           @Override
           public void onResponse(Call<List<Campos>> call, Response<List<Campos>> response) {
               if(response.isSuccessful()){
                   mCampos.postValue(response.body());
               }else{
                   mMensage.postValue("Error al buscar Campos; "+response.message());
               }
           }

           @Override
           public void onFailure(Call<List<Campos>> call, Throwable t) {
                  mMensage.postValue("Error en el servidor: "+t.getMessage());
           }
       });
    }
}