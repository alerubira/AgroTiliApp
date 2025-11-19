package com.principal.agrotiliapp.request;

import retrofit2.Response;

public class ApiErrorHandler {

        public static String parseError(Response<?> response) {
            String errorMsg;

            try {
                if (response.errorBody() != null) {
                    errorMsg = response.errorBody().string();
                } else {
                    errorMsg = "Error desconocido";
                }
            } catch (Exception e) {
                errorMsg = "Error al leer la respuesta del servidor";
            }

            // manejar el mensaje según el código HTTP:
            switch (response.code()) {
                case 400:
                    return "Solicitud inválida: " + errorMsg;
                case 401:
                    return "No autorizado: " + errorMsg;
                case 403:
                    return "Acceso denegado: " + errorMsg;
                case 404:
                    return "No encontrado: " + errorMsg;
                case 500:
                    return "Error interno del servidor";
                default:
                    return "Error (" + response.code() + "): " + errorMsg;
            }
        }

        public static String defaultFailure(Throwable t) {
            return "Error de conexión: " + t.getMessage();
        }
    }


