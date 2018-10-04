package com.itmaster.clima;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AgregarActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar);
		
		Button boton = (Button) findViewById(R.id.botonGuardar);
		
		boton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		EditText nombre = (EditText) findViewById(R.id.nombreCiudad);

		Intent datos = new Intent();
		datos.putExtra("ciudad", nombre.getText().toString());

		//Acá es donde retorno al MainActivity
		setResult(RESULT_OK, datos);
		finish();
		
	}
	
}












