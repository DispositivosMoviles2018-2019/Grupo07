package ec.edu.uce.avelez_examen_1h;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import ec.edu.uce.servicios.ServicioVehiculo;


public class AgregarVehiculo extends AppCompatActivity {
    private String VPlaca;
    private EditText txtPlaca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void guardar(View view) {
        txtPlaca = findViewById(R.id.txt_license);
        Vehiculo vehiculo = new Vehiculo();

        if (this.txtPlaca.getText().toString().isEmpty()) {
            txtPlaca.setError("Campo Vacio");
            txtPlaca.requestFocus();
        } else {
            Pattern pattern = Pattern.compile("[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]");
            Matcher matcher = pattern.matcher(this.txtPlaca.getText().toString());
            if (matcher.matches()) {
                Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " agregado correctamente", Toast.LENGTH_LONG).show();

            } else {
                txtPlaca.setError("formato de placa incorrecto!");
                txtPlaca.requestFocus();
            }
        }
    }
}
