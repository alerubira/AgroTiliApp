package com.principal.agrotiliapp.ui.tareas;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.clases.Tareas;
import com.principal.agrotiliapp.databinding.FragmentTareasBinding;

import java.util.List;

public class TareasFragment extends Fragment {
  private TareasViewModel mViewModel;
    private FragmentTareasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(TareasViewModel.class);

        binding = FragmentTareasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Liata Tareas",s);

            }
        });
        mViewModel.getMTareas().observe(getViewLifecycleOwner(), new Observer<List<Tareas>>() {
            @Override
            public void onChanged(List<Tareas> tareas) {
                TareasAdapter adapter=new TareasAdapter(tareas,getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                RecyclerView rv=binding.rvTareas;
                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        binding.fabCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TareasFragment.this)
                        .navigate(R.id.crearTareaFragment);
            }
        });
        mViewModel.obtenerTareas();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}