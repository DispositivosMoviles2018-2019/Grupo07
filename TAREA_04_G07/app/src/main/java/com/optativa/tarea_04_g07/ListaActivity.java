package com.optativa.tarea_04_g07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class ListaActivity extends AppCompatActivity{

    private RecyclerView reciclerViewUsuario;
    private RecyclerViewAdaptador adaptadorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        reciclerViewUsuario = (RecyclerView) findViewById(R.id.recycler_usuario);
        reciclerViewUsuario.setLayoutManager(new LinearLayoutManager(this));


        adaptadorUsuario = new RecyclerViewAdaptador(MainActivity.usuarios);
        reciclerViewUsuario.setAdapter(adaptadorUsuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.regresar:

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.salir:
                System.exit(0);
                finish();
                break;
        }
        return true;
    }
}
