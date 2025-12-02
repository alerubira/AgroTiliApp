package com.principal.agrotiliapp.auxiliares;

import android.app.Activity;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class Navegacion {
    public static void navegarBorrandoStack(Activity activity,
                                              int navHostId,
                                              int destinationId,
                                              int popUpToId,
                                              boolean inclusive) {

        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(popUpToId, inclusive)
                .build();

        Navigation.findNavController(activity, navHostId)
                .navigate(destinationId, null, navOptions);
    }
}
