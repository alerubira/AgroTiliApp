package com.principal.agrotiliapp.ui.tareas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.principal.agrotiliapp.clases.Tareas;

import java.util.List;

public class TareasViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mMensage = new MutableLiveData<>();
    private final MutableLiveData<List<Tareas>> mTareas = new MutableLiveData<>();

    public TareasViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMMensage(){
        return mMensage;
    }
    public LiveData<List<Tareas>>getMEmpleados(){
        return mTareas;
    }
}