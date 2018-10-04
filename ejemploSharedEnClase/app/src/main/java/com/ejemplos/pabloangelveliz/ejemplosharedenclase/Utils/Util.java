package com.ejemplos.pabloangelveliz.ejemplosharedenclase.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Util {

    public static void setSharedPreferenceVar(Context c, String value) {

        SharedPreferences preferences = c.getSharedPreferences("miShared", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Color", value);

        editor.commit();
    }

    public static void mensajitos(Context c, String mensaje) {
        Toast.makeText(c, mensaje, Toast.LENGTH_LONG).show();
    }
}
