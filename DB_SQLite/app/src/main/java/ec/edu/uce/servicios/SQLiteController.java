package ec.edu.uce.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteController extends SQLiteOpenHelper{

    //Query creacion tabla
    String query = "CREATE TABLE Estudiante(CI INTEGER, Nombre TEXT, Materia TEXT )";

    public SQLiteController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //El metodo se ejecuta automaticamente cuando la base de datos no existe
        //Crea la DB, si es la primera vez que se llama a la clase
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se ejecuta cuando se detecta que la version de la DB cambio, por lo que se
        //debe difinir todos los procesos de migracion de la estructura anterior a la
        //nueva estructura.
        db.execSQL("DROP TABLE IF EXISTS Estudiante");
        db.execSQL(query);

    }
}
