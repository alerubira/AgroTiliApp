package com.principal.agrotiliapp.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.databinding.ActivityLoginBinding;
import com.principal.agrotiliapp.databinding.ActivityRecuperarClaveBinding;

public class RecuperarClaveActivity extends AppCompatActivity {
    private RecuperarClaveActivityViewModel mv;
    private ActivityRecuperarClaveBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecuperarClaveBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        mv.recibirIntent(intent);
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RecuperarClaveActivityViewModel.class);
        setContentView(binding.getRoot());
        mv.getMMensage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(RecuperarClaveActivity.this)
                        .setTitle("Recuperar Clave")
                        .setMessage(s)
                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        binding.btnResetClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveEmail=binding.edtClaveEmail.getText().toString();
                String claveNueva=binding.edtClaveNueva.getText().toString();
                String claveRepetida=binding.edtClaveRepetida.getText().toString();
                mv.corroborarDatos(claveEmail,claveNueva,claveRepetida);
            }
        });
        mv.getMExito().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent =new Intent(getApplication(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}