package com.principal.agrotiliapp.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;
    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        binding=FragmentCambiarClaveBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(requireContext(),"Cambiar Clave",s);

            }
        });
        mViewModel.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(requireContext(),"Cambiar Clave",s);

                binding.edtClaveActual.setText("");
                binding.edtClaveNuevaC.setText("");
                binding.edtClaveRepetidaC.setText("");
            }
        });
        binding.btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clave=binding.edtClaveActual.getText().toString();
                String claveNueva=binding.edtClaveNuevaC.getText().toString();
                String claverepetida=binding.edtClaveRepetidaC.getText().toString();
                mViewModel.corroborarClaves(clave,claveNueva,claverepetida);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        // TODO: Use the ViewModel
    }

}