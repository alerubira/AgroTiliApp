package com.principal.agrotiliapp.ui.perfil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.ApiDialogos;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.databinding.FragmentCambiarImagenBinding;
import com.principal.agrotiliapp.request.ApiClient;

public class CambiarImagenFragment extends Fragment {

    private CambiarImagenViewModel mViewModel;
    private FragmentCambiarImagenBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;


    private ActivityResultLauncher<String> permisoCamaraLauncher;
    private ActivityResultLauncher<Void> abrirCamaraLauncher;
    private Bitmap fotoBitmap;


    public static CambiarImagenFragment newInstance() {
        return new CambiarImagenFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // launcher para pedir permiso
        permisoCamaraLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        abrirCamaraLauncher.launch(null);
                    } else {
                        mostrarDialogoPermiso();
                    }
                });

        // launcher para abrir cámara
        abrirCamaraLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                    if (bitmap != null) {
                        fotoBitmap = bitmap;

                        // ENVIAR AL VIEWMODEL
                        mViewModel.recibirFotoDeCamara(bitmap);

                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CambiarImagenViewModel.class);
        binding=FragmentCambiarImagenBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        abrirGaleria();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Cambiar Imagen",s);
            }
        });
        mViewModel.getMUrlImagen().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String urlActualizada = ApiClient.URLBASE + s + "?t=" + System.currentTimeMillis();
                Glide.with(getContext())
                        .load(urlActualizada)
                        .error("null")
                        .into(binding.imgCambiarImagen);
            }
        });
        mViewModel.getMEmpleado().observe(getViewLifecycleOwner(), new Observer<Empleados>() {
            @Override
            public void onChanged(Empleados empleados) {
                ApiDialogos.abrirDialogoSimple(getContext(),"Cambiar Imagen","Imagen Modoficada con exito");
                String urlActualizada = ApiClient.URLBASE
                        + empleados.getImagen_perfil()
                        + "?t=" + System.currentTimeMillis();
                Glide.with(getContext())
                        .load(urlActualizada)

                        .error("null")
                        .into(binding.imgCambiarImagen);
            }
        });
        binding.btnBuscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });
        binding.btnAbrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermisoCamara();
            }
        });
        // Recuperar el bundle
        Bundle bundle = getArguments();
        mViewModel.recibirBumdle(bundle);
        return root;
    }


    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Es para abrir la galeria
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);

            }
        });
    }
    private void pedirPermisoCamara() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            abrirCamaraLauncher.launch(null);
        } else {
            permisoCamaraLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    private void mostrarDialogoPermiso() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Permiso de cámara requerido")
                .setMessage("Debe aceptar el permiso para usar la cámara.")
                .setPositiveButton("Configurar", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}