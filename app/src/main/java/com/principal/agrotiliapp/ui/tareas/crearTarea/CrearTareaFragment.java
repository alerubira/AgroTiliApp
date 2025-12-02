package com.principal.agrotiliapp.ui.tareas.crearTarea;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.clases.Campos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.clases.Tipos_Tareas;
import com.principal.agrotiliapp.databinding.FragmentCrearTareaBinding;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.ui.tareas.TareasFragment;

import java.util.List;

public class CrearTareaFragment extends Fragment {

    private CrearTareaViewModel mViewModel;
    private FragmentCrearTareaBinding binding;
    private boolean usuarioTocoSpinner = false;

    public static CrearTareaFragment newInstance() {
        return new CrearTareaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(CrearTareaViewModel.class);

        binding=FragmentCrearTareaBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
       // mViewModel.limpiarSharedPreference();

        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Crear Tarea",s);

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
        binding.spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                usuarioTocoSpinner = true;
                return false;
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!usuarioTocoSpinner) {
                    return; // Ignora selección automática
                }
                Tipos_Tareas seleccion = (Tipos_Tareas) parent.getItemAtPosition(position);
                mViewModel.setearMTipoTareaSeleccionada(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        mViewModel.getMTipoTareasSeleccionada().observe(getViewLifecycleOwner(), new Observer<Tipos_Tareas>() {
            @Override
            public void onChanged(Tipos_Tareas tiposTareas) {
                if(tiposTareas.getId_tipo_tarea()>0){
                    binding.tvTareaCrearTarea.setText("Tipo Tarea Seleccionada: "+tiposTareas.getNombre_tipo_tarea());
                }else{
                    binding.tvTareaCrearTarea.setText("");
                }
            }
            });

       binding.tvCampoCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CrearTareaFragment.this)
                        .navigate(R.id.camposFragment);
            }
        });
        mViewModel.getMCampoSeleccionado().observe(getViewLifecycleOwner(), new Observer<Campos>() {
            @Override
            public void onChanged(Campos campos) {
                if(campos.getNombre_campo()==null){
                    binding.tvCampoCrearTarea.setText(getString(R.string.seleccione_un_campo));
                }else{
                    binding.tvCampoCrearTarea.setText("Campo seleccionado: "+ campos.getNombre_campo());
                }
            }
        });

        binding.tvEmpleadoCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CrearTareaFragment.this)
                        .navigate(R.id.empleadosDesocupadosFragment);
            }
        });
        mViewModel.getMEmpleadoseleccionado().observe(getViewLifecycleOwner(), new Observer<Empleados>() {
            @Override
            public void onChanged(Empleados empleados) {
                if(empleados.getApellido()==null){
                    binding.tvEmpleadoCrearTarea.setText(getString(R.string.seleccione_un_operario));
                }else{
                    binding.tvEmpleadoCrearTarea.setText("Operario a cargo de la Tarea: "+empleados.getNombre()+" "+empleados.getApellido());
                }

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
        mViewModel.getMMaquinaSeleccionada().observe(getViewLifecycleOwner(), new Observer<Maquinas_Agrarias>() {
            @Override
            public void onChanged(Maquinas_Agrarias maquinasAgrarias) {
                if(maquinasAgrarias.getPatente()==null){
                    binding.tvMaquinaCrearTarea.setText(getString(R.string.seleccione_una_maquina));
                }else {
                    binding.tvMaquinaCrearTarea.setText("Maquina seleccionada: "+maquinasAgrarias.getPatente());
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

        mViewModel.obtenerTiposTareas();
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewModel.obtenerObjetos();


    }
}