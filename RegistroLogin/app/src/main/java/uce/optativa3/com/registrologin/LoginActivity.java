package uce.optativa3.com.registrologin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{
    private TextView registar;
    private Button ingresar;
    protected EditText usuario;
    protected EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario=(EditText) findViewById(R.id.txtUsuario);
        password=(EditText) findViewById(R.id.txtPassword);
        ingresar=(Button) findViewById(R.id.btnIngresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(usuario.getText().toString(), password.getText().toString());
            }
        });

        registar = findViewById(R.id.tvRegistrar);
        registar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registrar = new Intent(LoginActivity.this, RegistrarActivity.class);
                LoginActivity.this.startActivity(registrar);
            }
        });
    }
    public void validar(String userName, String userPass ){
        if ((userName.equals("admin") ) && (userPass.equals("admin"))){
            Intent darwin = new Intent(LoginActivity.this, UsuariosActivity.class);
            LoginActivity.this.startActivity(darwin);
        }
        else{
            Toast.makeText(getApplicationContext(),"Error de Usuario o Password",Toast.LENGTH_SHORT).show();
        }

    }
}
