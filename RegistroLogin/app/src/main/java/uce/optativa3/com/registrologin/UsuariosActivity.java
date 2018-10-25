package uce.optativa3.com.registrologin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class UsuariosActivity extends AppCompatActivity{
    RegistrarActivity r = new RegistrarActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

       // super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        //  lista=(ListView) findViewById(R.id.ListView);
        TextView datos = (TextView) findViewById(R.id.datostxt);
        try {
            FileInputStream fin = openFileInput("Archivo.txt");

            InputStreamReader fileReader = new InputStreamReader(fin);
            char[] buffer = new char[6000];
            String s = "";
            int charRead;
            s+="\n";
            while ((charRead = fileReader.read(buffer)) > 0) {
                s += String.copyValueOf(buffer, 0, charRead);
            }
            fileReader.close();
            datos.setText(s);

        }catch (Exception e) {
                Toast.makeText(this,"error ",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.regresar:
                Toast.makeText(getApplicationContext(),r.leer(),Toast.LENGTH_SHORT).show();
                case R.id.salir:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
