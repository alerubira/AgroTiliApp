package com.principal.agrotiliapp.ui.perfil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv =new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mv.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Perfil")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mv.getMEmpleado().observe(getViewLifecycleOwner(), new Observer<Empleados>() {
            @Override
            public void onChanged(Empleados empleado) {
                binding.edtIdEmpleado.setText("Codigo Interno: "+empleado.getId_empleado());
                binding.edtNombre.setText("Nombre: "+empleado.getNombre());
                binding.edtApellido.setText("Apellido: "+empleado.getApellido());
                binding.edtRole.setText("Rol dentro de la Empresa: "+empleado.getNombre_role());
                binding.edtEmail.setText("Email: "+empleado.getEmail());
                binding.edtFechaIngreso.setText("Fecha de Ingreso: "+empleado.getFecha_ingreso());
            }
        });
        binding.btnModificarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView())
                        .navigate(R.id.cambiarClaveFragment);

            }
        });
        binding.btnEditarModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.editarModificar(binding.btnEditarModificar.getText().toString());
            }
        });
        mv.obtenrPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}