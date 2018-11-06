package com.optativa.tarea_04_g07;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.optativa.pojos.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.widget.ArrayAdapter;

public class RegistroActivity extends AppCompatActivity{
    private static final String TAG = "RegistroActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    List<String> asignaturas = new ArrayList();
    private EditText usuario;
    private EditText clave;
    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private EditText celular;
    private Button botonImagen;
    private ImageView imagen;
    private RadioButton masculino;
    private RadioButton femenino;
    private TextView fecha;
    private Spinner dia;
    private Spinner mes;
    private Spinner ano;
    private CheckBox mineria;
    private CheckBox distribuida;
    private CheckBox gestion;
    private CheckBox simulacion;
    private CheckBox optativaIII;
    private Switch becado;
    Bitmap imageBitmap;

    private byte[] foto;

    private String archivo = "usuario";
    private String carpeta = "/ArchivoOptativaIII/";

    String file_path = "";
    String name = "";

    private DatePickerDialog.OnDateSetListener dateListener;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        usuario = (EditText) findViewById(R.id.txt_usuario_registro);
        clave = (EditText) findViewById(R.id.txt_clave);
        nombre = (EditText) findViewById(R.id.txt_nombre_registro);
        apellido = (EditText) findViewById(R.id.txt_apellido);
        email = (EditText) findViewById(R.id.txt_email_registro);
        celular = (EditText) findViewById(R.id.txt_celular_registro);
        masculino = (RadioButton) findViewById(R.id.rb_masculino);
        femenino = (RadioButton) findViewById(R.id.rb_femenino);
        botonImagen = (Button) findViewById(R.id.btn_foto);
        imagen = (ImageView) findViewById(R.id.imag_view_foto);
        mineria = (CheckBox) findViewById(R.id.checkBoxMineria);
        distribuida = (CheckBox) findViewById(R.id.checkBoxDistribuida);
        gestion = (CheckBox) findViewById(R.id.checkBoxGestion);
        simulacion = (CheckBox) findViewById(R.id.checkBoxSimulacion);
        optativaIII = (CheckBox) findViewById(R.id.checkBoxOptativaIII);
        becado = (Switch) findViewById(R.id.switchBeca);
        fecha = (TextView) findViewById(R.id.txt_fecha);


        //Desplegar Fecha En un TextView
        fecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                ParseFecha(date);
                System.out.println(ParseFecha(date));
            }
        };

    }

    //Formato Date
    public static Date ParseFecha(String fecha)
    {
        Date fechaDate = null;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
    //Validacion Beca
    public  Boolean validarBeca() {
        Boolean beca=true;
        if (becado.isChecked()) {
            beca=true;
        } else {
            beca=false;
        }
        return beca;
    }

    //Validacion Asigantura
    public List<String> validarAsignatura() {


        if (mineria.isChecked() == true) {
            asignaturas.add("MIneria");
        }
        if (distribuida.isChecked()) {
            asignaturas.add("Programación Distribuida");
        }
        if (gestion.isChecked()) {
            asignaturas.add("Gestión");
        }
        if (simulacion.isChecked()) {
            asignaturas.add("Simulación");
        }
        if (optativaIII.isChecked()) {
            asignaturas.add("OptativaIII");
        }
        return asignaturas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.regresar:
                Intent siguiente;
                siguiente = new Intent(this, MainActivity.class);
                startActivity(siguiente);
                finish();
                break;
            case R.id.salir:
                System.exit(0);
                finish();
                break;
        }
        return true;
    }

    public void tomarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
        }
    }

    public void guardar(View view) throws IOException {

        File file;
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuarioAux;
        this.file_path = (Environment.getExternalStorageDirectory() + this.carpeta);
        File localFile = new File(this.file_path);

        if (!localFile.exists()) {
            localFile.mkdir();
        }

        this.name = (this.archivo + ".bin");
        file = new File(localFile, this.name);

        if (file.exists()) {
            try {
                FileInputStream fis;
                fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    usuarioAux = (Usuario) ois.readObject();
                    usuarios.add(usuarioAux);
                }
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        if (this.usuario.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo Usuario vacio", Toast.LENGTH_SHORT).show();
        } else {
            if (this.clave.getText().toString().isEmpty()) {
                Toast.makeText(this, "Campo Clave vacio", Toast.LENGTH_SHORT).show();
            } else {
                if (this.nombre.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Campo Nombre vacio", Toast.LENGTH_SHORT).show();
                } else {
                    if (this.apellido.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Campo Apellido vacio", Toast.LENGTH_SHORT).show();
                    } else {
                        if (this.email.getText().toString().isEmpty()) {
                            Toast.makeText(this, "Campo E-mail vacio", Toast.LENGTH_SHORT).show();
                        } else {
                            String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
                                    "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
                            Pattern pattern = Pattern.compile(emailPattern);

                            Matcher matcher = pattern.matcher(this.email.getText().toString());
                            if (matcher.matches()) {
                                if (this.celular.getText().toString().isEmpty()) {
                                    Toast.makeText(this, "Campo Celular vacio", Toast.LENGTH_SHORT).show();
                                } else {
                                    Pattern pattern1 = Pattern.compile("^09[0-9]{8}$");
                                    Matcher matcher1 = pattern1.matcher(this.celular.getText().toString());
                                    if (matcher1.matches()) {
                                        if (imagen.getDrawable() == null) {
                                            Toast.makeText(this, "Debe insertar una imagen", Toast.LENGTH_SHORT).show();
                                        } else {

                                            Integer genero;
                                            if (masculino.isChecked() == true)

                                            {
                                                genero = 0;
                                            } else if (femenino.isChecked() == true)

                                            {
                                                genero = 1;
                                            } else

                                            {
                                                genero = 2;
                                            }

                                            Usuario usuarioNuevo = new Usuario();

                                            usuarioNuevo.setUsuario(this.usuario.getText().toString());
                                            usuarioNuevo.setClave(this.clave.getText().toString());
                                            usuarioNuevo.setNombre(this.nombre.getText().toString());
                                            usuarioNuevo.setApellido(this.apellido.getText().toString());
                                            usuarioNuevo.setEmail(this.email.getText().toString());
                                            usuarioNuevo.setCelular(this.celular.getText().toString());

                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            if (imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) == true)

                                            {
                                                byte[] byteArray = stream.toByteArray();
                                                imageBitmap.recycle();
                                                usuarioNuevo.setFoto(byteArray);
                                            }

                                            usuarioNuevo.setGenero(genero);
                                            usuarioNuevo.setFecha(ParseFecha(fecha.getText().toString()));
                                            usuarioNuevo.setAsignaturas(validarAsignatura());
                                            usuarioNuevo.setBecado(validarBeca());
                                            usuarios.add(usuarioNuevo);

                                            OutputStream os = new FileOutputStream(file);

                                            ObjectOutputStream oos = new ObjectOutputStream(os);
                                            for (
                                                    Usuario u : usuarios) {
                                                oos.writeObject(u);
                                            }
                                            oos.close();
                                            os.close();
                                            Toast.makeText(this, "Registro Satisfactorio", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else {
                                        Toast.makeText(this, "El numero celular no tiene el formato deseado, debe tener 10 numeros y iniciar con 09", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(this, "E-mail no cumple los estandares de un correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }
    }
}
