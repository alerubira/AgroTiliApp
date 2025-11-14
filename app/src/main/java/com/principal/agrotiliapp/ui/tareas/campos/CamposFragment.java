package com.principal.agrotiliapp.ui.tareas.campos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.databinding.FragmentCamposBinding;

import java.util.List;

public class CamposFragment extends Fragment {

    private CamposViewModel mViewModel;
    private FragmentCamposBinding binding;

    public static CamposFragment newInstance() {
        return new CamposFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CamposViewModel.class);
        binding = FragmentCamposBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lista Campos")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMCampos().observe(getViewLifecycleOwner(), new Observer<List<Campos>>() {
            @Override
            public void onChanged(List<Campos> campos) {
                CamposAdapter adapter=new CamposAdapter(campos,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                RecyclerView rv = binding.rvCampos;
                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        mViewModel.obtenerCamposPorCapataz();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CamposViewModel.class);
        // TODO: Use the ViewModel
    }

}