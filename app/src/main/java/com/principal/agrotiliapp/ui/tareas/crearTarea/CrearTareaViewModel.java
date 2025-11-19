package com.principal.agrotiliapp.ui.tareas.crearTarea;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearTareaViewModel extends AndroidViewModel {
    private MutableLiveData<String>mMensage=new MutableLiveData<>();
    private MutableLiveData <List<Tipos_Tareas>>mTiposTareas=new MutableLiveData<>();
    private MutableLiveData<Campos> mCampoSelecionado =new MutableLiveData<>();
    private MutableLiveData<Maquinas_Agrarias> mMaquinaSeleccionada =new MutableLiveData<>();
    private MutableLiveData<Empleados> mEmpleadoSeleccionado =new MutableLiveData<>();
    private Tipos_Tareas tipoTareasSeleccionada;
    public CrearTareaViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<List<Tipos_Tareas>>getMTiposTareas(){
        return mTiposTareas;
    }
    public LiveData<Campos>getMCampoSeleccionado(){
        return mCampoSelecionado;
    }
    public LiveData<Maquinas_Agrarias>getMMaquinaSeleccionada(){
        return mMaquinaSeleccionada;
    }
    public LiveData<Empleados>getMEmpleadoseleccionado(){
        return mEmpleadoSeleccionado;
    }

    public Tipos_Tareas getTipoTareasSeleccionada() {
        return tipoTareasSeleccionada;
    }

    public void setTipoTareasSeleccionada(Tipos_Tareas tipoTareasSeleccionada) {
        this.tipoTareasSeleccionada = tipoTareasSeleccionada;
    }

    public void obtenerTiposTareas(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<List<Tipos_Tareas>>llamada=api.obtenerTiposTareas(token);
        llamada.enqueue(new Callback<List<Tipos_Tareas>>() {
            @Override
            public void onResponse(Call<List<Tipos_Tareas>> call, Response<List<Tipos_Tareas>> response) {
                if(response.isSuccessful()){
                    mTiposTareas.postValue(response.body());
                }else{
                    mMensage.postValue("Error al buscar Tipos de Tareas; "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Tipos_Tareas>> call, Throwable t) {
                mMensage.postValue("Error en el servidor: "+t.getMessage());
            }
        });
    }
}