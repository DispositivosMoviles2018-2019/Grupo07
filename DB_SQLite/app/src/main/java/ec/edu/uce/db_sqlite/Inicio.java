package ec.edu.uce.db_sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.servicios.SQLiteController;

public class Inicio extends AppCompatActivity{

    private EditText ci, nombre, materia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ci = findViewById(R.id.editCI);
        nombre = findViewById(R.id.editNombre);
        materia = findViewById(R.id.editMateria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estudiantes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_add:
                String ci1 = ci.getText().toString();
                String nom = nombre.getText().toString();
                String mat = materia.getText().toString();
                if (ci1.length() > 0 && nom.length() > 0 && mat.length() > 0) {
                    SQLiteController sqLiteController = new SQLiteController(this, "DBEstudiantes", null, 1);
                    SQLiteDatabase db = sqLiteController.getWritableDatabase();
                    db.execSQL("INSERT INTO Estudiante (CI, Nombre, Materia) VALUES(" + ci1 + ",'" + nom + "','" + mat + "')");
                    db.close();
                    Toast.makeText(this, "El estudiante se a creado exitosamente", Toast.LENGTH_SHORT).show();
                    ci.setText("");
                    nombre.setText("");
                    materia.setText("");
                } else {
                    Toast.makeText(this, "Debe ingresar todos los datos asociados al Estudiante", Toast.LENGTH_SHORT).show();

                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
