package com.principal.agrotiliapp.auxiliares;

import android.app.AlertDialog;
import android.content.Context;

import com.principal.agrotiliapp.login.LoginActivity;

public class ApiDialogos {
    public static void abrirDialogoSimple(Context context, String titulo, String mensage){
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensage)
                .setNegativeButton("Cerrar Este Dialogo", (dialog, which) -> {
                    // Solo cierra el diálogo
                    dialog.dismiss();
                })
                .show();
    }

}
