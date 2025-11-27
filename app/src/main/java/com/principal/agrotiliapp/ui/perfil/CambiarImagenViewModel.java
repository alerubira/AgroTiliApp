package com.principal.agrotiliapp.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.request.ApiErrorHandler;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            mUri.setValue(uri);
           cambiarImagen();

        }
    }
    private void cambiarImagen(){
        //Convertir en base a la uri
        byte[] imagen = transformarImagen();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        //Armar multipart
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

        String token= ApiClient.leerToken(context);
        ApiClient.AgroTiliService api=ApiClient.getApiAgroTili();
       Call<Empleados>llamada=api.cambiarImagen(token,imagenPart);
       llamada.enqueue(new Callback<Empleados>() {
           @Override
           public void onResponse(Call<Empleados> call, Response<Empleados> response) {
               if(response.isSuccessful()){
                  mEmpleado.postValue(response.body());
                  // mUrlImagen.postValue(response.body().getImagen_perfil());
               }else{
                   mMensage.postValue(ApiErrorHandler.parseError(response));
               }
           }

           @Override
           public void onFailure(Call<Empleados> call, Throwable t) {
               mMensage.postValue(ApiErrorHandler.defaultFailure(t));
           }
       });

    }
    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue(); //lo puedo usar porque estoy en viewmodel
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);//Crea un canal para conectarse a un archivo
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {

            mMensage.postValue("No a seleccionado ninguna foto");
            return new byte[]{};
        }
    }



}