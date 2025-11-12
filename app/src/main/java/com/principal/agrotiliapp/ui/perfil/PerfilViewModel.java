package com.principal.agrotiliapp.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
      private MutableLiveData<Empleados> mEmpleado=new MutableLiveData<>();
      private MutableLiveData<String> mMensage=new MutableLiveData<>();
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
        if(textoBoton.equals("@string/editar_perfil")){
            //hacer un mutable y en el fragment cmabiar los estados del editext necesario y el texto del bon
        }
        if(textoBoton.equals("@string/modificar_perfil")){
            //haver un mutable par captar los cambios,cambiar los estados y el texto del botony efectuar el cambio
        }
    }
    public void modificarPerfil(String nombre,String apellido){
        //hacer el resteo con el empleado del mutable,verificar que recive el metodo en el seridor
    }
}