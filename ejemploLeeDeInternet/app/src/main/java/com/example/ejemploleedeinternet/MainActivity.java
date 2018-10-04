package com.example.ejemploleedeinternet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new LeerDeInternet().execute("http://alumnos.itmaster.com.ar/horoscopo.txt");

		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
				.execute("https://www.itmaster.com.ar/images/logo-academy.png");

	}

	/*
	Voy a crear una clase que extienda AsyncTask para poder descargarme un archivo de
	texto de internet.

	public class LeerDeInternet extends AsyncTask<String, Integer, String>

	El primer paráemtro es de tipo String, y lo usaremos para indicar la URL.
	El segundo paráemtro es de tipo Integer, y lo usaremos para indicar una variable de progreso.
	El tercer parámetro es un String, que contendrá el contenido del archivo de texto.

	 */

	public class LeerDeInternet extends AsyncTask<String, Integer, String> {

		private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading. Please wait... ");
			dialog.setIndeterminate(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
	    protected String doInBackground(String... urls) {
		    InputStream inputStream = null;
			String result = "";
		    try {
				inputStream = new URL(urls[0]).openStream();

				if(inputStream != null) {
			        	BufferedReader buffer = new BufferedReader( new InputStreamReader(inputStream));
			        String line = "";

					int i = 0;
			        while ((line = buffer.readLine()) != null) {
						result += line;

						//ESTO SOLAMENTE LO HAGO A EFECTOS DE PODER MOSTRAR EL PROGRESSBAR
						Thread.sleep(100);
						publishProgress(i);
						i++;
					}

			        inputStream.close();
		        } else {
		            // ERROR;
		        }
		
		    } catch (Exception e) {
	            // ERROR;
		        //Log.d("InputStream", e.getLocalizedMessage());
		    }
		    return result;
	    }

		protected void onProgressUpdate(Integer... progress) {
			// Aca animación

			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading. Please wait... " + progress[0].toString());
			dialog.setIndeterminate(true);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
	    protected void onPostExecute(String resultado) {

			// REMUEVO EL DIALOGO.
			dialog.dismiss();

			// En result está el texto que viene de Internet
			TextView txtv = (TextView) findViewById(R.id.textView1);
			txtv.setText(resultado);
			txtv.setMovementMethod(new ScrollingMovementMethod());
		}
	}

	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView miImageView;

	    public DownloadImageTask(ImageView bmImage) {
	        this.miImageView = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new URL(urldisplay).openStream();

	            mIcon11 = BitmapFactory.decodeStream(in);

	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {

	        miImageView.setImageBitmap(result);
	    }
	}


}
