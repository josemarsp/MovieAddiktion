package br.com.josef.movieaddiktion.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

    public static boolean isNetworkConnected(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = (conectivtyManager.getActiveNetworkInfo() != null) &&
                conectivtyManager.getActiveNetworkInfo().isAvailable() &&
                conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }
}