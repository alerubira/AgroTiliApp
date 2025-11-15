package com.principal.agrotiliapp.ui.tareas.empleadosDesocupados;

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

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.databinding.FragmentEmpleadosDesocupadosBinding;

import java.util.List;

public class EmpleadosDesocupadosFragment extends Fragment {

    private EmpleadosDesocupadosViewModel mViewModel;
    private FragmentEmpleadosDesocupadosBinding binding;
    public static EmpleadosDesocupadosFragment newInstance() {
        return new EmpleadosDesocupadosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EmpleadosDesocupadosViewModel.class);
        binding=FragmentEmpleadosDesocupadosBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lista Empleados")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMEmpleados().observe(getViewLifecycleOwner(), new Observer<List<Empleados>>() {
            @Override
            public void onChanged(List<Empleados> empleados) {
                EmpleadosDesocupadosAdapter adapter=new EmpleadosDesocupadosAdapter(empleados,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                RecyclerView rv = binding.rvEmpleadosDesocupados;
                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        mViewModel.obtenerEmpleadosDesocupados();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EmpleadosDesocupadosViewModel.class);
        // TODO: Use the ViewModel
    }

}