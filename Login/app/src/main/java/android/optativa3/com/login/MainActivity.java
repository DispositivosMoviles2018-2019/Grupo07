package android.optativa3.com.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected TextView Informacion;
    protected EditText Usuario;
    protected EditText Password;
    protected Button Ingresar;
    int counter=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Informacion=(TextView) findViewById(R.id.tvInformacion);
        Usuario=(EditText) findViewById(R.id.txtUsuario);
        Password=(EditText) findViewById(R.id.txtPassword);
        Ingresar=(Button) findViewById(R.id.btnIngresar);

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(Usuario.getText().toString(), Password.getText().toString());
            }
        });

    }
    public void validar(String userName, String userPass ){
        if ((userName.equals("darwin") ) && (userPass.equals("cumbajin"))){
            Intent darwin = new Intent(MainActivity.this, BienvenidoActivity.class);
            MainActivity.this.startActivity(darwin);
        }else if ((userName.equals("andres")) && (userPass.equals("velez"))) {
            Intent andres = new Intent(MainActivity.this, BienvenidoActivity.class);
            startActivity(andres);
        }else if ((userName.equals("wiliam")) && (userPass.equals("salazar"))) {
            Intent wiliam = new Intent(MainActivity.this, BienvenidoActivity.class);
            startActivity(wiliam);
        }else if ((userName.equals("damian")) && (userPass.equals("quisingo"))) {
            Intent damian = new Intent(MainActivity.this, BienvenidoActivity.class);
            startActivity(damian);
        }
        else{
            counter --;
            Informacion.setText("ha excedido los "+ String.valueOf(counter)+ " intentos");
            if (counter == 0){
                Ingresar.setEnabled(false);
            }
        }

    }


}
