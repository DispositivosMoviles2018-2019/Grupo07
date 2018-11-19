package ec.edu.uce.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicios.ServicioUsuario;

public class Login extends AppCompatActivity{

    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void registroUsuarioActivity(View view) {
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        if (txtUsername.getText().toString().isEmpty()) {
            txtUsername.setError("Campo Vacio");
            txtUsername.requestFocus();

        } else if (txtPassword.getText().toString().isEmpty()) {
            txtPassword.setError("Campo Vacio");
            txtPassword.requestFocus();

        } else {

            Usuario usuario = servicioUsuario.obtener(new Usuario(txtUsername.getText().toString(), null));

            if (usuario != null) {
                if (usuario.getPassword().equals(txtPassword.getText().toString())) {
                    Intent intent = new Intent(Login.this, ListadoVehiculos.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "El usuario no existe por favor registrese", Toast.LENGTH_LONG).show();
            }
        }
    }

}
