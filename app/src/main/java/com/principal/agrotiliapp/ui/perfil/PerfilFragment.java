package com.principal.agrotiliapp.ui.perfil;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;


import com.bumptech.glide.Glide;
import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.databinding.FragmentPerfilBinding;
import com.principal.agrotiliapp.request.ApiClient;

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
                ApiDialogos.abrirDialogoSimple(getContext(),"Perfil",s);
            }
        });
        mv.getMEmpleado().observe(getViewLifecycleOwner(), new Observer<Empleados>() {
            @Override
            public void onChanged(Empleados empleado) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Perfil",empleado.getImagen_perfil());
                binding.edtIdEmpleado.setText("Codigo Interno: "+empleado.getId_empleado());
                binding.edtNombre.setText(empleado.getNombre());
                binding.edtApellido.setText(empleado.getApellido());
                binding.edtRole.setText("Rol dentro de la Empresa: "+empleado.getNombre_role());
                binding.edtEmail.setText("Email: "+empleado.getEmail());
                binding.edtFechaIngreso.setText("Fecha de Ingreso: "+empleado.getFecha_ingreso());
                Glide.with(getContext())
                        .load(ApiClient.URLBASE+empleado.getImagen_perfil())
                        .error("null")
                        .into(binding.imgImagenPerfil);
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
        mv.getMEditar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               binding.btnEditarModificar.setText(getString(R.string.modificar_perfil));
               binding.edtNombre.setEnabled(true);
               binding.edtApellido.setEnabled(true);
            }
        });
        mv.getMModificar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String nombre=binding.edtNombre.getText().toString();
                String apellido=binding.edtApellido.getText().toString();
                mv.corroborarCampos(nombre,apellido);
            }
        });
        mv.getMModificado().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditarModificar.setText(getText(R.string.editar_perfil));
                binding.edtNombre.setEnabled(false);
                binding.edtApellido.setEnabled(false);
                ApiDialogos.abrirDialogoSimple(getContext(),"Perfil",s);

            }
        });
        mv.obtenrPerfil();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnEditarModificar.setText(getText(R.string.editar_perfil));
        binding.edtNombre.setEnabled(false);
        binding.edtApellido.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}