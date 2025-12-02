package com.principal.agrotiliapp.ui.tareas.crearTarea;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.auxiliares.SingleLiveEvent;
import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.request.ApiErrorHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearTareaViewModel extends AndroidViewModel {
    private SingleLiveEvent<String> mMensage=new SingleLiveEvent<>();
    private MutableLiveData <List<Tipos_Tareas>>mTiposTareas=new MutableLiveData<>();
    private MutableLiveData<Campos> mCampoSelecionado =new MutableLiveData<>();
    private MutableLiveData<Maquinas_Agrarias> mMaquinaSeleccionada =new MutableLiveData<>();
    private MutableLiveData<Empleados> mEmpleadoSeleccionado =new MutableLiveData<>();
    private MutableLiveData<Boolean>mHayTarea=new MutableLiveData<>();
    private MutableLiveData<Tipos_Tareas> mTipoTareasSeleccionada=new MutableLiveData<>();
    private Context context;
    public CrearTareaViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public SingleLiveEvent<String>getMMensage(){
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

    public LiveData<Tipos_Tareas> getMTipoTareasSeleccionada() {
        return mTipoTareasSeleccionada;
    }
    public LiveData<Boolean>getMHayTarea(){
        return mHayTarea;
    }

    public void limpiarSharedPreference(){
        ApiClient.borrarObjeto(context,"campo");
        ApiClient.borrarObjeto(context,"empleado");
        ApiClient.borrarObjeto(context,"maquinaAgraria");
    }
    public void obtenerTiposTareas(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<List<Tipos_Tareas>>llamada=api.obtenerTiposTareas(token);
        llamada.enqueue(new Callback<List<Tipos_Tareas>>() {
            @Override
            public void onResponse(Call<List<Tipos_Tareas>> call, Response<List<Tipos_Tareas>> response) {
                if(response.isSuccessful()){
                    List<Tipos_Tareas>lista=new ArrayList<>();
                    Tipos_Tareas tarea=new Tipos_Tareas(0,"Seleccione un tipo de tarea");
                    lista.add(tarea);
                    lista.addAll(response.body());
                    mTiposTareas.postValue(lista);
                }else{
                    mMensage.postValue(ApiErrorHandler.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Tipos_Tareas>> call, Throwable t) {
                mMensage.postValue(ApiErrorHandler.defaultFailure(t));
            }
        });
    }
    public void obtenerObjetos(){
        Campos campo= ApiClient.leerObjeto(context,"campo",Campos.class);
        if(campo!=null){
            mCampoSelecionado.setValue(campo);
        }
        Empleados empleado= ApiClient.leerObjeto(context,"empleado",Empleados.class);
        if(empleado!=null){
            mEmpleadoSeleccionado.setValue(empleado);
        }

        Maquinas_Agrarias maquina= ApiClient.leerObjeto(context,"maquinaAgraria",Maquinas_Agrarias.class);
        if(maquina!=null){
           mMaquinaSeleccionada.setValue(maquina);

        }

    }
    public void cooroborarTipoTarea(){

        if (mTipoTareasSeleccionada.getValue() != null && mTipoTareasSeleccionada.getValue().getId_tipo_tarea() > 0) {
            mHayTarea.setValue(true);
        }else{
            mMensage.setValue("Debe seleccionar el tipo de tarea para buscar la Maquina");
        }
    }
    public void setearMHayTarea(){
        mHayTarea.setValue(false);
    }

    public void setearMTipoTareaSeleccionada(Tipos_Tareas tipo){
        mTipoTareasSeleccionada.setValue(tipo);
    }
    public void cooroborarDatosTarea(){
        Empleados empleado= mEmpleadoSeleccionado.getValue();
        if(empleado==null|| empleado.getApellido()==null){
            mMensage.setValue("Debe seleccionar un Empleado para la tarea");
            return;
        }
        Campos campo= mCampoSelecionado.getValue();
        if(campo==null|| campo.getNombre_campo()==null){
            mMensage.setValue("debe seleccionar un Campo para la tarea");
            return;
        }
        Maquinas_Agrarias maquina= mMaquinaSeleccionada.getValue();
        if(maquina==null|| maquina.getPatente()==null){
            mMensage.setValue("Debe seleccionar una Maquina para la tarea");
            return;
        }
        if(mTipoTareasSeleccionada.getValue()==null){
            mMensage.setValue("Debe seleccionar un tipo de Tatrea");
            return;
        }
        if(maquina.getId_tipo_tarea()!=mTipoTareasSeleccionada.getValue().getId_tipo_tarea()){
            mMensage.setValue("La maquina seleccionada no es aptata para la Tarea");
            return;
        }
        crearTara(mTipoTareasSeleccionada.getValue().getId_tipo_tarea(),maquina.getId_maquina_agraria(), empleado.getId_empleado(), campo.getId_campo());
    }
    private void crearTara(int idTipoTraea,int idMaquina,int idEmpleado,int idCampo){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.AgroTiliService api = ApiClient.getApiAgroTili();
        Call<Void>llamada=api.crearTarea(token,idTipoTraea,idCampo,idMaquina,idEmpleado);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mMensage.postValue("Tarea Generada con exito");
                    limpiarSharedPreference();
                    setearMutables();
                }else{
                    mMensage.postValue(ApiErrorHandler.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mMensage.postValue(ApiErrorHandler.defaultFailure(t));
            }
        });

    }
    private void setearMutables(){
        mTipoTareasSeleccionada.setValue(new Tipos_Tareas());
        mMaquinaSeleccionada.setValue(new Maquinas_Agrarias());
        mCampoSelecionado.setValue(new Campos());
        mEmpleadoSeleccionado.setValue(new Empleados());
    }

}