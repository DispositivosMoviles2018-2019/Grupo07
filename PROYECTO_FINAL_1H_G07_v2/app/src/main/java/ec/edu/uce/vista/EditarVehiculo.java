package ec.edu.uce.vista;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.controlador.DatePickerFragment;
import ec.edu.uce.modelo.Vehiculo;

public class EditarVehiculo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static Vehiculo vehiculo;

    private EditText txtPlaca;
    private EditText txtMarca;
    private EditText txtCosto;
  //  private EditText txtColor;
    private Switch wsEnrollment;
    private TextView txtDate;
    private RadioButton blanco;
    private RadioButton negro;
    private RadioButton otroColor;
    private EditText otroColorText;
    public String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (vehiculo != null) {
            montarDatos();
        }

    }

    public void editar(View view) {
        inicializarCampos();

        Pattern pattern1 = Pattern.compile("[A-Za-z]*");
        Matcher matcher1 = pattern1.matcher(this.txtMarca.getText().toString());
        if (matcher1.matches()) {

            Pattern pattern2 = Pattern.compile("[0-9]*");
            Matcher matcher2 = pattern1.matcher(this.txtMarca.getText().toString());
            if (matcher2.matches()) {

                Pattern pattern3 = Pattern.compile("[A-Za-z]*");
                Matcher matcher3 = pattern1.matcher(this.otroColorText.getText().toString());
                if (matcher3.matches()) {

                    String datePattern = "dd MMMM yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

                    Double costo = Double.parseDouble(txtCosto.getText().toString());
                    Boolean isEnrollment = wsEnrollment.isChecked();
                    Date date = new Date();
                    try {
                        date = sdf.parse(txtDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Vehiculo newVehiculo = new Vehiculo();
                    newVehiculo.setPlaca(txtPlaca.getText().toString());
                    newVehiculo.setMarca(txtMarca.getText().toString());
                    newVehiculo.setCosto(costo);
                    newVehiculo.setColor(validarColor());
                    newVehiculo.setMatriculado(isEnrollment);
                    newVehiculo.setFechaFabricacion(date);

                    try {
                        ListadoVehiculos.vehiculos.remove(vehiculo);
                        ListadoVehiculos.vehiculos.add(newVehiculo);
                        ListadoVehiculos.adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " se edito correctamente", Toast.LENGTH_LONG).show();
                        finish();
                    } catch (CustomException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    otroColorText.setError("Solo escribir texto!");
                    otroColorText.requestFocus();
                }
            }else {
                txtCosto.setError("Solo escribir Números!");
                txtCosto.requestFocus();
            }
        }else {
            txtMarca.setError("Solo escribir texto!");
            txtMarca.requestFocus();
        }
    }

    public void inicializarCampos() {
        txtPlaca = findViewById(R.id.txt_license);
        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);
      //  txtColor = findViewById(R.id.txt_color);
        wsEnrollment = findViewById(R.id.sw_enrollment);
        txtDate = findViewById(R.id.txt_date);
        blanco = findViewById(R.id.rd_blanco);
        negro = findViewById(R.id.rd_negro);
        otroColor = findViewById(R.id.rd_otro);
        otroColorText=findViewById(R.id.txt_otro);

    }

    public void montarDatos() {
        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        inicializarCampos();
        txtPlaca.setText(vehiculo.getPlaca());
        txtMarca.setText(vehiculo.getMarca());
        txtCosto.setText(String.valueOf(vehiculo.getCosto()));
     //   txtColor.setText(vehiculo.getColor());
        if(vehiculo.getColor().equalsIgnoreCase("Blanco")) {
            blanco.setChecked(true);
        } else if(vehiculo.getColor().equalsIgnoreCase("Negro")) {
            negro.setChecked(true);
        }
        else {
            otroColor.setChecked(true);
            otroColorText.setText(vehiculo.getColor());
        }
        wsEnrollment.setChecked(vehiculo.getMatriculado());
        txtDate.setText(sdf.format(vehiculo.getFechaFabricacion()));
    }

    public void showDataPicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        String fecha = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.txt_date);
        txtFecha.setText(fecha);
    }

    //Validacion Color
    public String validarColor() {

        if (blanco.isChecked() == true) {
            color = "Blanco";
        }
        if (negro.isChecked()) {
            color = "Negro";
        }
        if (otroColor.isChecked()){
            color= otroColorText.getText().toString();
        }
        return color;
    }


}
