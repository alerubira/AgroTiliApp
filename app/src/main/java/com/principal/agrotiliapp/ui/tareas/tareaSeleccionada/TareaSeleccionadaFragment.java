package com.principal.agrotiliapp.ui.tareas.tareaSeleccionada;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Tareas;
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
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Tarea Seleccionada")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMTarea().observe(getViewLifecycleOwner(), new Observer<Tareas>() {
            @Override
            public void onChanged(Tareas tarea) {
                binding.tvIdTarea.setText("@string/codigo_interno"+" :"+tarea.getId_tarea());
                binding.tvCampo.setText("Nombre del Campo: "+tarea.getCampo().getNombre_campo());
                binding.tvOperario.setText("Operario: "+tarea.getEmpleado().getNombre()+" "+tarea.getEmpleado().getApellido());
                binding.tvMaquina.setText("Maquina: "+tarea.getMaquina_Agraria().getPatente());
                binding.tvFechaInicio.setText("Fecha de Inicio: "+tarea.getFecha_inicio());
                if(tarea.getFecha_fin()==null){
                    binding.tvFechaFin.setText("Tarea no finalizada");
                }else{
                    binding.tvFechaFin.setText("Fecha de finalizacion: "+tarea.getFecha_fin());
                }
                if(tarea.getObservaciones()!=null){
                    binding.edtObservaciones.setText("Observaciones: "+tarea.getObservaciones());
                }
            }
        });
        binding.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obs=binding.edtObservaciones.getText().toString();
                mViewModel.corroborarDatos(obs);
            }
        });
       // Recuperar el bundle
        Bundle bundle = getArguments();
        mViewModel.recibirBundle(bundle);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TareaSeleccionadaViewModel.class);
        // TODO: Use the ViewModel
    }

}