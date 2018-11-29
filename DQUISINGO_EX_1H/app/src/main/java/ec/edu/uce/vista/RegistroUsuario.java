package ec.edu.uce.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicios.ServicioUsuario;

public class RegistroUsuario extends AppCompatActivity {

    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void registrarUsuario(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        if (txtUsername.getText().toString().isEmpty()) {
            txtUsername.setError("Campo Vacio");
            txtUsername.requestFocus();
        } else if (txtPassword.getText().toString().isEmpty()){
            txtPassword.setError("Campo Vacio");
            txtPassword.requestFocus();
        }else{
            try {
                servicioUsuario.inicializarRecursos(this);

                Usuario usuario = new Usuario(txtUsername.getText().toString(), txtPassword.getText().toString());
                if (servicioUsuario.existe(usuario)) {
                    Toast.makeText(this, "El usuario " + usuario.getUsername() + " ya existe ingrese otro", Toast.LENGTH_LONG).show();
                } else {
                    servicioUsuario.guardar(usuario);
                    Toast.makeText(this, "Usuario " + usuario.getUsername() + " registrado correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (CustomException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
