package com.principal.agrotiliapp.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivtyViewModel extends AndroidViewModel {
    private MutableLiveData<String> mLogueado=new MutableLiveData<>();
    private MutableLiveData<String> mNoLogueado=new MutableLiveData<>();
    private MutableLiveData<String> mReseteo=new MutableLiveData<>();
    private Context context;
    public LoginActivtyViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String> getMLogueado(){
        return mLogueado;
    }
    public LiveData<String> getMNoLogueado(){
        return mNoLogueado;
    }
    public LiveData<String> getMReseteo(){
        return mReseteo;
    }
    public void loguear(String mail,String clave){
        if (verificarCampos(mail, clave)) {
            loguearse(mail,clave);

        }else{
            mNoLogueado.setValue("falta el usuario o la clave");
        }

    }
    private boolean verificarCampos(String usuario,String clave){
        boolean bandera=true;
        if(usuario==null||usuario.isEmpty()||clave==null||clave.isEmpty()){
            bandera=false;
        }
        return bandera;
    }
    private void loguearse(String mail,String clave){
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<String> llamada = api.login(mail, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                String token= response.body();
                ApiClient.guardarToken(context, token);
                mLogueado.postValue("logueado");
                } else{
                    mNoLogueado.postValue("Error al loguearse: "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mNoLogueado.postValue("Eeror interno del servidor: "+t.getMessage());
            }
        });
    }
    public void resetear(String email){
        if(email==null|| email.isEmpty()){
            mNoLogueado.setValue("Debe colocar el meil");
        }else{
            enviaEmail(email);

        }
    }
    private void enviaEmail(String email){
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        Call<String> llamada = api.enviarEmail(email);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                     mReseteo.setValue(email);
                }else{
                    mNoLogueado.postValue("Error al enviar el mail: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mNoLogueado.postValue("Error del servidor: "+t.getMessage());
            }
        });
    }
}
