package com.principal.agrotiliapp.ui.tareas.tareaSeleccionada;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.clases.Tareas;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.request.ApiErrorHandler;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaSeleccionadaViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mMensage = new MutableLiveData<>();
    private final MutableLiveData<Tareas> mTarea = new MutableLiveData<>();
    public TareaSeleccionadaViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMMensage(){
        return mMensage;
    }
    public LiveData<Tareas>getMTarea(){
        return mTarea;
    }
    public void recibirBundle(Bundle bundle){
        if (bundle != null) {
            Tareas tarea = (Tareas) bundle.getSerializable("tarea");
            mTarea.setValue(tarea);

        }else{
            mMensage.setValue("No se recibio ninguna tarea");
        }
    }
    public void corroborarDatos(String obsrvaciones){
        if(obsrvaciones==null||obsrvaciones.isEmpty()){
            mMensage.setValue("Debe agregar Observaciones");
        }else if(mTarea.getValue()==null){
            mMensage.setValue("No hay tarea seleccionada");
        }else{
            finalizarTarea(obsrvaciones);
        }
    }
    private void finalizarTarea(String observaciones){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<Void> llamada=api.finalizarTarea(token,mTarea.getValue().getId_tarea(),observaciones);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mMensage.postValue("La tarea fue finalizada con exito");
                } else {
                    mMensage.postValue(ApiErrorHandler.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    mMensage.postValue(ApiErrorHandler.defaultFailure(t));
            }
        });
    }
}