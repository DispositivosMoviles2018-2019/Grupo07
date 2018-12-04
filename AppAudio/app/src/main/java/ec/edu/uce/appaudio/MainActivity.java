package ec.edu.uce.appaudio;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    TextView estado;
    MediaRecorder recorder;
    MediaPlayer player;
    File archivo;
    Button Grabar, Parar, Reproducir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estado = (TextView) this.findViewById(R.id.estado);
        Grabar = (Button) findViewById(R.id.Grabar);
        Parar = (Button) findViewById(R.id.Parar);
        Reproducir = (Button) findViewById(R.id.Reproducir);
    }

    public void grabar(View v) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            archivo = File.createTempFile("Audio", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        estado.setText("Grabando");
        Grabar.setEnabled(false);
        Parar.setEnabled(true);
    }

    public void detener(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();
        } catch (IOException e) {
        }
        Grabar.setEnabled(true);
        Parar.setEnabled(false);
        Reproducir.setEnabled(true);
        estado.setText("Listo para reproducir");
    }

    public void reproducir(View v) {
        player.start();
        Grabar.setEnabled(false);
        Parar.setEnabled(false);
        Reproducir.setEnabled(false);
        estado.setText("Reproduciendo");
    }

    public void onCompletion(MediaPlayer mp) {
        Grabar.setEnabled(true);
        Parar.setEnabled(true);
        Reproducir.setEnabled(true);
        estado.setText("Listo");
    }

}
