package com.itmaster.clima;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.preference.PreferenceManager;

public class MainActivity extends ListActivity {

	private ArrayList<Ciudad> ciudades;
	private ArrayAdapter<Ciudad> adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ciudades = new ArrayList<Ciudad>();

		adaptador = new ArrayAdapter<Ciudad>(this, 
				android.R.layout.simple_list_item_1, ciudades);
		
		setListAdapter(adaptador);
	}
	

	@Override
	protected void onListItemClick(ListView l, View v,
			int position, long id) {
				
		Ciudad seleccionada = ciudades.get(position);
		try {
			new LeerClima().execute("http://api.openweathermap.org/data/2.5/weather?q=" +
									URLEncoder.encode(seleccionada.getNombre(), "utf-8") +
									"&appid=aa43128c1614074c31228079baa6869a"
									);
		} catch (UnsupportedEncodingException e) {
		}
		
		super.onListItemClick(l, v, position, id);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent = new Intent(this, AgregarActivity.class);

		startActivityForResult(intent, 90210);

		return super.onOptionsItemSelected(item);
	}
	
	// Se ejecuta cuando me pasan datos de otro activity al cual yo abr�
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ((requestCode==90210) && (resultCode==RESULT_OK)) {
			
			String nombreCiudad = data.getStringExtra("ciudad");

			if (nombreCiudad.length()>2) {
				try {
					//ciudades.add(new Ciudad(URLEncoder.encode(nombreCiudad, "utf-8")));
                    ciudades.add(new Ciudad(nombreCiudad));
				} catch (Exception e) {
					//
				}
				
				// Debo refrescar el ListView
				adaptador.notifyDataSetChanged();

			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}


	public class LeerClima extends AsyncTask<String, Void, String> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MainActivity.this, "Por favor espere...", "Descargando clima...", true);
			super.onPreExecute();
		}
		
	    @Override
	    protected String doInBackground(String... urls) {
	    		// Trae contenido de internet
		    InputStream inputStream = null;
		    String result = "";
		    try {
				inputStream = new URL(urls[0]).openStream();
		        if(inputStream != null) {
			        	BufferedReader buffer = new BufferedReader( new InputStreamReader(inputStream));
			        String line = "";
			        while ((line = buffer.readLine()) != null)
			            result += line;
			 
			        inputStream.close();
		        } else {
		            // ERROR;
		        }
		
		    } catch (Exception e) {
	            // ERROR;
		        Log.d("InputStream", e.getLocalizedMessage());
		    }
		    return result;
	    }
	
	    
	    @Override
	    protected void onPostExecute(String texto) {	
	    		dialog.cancel();

	    		try {
	    			// Tomo todo el string del JSON y genero un objeto de tipo JSON.
			       	JSONObject json = new JSONObject(texto);

					String json_code = json.getString("cod");

						// Tomo un objeto de tipo Array donde puedo ir a buscar el m�s informaci�n de clima.
						JSONArray WeatherJson = json.getJSONArray("weather");

						// Tomo del objeto Main la temperatura.
						JSONObject jsonMain = json.getJSONObject("main");

						double temperaturaK = jsonMain.getDouble("temp");
						float temperaturaKFloat = ((int) temperaturaK * 100) / 100;
						float temperaturaC = (float) (temperaturaKFloat - 273.15);

						String mensaje = "La temperatura es " +
								String.valueOf(temperaturaC) + " C";

						Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();

		    		} catch (Exception e) {
		    			
		    			// Cualquier problema en la lectura del JSON, se ir� por este camino.
		    			Toast.makeText(MainActivity.this, "Hubo un problema", Toast.LENGTH_LONG).show();
	    		}
	    }
	    
	}

}
