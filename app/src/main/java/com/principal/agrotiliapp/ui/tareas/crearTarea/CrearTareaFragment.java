package com.principal.agrotiliapp.ui.tareas.crearTarea;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.databinding.FragmentCrearTareaBinding;

public class CrearTareaFragment extends Fragment {

    private CrearTareaViewModel mViewModel;
    private FragmentCrearTareaBinding binding;
    public static CrearTareaFragment newInstance() {
        return new CrearTareaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CrearTareaViewModel.class);
        binding=FragmentCrearTareaBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearTareaViewModel.class);
        // TODO: Use the ViewModel
    }

}