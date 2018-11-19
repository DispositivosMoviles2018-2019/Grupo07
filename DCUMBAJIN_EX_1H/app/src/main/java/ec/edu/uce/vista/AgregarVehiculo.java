package ec.edu.uce.vista;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import ec.edu.uce.servicios.ServicioVehiculo;

import static ec.edu.uce.vista.ListadoVehiculos.vehiculos;

public class AgregarVehiculo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ServicioVehiculo servicioVehiculo = new ServicioVehiculo();

    private String VPlaca;
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
    public String colorValidado;

    //Radio Matricula
    private RadioButton si;
    private RadioButton no;

    //Color Spinner
    private Spinner comboColores;
    private TextView otroC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        comboColores = findViewById(R.id.idSpinnerColor);
        otroC = findViewById(R.id.txt_otro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_colores, android.R.layout.simple_spinner_item);

        comboColores.setAdapter(adapter);
        comboColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otroC.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

    }

    public void guardar(View view) {
        txtPlaca = findViewById(R.id.txt_license);
        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);
        //    txtColor = findViewById(R.id.txt_color);
        wsEnrollment = findViewById(R.id.sw_enrollment);
        txtDate = findViewById(R.id.txt_date);
        blanco = findViewById(R.id.rd_blanco);
        negro = findViewById(R.id.rd_negro);
        otroColor = findViewById(R.id.rd_otro);

        // Instancia de matricula Radio Button
        si = findViewById(R.id.rd_si);
        no = findViewById(R.id.rd_no);


        //Instancia de Vehiculo
        Vehiculo vehiculo = new Vehiculo();

        if (this.txtPlaca.getText().toString().isEmpty()) {
            txtPlaca.setError("Campo Vacio");
            txtPlaca.requestFocus();
        } else {
            Pattern pattern = Pattern.compile("[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]");
            Matcher matcher = pattern.matcher(this.txtPlaca.getText().toString());
            if (matcher.matches()) {

                if (this.txtMarca.getText().toString().isEmpty()) {
                    txtMarca.setError("Campo Vacio");
                    txtMarca.requestFocus();
                } else {
                    Pattern pattern1 = Pattern.compile("[A-Za-z]*");
                    Matcher matcher1 = pattern1.matcher(this.txtMarca.getText().toString());
                    if (matcher1.matches()) {

                        if (this.txtCosto.getText().toString().isEmpty()) {
                            txtCosto.setError("Campo Vacio");
                            txtCosto.requestFocus();
                        } else {
                            Pattern pattern2 = Pattern.compile("[0-9]*");
                            Matcher matcher2 = pattern2.matcher(this.txtCosto.getText().toString());
                            if (matcher2.matches()) {


                                if (this.txtDate.getText().toString().isEmpty()) {
                                    txtDate.setError("Campo Vacio");
                                    txtDate.requestFocus();
                                } else {
                                /*     if(blanco.isChecked()){
                                         colorValidado="Blanco";

                                     }if (negro.isChecked()){
                                         colorValidado ="Negro";
                                    } if(otroColor.isChecked()){
                                         colorValidado=otroColorText.getText().toString();
                                    }
                                   */
                                    Double cost = Double.parseDouble(txtCosto.getText().toString());
                                    if (cost > 15000 && cost < 35000) {
                                        colorValidado = otroC.getText().toString();
                                        String datePattern = "dd MMMM yyyy";
                                        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

                                        // Todo eliminar
                                        System.out.println("Precios: " + txtCosto.getText().toString());

                                        //  Boolean matribulaV = wsEnrollment.isChecked();
                                        Date date = new Date();
                                        try {
                                            date = sdf.parse(txtDate.getText().toString());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        vehiculo.setPlaca(txtPlaca.getText().toString());
                                        vehiculo.setMarca(txtMarca.getText().toString());
                                        vehiculo.setCosto(cost);
                                        vehiculo.setColor(colorValidado);
                                        //vehiculo.setMatriculado(matribulaV);
                                        vehiculo.setMatriculado(validarMatricula());
                                        vehiculo.setFechaFabricacion(date);

                                        try {
                                            if (!vehiculos.contains(vehiculo)) {
                                                vehiculos.add(vehiculo);

                                                ListadoVehiculos.adapter.notifyDataSetChanged();
                                                Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " agregado correctamente", Toast.LENGTH_LONG).show();
                                                finish();
                                            } else {
                                                Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " ya existe", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (CustomException e) {
                                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        txtCosto.setError("el costo debe ser menor 35000 && mayor 15000!");
                                        txtCosto.requestFocus();
                                    }
                                }

                            } else {
                                txtCosto.setError("Solo escribir Números!");
                                txtCosto.requestFocus();
                            }
                        }

                    } else {
                        txtMarca.setError("Solo escribir texto!");
                        txtMarca.requestFocus();
                    }
                }
            } else {
                txtPlaca.setError("formato de placa incorrecto!");
                txtPlaca.requestFocus();
            }
        }
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

        String date = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.txt_date);
        txtFecha.setText(date);
    }

    //Retonar valores Matricula Con Radio Button
    private Boolean validarMatricula() {
        Boolean valor = true;
        if (si.isChecked()) {
            valor = true;
        }
        if (no.isChecked()) {
            valor = false;
        }
        return valor;
    }


}
