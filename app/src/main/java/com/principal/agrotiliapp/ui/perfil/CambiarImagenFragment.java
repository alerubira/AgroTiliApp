package com.principal.agrotiliapp.ui.perfil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.databinding.FragmentCambiarImagenBinding;
import com.principal.agrotiliapp.request.ApiClient;

public class CambiarImagenFragment extends Fragment {

    private CambiarImagenViewModel mViewModel;
    private FragmentCambiarImagenBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;
    public static CambiarImagenFragment newInstance() {
        return new CambiarImagenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CambiarImagenViewModel.class);
        binding=FragmentCambiarImagenBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Cambiar Imagen",s);
            }
        });
        mViewModel.getMUrlImagen().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(getContext())
                        .load(ApiClient.URLBASE+s)
                        .error("null")
                        .into(binding.imgCambiarImagen);
            }
        });
        binding.btnBuscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirGaleria();
            }
        });
        // Recuperar el bundle
        Bundle bundle = getArguments();
        mViewModel.recibirBumdle(bundle);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CambiarImagenViewModel.class);
        // TODO: Use the ViewModel
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Es para abrir la galeria
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //Log.d("AgregarInmuebleFragment", "Result: " + result);
                mViewModel.recibirFoto(result);

            }
        });
    }
}