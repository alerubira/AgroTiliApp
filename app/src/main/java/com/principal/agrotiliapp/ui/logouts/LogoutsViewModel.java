package com.principal.agrotiliapp.ui.logouts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.agrotiliapp.request.ApiClient;

public class LogoutsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mDeslogueado=new MutableLiveData<>();
    public LogoutsViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMDeslogueado(){
        return mDeslogueado;
    }
    public void desloquearse(){
        ApiClient.borrarToken(getApplication());

        mDeslogueado.setValue("deslogueado");
    }
}