package com.principal.agrotiliapp.ui.tareas.maquinasAgrarias;

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
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.databinding.FragmentMaquinasAgrariasBinding;

import java.util.List;

public class MaquinasAgrariasFragment extends Fragment {

    private MaquinasAgrariasViewModel mViewModel;
    private FragmentMaquinasAgrariasBinding binding;

    public static MaquinasAgrariasFragment newInstance() {
        return new MaquinasAgrariasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MaquinasAgrariasViewModel.class);
        binding=FragmentMaquinasAgrariasBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Maquinas Agrarias")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMMaquinas().observe(getViewLifecycleOwner(), new Observer<List<Maquinas_Agrarias>>() {
            @Override
            public void onChanged(List<Maquinas_Agrarias> maquinasAgrarias) {
                MaquinasAgrariasAdapter adapter=new MaquinasAgrariasAdapter(maquinasAgrarias,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                RecyclerView rv = binding.rvMaquinas;
                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
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
        mViewModel = new ViewModelProvider(this).get(MaquinasAgrariasViewModel.class);
        // TODO: Use the ViewModel
    }

}