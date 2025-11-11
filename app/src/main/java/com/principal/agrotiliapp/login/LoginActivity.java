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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.principal.agrotiliapp.MainActivity;
import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
     private LoginActivtyViewModel mv;
     private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=ActivityLoginBinding.inflate(getLayoutInflater());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivtyViewModel.class);
        setContentView(binding.getRoot());
        mv.getMNoLogueado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Login")
                        .setMessage(s)
                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
                binding.edtUsuario.setText("");
                binding.edtPassword.setText("");
            }
        });
        mv.getMLogueado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // opcional, evita volver atrás al Inicio
                binding.edtUsuario.setText("");
                binding.edtPassword.setText("");
            }
        });
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.edtUsuario.getText().toString();
                String password = binding.edtPassword.getText().toString();
                mv.loguear(usuario, password);
            }
        });
        binding.btnOlvidoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.resetear(binding.edtUsuario.getText().toString());
            }
        });
        mv.getMReseteo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent =new Intent(getApplication(),RecuperarClaveActivity.class);
                intent.putExtra("email",s);
                startActivity(intent);
            }
        });
    }
}