package com.ejemplos.pabloangelveliz.ejemplosharedenclase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ejemplos.pabloangelveliz.ejemplosharedenclase.Utils.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("miShared", Context.MODE_PRIVATE);


        String color_guardado = preferences.getString("Color", "nada");

        Toast.makeText(this, "El color es:"+color_guardado, Toast.LENGTH_LONG).show();


    }

    public void clicGuardar(View v) {

        EditText editText = findViewById(R.id.edtColor);


        Util.setSharedPreferenceVar(this, editText.getText().toString());

        Util.mensajitos(this, getResources().getString(R.string.mensaje_ok));

        }
}
