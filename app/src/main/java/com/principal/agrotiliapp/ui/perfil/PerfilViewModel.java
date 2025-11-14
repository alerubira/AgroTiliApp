package com.principal.agrotiliapp.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
      private MutableLiveData<Empleados> mEmpleado=new MutableLiveData<>();
      private MutableLiveData<String> mMensage=new MutableLiveData<>();
      private MutableLiveData<String>mEditar=new MutableLiveData<>();
      private MutableLiveData<String>mModificar=new MutableLiveData<>();
      private MutableLiveData<String>mModificado=new MutableLiveData<>();
      private Context context;
      private Empleados empleado;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<Empleados>getMEmpleado(){
        return mEmpleado;
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<String>getMEditar(){
        return mEditar;
    }
    public LiveData<String>getMModificar(){
        return mModificar;
    }
    public LiveData<String>getMModificado(){
        return mModificado;
    }
    public void obtenrPerfil(){
        String token=ApiClient.leerToken(context);
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<Empleados> llamada = api.obtenerEmpleado(token);
        llamada.enqueue(new Callback<Empleados>() {
            @Override
            public void onResponse(Call<Empleados> call, Response<Empleados> response) {
                if(response.isSuccessful()){
                     empleado=response.body();
                     mEmpleado.postValue(empleado);
                }else{
                    mMensage.postValue("Error al buscar el Empleado: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Empleados> call, Throwable t) {
               mMensage.postValue("Error en el servidor: "+t.getMessage());
            }
        });
    }
    public void editarModificar(String textoBoton){
        if(textoBoton.equals(context.getString(R.string.editar_perfil))){
            mEditar.setValue("");
        }else if(textoBoton.equals(context.getString(R.string.modificar_perfil))){
            mModificar.setValue("");
        }else{
            mMensage.setValue("No se puede realizar la accion");
        }
    }
    public void corroborarCampos(String nombre,String apellido){
        if(nombre==null||nombre.isEmpty()||apellido==null||apellido.isEmpty()){
            mMensage.setValue("El nombre y el apellido son obligarorios");
        }else{
            modificarPerfil( nombre,apellido);
        }
    }
    private void modificarPerfil(String nombre,String apellido){
        Empleados empleado=mEmpleado.getValue();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        String token=ApiClient.leerToken(context);
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<Empleados> llamada = api.actualizarEmpleado(token,empleado);
        llamada.enqueue(new Callback<Empleados>() {
            @Override
            public void onResponse(Call<Empleados> call, Response<Empleados> response) {
                if(response.isSuccessful()){
                    mModificado.postValue("El perfil del empleado fue modificado con exito");
                }else{
                    mMensage.postValue("Error al modificar el perfil del empleado:"+response.message());
                }
            }

            @Override
            public void onFailure(Call<Empleados> call, Throwable t) {
                     mMensage.postValue("Erroe en el servidor: "+t.getMessage());
            }
        });
    }
}