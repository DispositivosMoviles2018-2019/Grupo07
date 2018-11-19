package ec.edu.uce.avelez_examen_1h;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ListadoVehiculos extends AppCompatActivity {
    //private ServicioVehiculo servicioVehiculo = new ServicioVehiculo();
    public static List<Vehiculo> vehiculos = new ArrayList<>();
   // public static DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_listado_vehiculos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Inicializa los vehiculos
        //inicializarVehiculos();

        // Inicializa el RecyclerView
      //  RecyclerView recycler = findViewById(R.id.RecyclerID);

        //adapter = new DataAdapter(vehiculos, this);
       // recycler.setAdapter(adapter);

        // Define el ClickListener
       // adapter.setItemClickListener(new ItemClickListener(){
           // @Override
         /*   public void onClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListadoVehiculos.this);
                    builder.setTitle("Seleccione una opción");

                    String[] options = {"Editar", "Eliminar"};
                    builder.setItems(options, new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0: // Editar
                                    EditarVehiculo.vehiculo = vehiculos.get(position);

                                    Intent intent = new Intent(ListadoVehiculos.this, EditarVehiculo.class);
                                    startActivity(intent);
                                    break;
                                case 1: // Eliminar
                                    vehiculos.remove(position);
                                    System.out.println("Size: " + vehiculos.size());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(ListadoVehiculos.this, "Manten presionado para ver las opciones", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_persist) {
            persistirDatos(vehiculos);
            return true;
        } else if (id == R.id.action_close) {
            persistirDatos(vehiculos);
            finish();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /*public void persistirDatos(List<Vehiculo> vehiculos) {
        try {
            if (!servicioVehiculo.existeArchivo()) {
                servicioVehiculo.inicializarRecursos(this);
            }
            this.vehiculos = servicioVehiculo.guardar(vehiculos);
            Toast.makeText(this, "Datos persistidos correctamente", Toast.LENGTH_LONG).show();
        } catch (CustomException e) {

        }
    }*/


    public void redirectFormActivity(View view) {
        Intent intent = new Intent(this, AgregarVehiculo.class);
        startActivity(intent);
    }

    /*public void inicializarVehiculos() {
        if (servicioVehiculo.existeArchivo()) {
            vehiculos = servicioVehiculo.getVehiculos();
        } else {
            vehiculos.add(new Vehiculo("XTR-9784", "Audi", new GregorianCalendar(2015, 11, 13).getTime(), 79990.0, true, "Negro"));
            vehiculos.add(new Vehiculo("CCD-0789", "Honda", new GregorianCalendar(1998, 3, 5).getTime(), 15340.0, false, "Blanco"));
        }
    }*/

}
