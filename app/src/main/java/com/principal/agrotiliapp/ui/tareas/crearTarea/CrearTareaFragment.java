package com.principal.agrotiliapp.ui.tareas.crearTarea;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.databinding.FragmentCrearTareaBinding;
import com.principal.agrotiliapp.ui.tareas.TareasFragment;

import java.util.List;

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
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                abrirDialogo(s);
            }
        });
        mViewModel.getMTiposTareas().observe(getViewLifecycleOwner(), new Observer<List<Tipos_Tareas>>() {
            @Override
            public void onChanged(List<Tipos_Tareas> tiposTareas) {
                ArrayAdapter<Tipos_Tareas> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        tiposTareas
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.spinner.setAdapter(adapter);
            }
        });
        mViewModel.obtenerTiposTareas();
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Tipos_Tareas seleccion = (Tipos_Tareas) parent.getItemAtPosition(position);

                mViewModel.setTipoTareasSeleccionada(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        binding.tvCampoCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CrearTareaFragment.this)
                        .navigate(R.id.camposFragment);
            }
        });

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearTareaViewModel.class);
        // TODO: Use the ViewModel
    }
    private void abrirDialogo(String s){
        new AlertDialog.Builder(getContext())
                .setTitle("Crear Tarea")
                .setMessage(s)

                .setNegativeButton("Cerrar", (dialog, which) -> {
                    // Solo cierra el diálogo
                    dialog.dismiss();
                })
                .show();
    }

}