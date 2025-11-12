package com.principal.agrotiliapp.ui.tareas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.principal.agrotiliapp.databinding.FragmentTareasBinding;

public class TareasFragment extends Fragment {

    private FragmentTareasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TareasViewModel tareasViewModel =
                new ViewModelProvider(this).get(TareasViewModel.class);

        binding = FragmentTareasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}