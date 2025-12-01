package com.principal.agrotiliapp.ui.tareas.maquinasAgrarias;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.auxiliares.SingleLiveEvent;
import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.request.ApiErrorHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaquinasAgrariasViewModel extends AndroidViewModel{
    private SingleLiveEvent<String> mMensage = new SingleLiveEvent<>();
    private final MutableLiveData<List<Maquinas_Agrarias>> mMaquinas = new MutableLiveData<>();
    public MaquinasAgrariasViewModel(@NonNull Application application) {
        super(application);
    }
    public SingleLiveEvent<String> getMMensage(){
        return mMensage;
    }
    public LiveData<List<Maquinas_Agrarias>>getMMaquinas(){
        return mMaquinas;
    }
    public void recibirBundle(Bundle bundle){
        if (bundle != null) {
            Tipos_Tareas tipoTarea = (Tipos_Tareas) bundle.getSerializable("tipoTarea");
            obtenerMaquinasDesocupadasPorTarea(tipoTarea.getId_tipo_tarea());

        }else{
            dispararEventoMensage("No se recibio ningun topo de tarea");
        }

    }
   private void obtenerMaquinasDesocupadasPorTarea(int idTipoTarea){


       String token = ApiClient.leerToken(getApplication());
       ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
       Call<List<Maquinas_Agrarias>>llamada=api.obtenerMaquinasDesocupadasPorTarea(token,idTipoTarea);
       llamada.enqueue(new Callback<List<Maquinas_Agrarias>>() {
           @Override
           public void onResponse(Call<List<Maquinas_Agrarias>> call, Response<List<Maquinas_Agrarias>> response) {
               if(response.isSuccessful()){
                   mMaquinas.postValue(response.body());
               }else{
                   dispararEventoMensage(ApiErrorHandler.parseError(response));
               }
           }

           @Override
           public void onFailure(Call<List<Maquinas_Agrarias>> call, Throwable t) {
               dispararEventoMensage(ApiErrorHandler.defaultFailure(t));
           }
       });
   }
   public void dispararEventoMensage(String mensage){
        mMensage.setValue(mensage);
   }
}