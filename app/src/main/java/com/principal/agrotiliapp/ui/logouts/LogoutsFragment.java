package com.principal.agrotiliapp.ui.logouts;

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

import com.principal.agrotiliapp.databinding.FragmentLogoutsBinding;

public class LogoutsFragment extends Fragment {
    private LogoutsViewModel mViewModel;

    private FragmentLogoutsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(LogoutsViewModel.class);

        binding = FragmentLogoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.getMDeslogueado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Cierra completamente la aplicación
                requireActivity().finishAffinity();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Deseas salir de la aplicación?")
                .setPositiveButton("Sí, salir", (dialog, which) -> {
                    mViewModel.desloquearse();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}