package com.principal.agrotiliapp.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;

import retrofit2.Call;


public class CambiarImagenViewModel extends AndroidViewModel {
    private MutableLiveData<String>mMensage=new MutableLiveData<>();
    private MutableLiveData<String>mUrlImagen=new MutableLiveData<>();
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private MutableLiveData<Empleados>mEmpleado=new MutableLiveData<>();
    private Context context;
    public CambiarImagenViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<String>getMUrlImagen(){
        return mUrlImagen;
    }
    public LiveData<Uri> getMuri(){
        return mUri;
    }
    public LiveData<Empleados>getMEmpleado(){
        return mEmpleado;
    }
    public void recibirBumdle(Bundle bundle){
        if (bundle != null) {
            String urlImagen= bundle.getString("urlImagen");
            mUrlImagen.setValue(urlImagen);

        }else{
            mMensage.setValue("No se recibio ninguna imagen");
        }
    }
    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            //mUri.setValue(uri);
            cambiarImagen(uri);
        }
    }
    private void cambiarImagen(Uri uri){
        String token= ApiClient.leerToken(context);
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
        //Convertir en base a la uri

    }


}