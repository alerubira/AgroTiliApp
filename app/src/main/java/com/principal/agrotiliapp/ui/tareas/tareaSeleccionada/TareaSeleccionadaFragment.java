package com.principal.agrotiliapp.ui.tareas.tareaSeleccionada;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.databinding.FragmentTareaSeleccionadaBinding;

public class TareaSeleccionadaFragment extends Fragment {

    private TareaSeleccionadaViewModel mViewModel;
    private FragmentTareaSeleccionadaBinding binding;

    public static TareaSeleccionadaFragment newInstance() {
        return new TareaSeleccionadaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(TareaSeleccionadaViewModel.class);
        binding=FragmentTareaSeleccionadaBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(TareaSeleccionadaViewModel.class);
        View root=binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TareaSeleccionadaViewModel.class);
        // TODO: Use the ViewModel
    }

}