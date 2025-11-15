package com.principal.agrotiliapp.ui.tareas.empleadosDesocupados;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpleadosDesocupadosViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mMensage = new MutableLiveData<>();
    private final MutableLiveData<List<Empleados>> mEmpleados = new MutableLiveData<>();
    public EmpleadosDesocupadosViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMMensage(){
        return mMensage;
    }
    public LiveData<List<Empleados>>getMEmpleados(){
        return mEmpleados;
    }
    public void obtenerEmpleadosDesocupados(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<List<Empleados>> llamada=api.obtenerOperariosDesocupados(token);
        llamada.enqueue(new Callback<List<Empleados>>() {
            @Override
            public void onResponse(Call<List<Empleados>> call, Response<List<Empleados>> response) {
                if(response.isSuccessful()){
                    mEmpleados.postValue(response.body());
                }else{
                    mMensage.postValue("Error al buscar Empleados; "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Empleados>> call, Throwable t) {
                mMensage.postValue("Error en el servidor: "+t.getMessage());
            }
        });
    }
}