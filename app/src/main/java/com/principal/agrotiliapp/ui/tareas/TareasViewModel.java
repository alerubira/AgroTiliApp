package com.principal.agrotiliapp.ui.tareas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.principal.agrotiliapp.clases.Tareas;
import com.principal.agrotiliapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareasViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mMensage = new MutableLiveData<>();
    private final MutableLiveData<List<Tareas>> mTareas = new MutableLiveData<>();

    public TareasViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMMensage(){
        return mMensage;
    }
    public LiveData<List<Tareas>>getMTareas(){
        return mTareas;
    }
    public void obtenerTareas(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<List<Tareas>> llamada=api.obtenerTareas(token);
        llamada.enqueue(new Callback<List<Tareas>>() {
            @Override
            public void onResponse(Call<List<Tareas>> call, Response<List<Tareas>> response) {
                if(response.isSuccessful()){
                    mTareas.postValue(response.body());
                }else{
                    mMensage.postValue("Error al buscar Tareas; "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Tareas>> call, Throwable t) {
                mMensage.postValue("Error en el servidor: "+t.getMessage());
            }
        });
    }
}