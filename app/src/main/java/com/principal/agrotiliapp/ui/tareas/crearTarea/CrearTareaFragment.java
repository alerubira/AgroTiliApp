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
import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
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
        mViewModel.obtenerTiposTareas();
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

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Tipos_Tareas seleccion = (Tipos_Tareas) parent.getItemAtPosition(position);

                mViewModel.setearMTipoTareaSeleccionada(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        mViewModel.getMTipoTareasSeleccionada().observe(getViewLifecycleOwner(), new Observer<Tipos_Tareas>() {
            @Override
            public void onChanged(Tipos_Tareas tiposTareas) {
                //Bloquear el Spinner
                binding.spinner.setEnabled(false);

                //Asegurar que muestre la selección actual
                ArrayAdapter adapter = (ArrayAdapter) binding.spinner.getAdapter();

                int position = adapter.getPosition(tiposTareas);
                binding.spinner.setSelection(position);
            }
            });

        binding.tvCampoCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CrearTareaFragment.this)
                        .navigate(R.id.camposFragment);
            }
        });
        binding.tvEmpleadoCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CrearTareaFragment.this)
                        .navigate(R.id.empleadosDesocupadosFragment);
            }
        });
        binding.tvMaquinaCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mViewModel.cooroborarTipoTarea();
            }
        });
        mViewModel.getMHayTarea().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mViewModel.setearMHayTarea();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("tipoTarea",mViewModel.getMTipoTareasSeleccionada().getValue());
                    NavHostFragment.findNavController(CrearTareaFragment.this)
                            .navigate(R.id.maquinasAgrariasFragment,bundle);
                }
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Crear Tarea")
                        .setMessage("Seguro de crear Tarea")
                        .setNegativeButton("No", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .setPositiveButton("Si",(dialog, which) -> {
                            mViewModel.cooroborarDatosTarea();
                        })
                        .show();

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

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.llenartv();
        Campos campo=mViewModel.getMCampoSeleccionado().getValue();
        Empleados empleado=mViewModel.getMEmpleadoseleccionado().getValue();
        Maquinas_Agrarias maquina=mViewModel.getMMaquinaSeleccionada().getValue();
        if(campo!=null){
            binding.tvCampoCrearTarea.setText(campo.getNombre_campo());
        }
        if(empleado!=null){
            binding.tvEmpleadoCrearTarea.setText(empleado.getApellido());
        }
        if(maquina!=null){
            binding.tvMaquinaCrearTarea.setText(maquina.getPatente());
        }

    }
}