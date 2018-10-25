package uce.optativa3.com.registrologin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class RegistrarActivity extends AppCompatActivity{
    private static final String TAG = "RegistrarActivity";

    private static final String FILENAME= "Archivo.txt";
    static final int READ_BLOCK_SIZE = 100;
    String genero = "";
    String asignatura = "";
    String ibeca = "";
    public String todo = "";
    //Datos
    private EditText usuario;
    private EditText clave;
    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private EditText celular;
    //Genero
    private RadioButton mas;
    private RadioButton fem;
    //Fecha
    private TextView fecha;
    private DatePickerDialog.OnDateSetListener dateListener;
    //Asignatura
    private CheckBox web;
    private CheckBox distribuida;
    private CheckBox BD;
    private CheckBox simulacion;
    private CheckBox optativaIII;
    //Beca
    private Switch beca;
    private ListView lista;
    //Guardar
    private Button guardar;

    ArrayList<Estudiante> listItems= new ArrayList<>();
    private AdapterUsuarios adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Variables
        usuario = findViewById(R.id.usuario);
        clave = findViewById(R.id.clave);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        email = findViewById(R.id.email);
        celular = findViewById(R.id.celular);
        fecha = findViewById(R.id.fecha);
        mas = findViewById(R.id.masculino);
        fem = findViewById(R.id.femenino);
        web = findViewById(R.id.web);
        distribuida = findViewById(R.id.disribuida);
        BD = findViewById(R.id.BD);
        simulacion = findViewById(R.id.simulacion);
        optativaIII = findViewById(R.id.optativaIII);
        beca = findViewById(R.id.beca);
        guardar = findViewById(R.id.btnGuardar);


        //Botón Guardar
        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnGuardar)
                    guardar();
            }
        });

        //Desplegar Fecha En un TextView
        fecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrarActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                fecha.setText(date);
            }
        };
    }

    //Validacion Genero
    public String validarGenero() {

        if (mas.isChecked() == true) {
            genero = "Masculino";
        }
        if (fem.isChecked()) {
            genero = "Femenino";
        }
        return genero;
    }

    //Validacion Genero
    public String validarAsignatura() {

        if (web.isChecked() == true) {
            asignatura += "Programacion Web ";
        }
        if (distribuida.isChecked()) {
            asignatura += "Programación Distribuida ";
        }
        if (BD.isChecked()) {
            asignatura += "Base de Datos I ";
        }
        if (simulacion.isChecked()) {
            asignatura += "Simulación ";
        }
        if (optativaIII.isChecked()) {
            asignatura += "OptativaIII ";
        }
        Toast.makeText(getApplicationContext(), asignatura, Toast.LENGTH_SHORT).show();
        return asignatura;
    }

    //Validacion Beca
    public String validarBeca() {
        if (beca.isChecked()) {
            ibeca = "Becado";
        } else {
            ibeca = "No Becado";
        }
        return ibeca;
    }

    //Metodo Guardar
    public void guardar() {

        todo = (usuario.getText() + "\n" + clave.getText() + "\n" + nombre.getText() + "\n"
                + apellido.getText() + "\n" + email.getText() + "\n" + celular.getText() + "\n" +
                validarGenero() + "\n" + fecha.getText() + "\n" + validarAsignatura() + "\n" +
                validarBeca()+"\n\n");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_APPEND));
            outputStreamWriter.write(todo);
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "Escritura de archivo fallo: " + e.toString());
        } finally {
            lista = (ListView) findViewById(R.id.listView);
            //adaptador=new AdapterUsuarios(this,GetArrayItems());
            listItems.add(new Estudiante(usuario.getText().toString(), clave.getText().toString(), nombre.getText().toString(),
                    apellido.getText().toString(),email.getText().toString(),celular.getText().toString(),
                    validarGenero(),fecha.toString(), validarAsignatura(), validarBeca()));
            Toast.makeText(getApplicationContext(),"Ya", Toast.LENGTH_SHORT).show();
            //lista.setAdapter(adaptador);
        }
        Intent registrar = new Intent(RegistrarActivity.this, LoginActivity.class);
        RegistrarActivity.this.startActivity(registrar);
    }

    public String leer() {
        String texto = todo;
        try {
            BufferedReader leer = new BufferedReader(
                    new InputStreamReader(
                            openFileInput("notas.txt")
                    ));
            while (leer.readLine() != null) {
                texto = leer.readLine();
            }
        } catch (Exception e) {
            System.out.println("error");

        }
        return texto;
    }
}
