package com.principal.agrotiliapp.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.principal.agrotiliapp.auxiliares.ApiDialogos;

import com.principal.agrotiliapp.databinding.ActivityRecuperarClaveBinding;

public class RecuperarClaveActivity extends AppCompatActivity {
    private RecuperarClaveActivityViewModel mv;
    private ActivityRecuperarClaveBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecuperarClaveBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RecuperarClaveActivityViewModel.class);
        setContentView(binding.getRoot());
        mv.recibirIntent(intent);
        mv.getMMensage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ApiDialogos.abrirDialogoSimple(RecuperarClaveActivity.this,"Recuperar Clave",s);

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