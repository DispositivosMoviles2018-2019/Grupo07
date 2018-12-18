package ec.edu.uce.appdownloadimage;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.iniciarDescarga).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iniciarDescarga) {
            //Iniciar descarga
            EditText miURL = (EditText) findViewById(R.id.urlImagen);
            String url = miURL.getText().toString();
            new descargaImagenes().execute(url);


        }
    }

    class descargaImagenes extends AsyncTask<String, Integer, Integer>{
        ProgressBar miBarra;

        @Override
        protected void onPreExecute() {
            miBarra = (ProgressBar) findViewById(R.id.barraProgreso);
        }

        @Override
        protected Integer doInBackground(String... urlStrings) {

            try {
                URL url = new URL(urlStrings[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int tamano = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/temp.jpg");
                byte datos[] = new byte[1024];
                int cuenta;
                long total = 0;
                while ((cuenta = input.read(datos)) != -1) {
                    total += cuenta;
                    output.write(datos, 0, cuenta);
                    this.publishProgress((int) (total * 100 / tamano));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progreso) {
            int porcentaje = progreso[0];
            miBarra.setProgress(porcentaje);
            Log.e("MIAPP", "Llevamos un " + porcentaje + " %");
        }

        @Override
        protected void onPostExecute(Integer resultado){
            ImageView miImagen = (ImageView) findViewById(R.id.imagenDescargada);
            miImagen.setImageBitmap(BitmapFactory.decodeFile("/sdcard/temp.jpg"));
        }
    }
}
