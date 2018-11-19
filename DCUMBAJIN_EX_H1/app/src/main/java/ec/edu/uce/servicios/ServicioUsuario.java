package ec.edu.uce.servicios;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Usuario;

public class ServicioUsuario{

    private ObjectMapper MAPPER = new ObjectMapper();
    private final String REGISTER_FILE_NAME = "registro.txt";

    private ManejadorArchivos manejarArchivos = new ManejadorArchivos();
    private File registrarArchivo;

    public ServicioUsuario() {
        this.registrarArchivo = manejarArchivos.obtenerArchivo(REGISTER_FILE_NAME);
    }

    public void inicializarRecursos(Context context) {
        try {
            if (!manejarArchivos.existeCarpetaBase()) {
                manejarArchivos.crearCarpetaBase();
                Toast.makeText(context, "Creando carpeta: " + manejarArchivos.carpetaBase().getName(), Toast.LENGTH_LONG).show();
            }
            if (!existeArchivo()) {
                manejarArchivos.crearArchivo(REGISTER_FILE_NAME);
                Toast.makeText(context, "Creando el archivo: " + REGISTER_FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public Usuario guardar(Usuario usuario) {
        List<Usuario> usuarios = obtenerUsuarios();
        usuarios.add(usuario);
        try {
            manejarArchivos.escrituraArchivo(registrarArchivo, MAPPER.writeValueAsString(usuarios));
            return usuario;
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del usuarios", e);
        }
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        if (existeArchivo()) {
            String registarDatos = manejarArchivos.lecturaArchivo(registrarArchivo);
            if (!registarDatos.isEmpty()) {
                try {
                    usuarios = MAPPER.readValue(registarDatos, new TypeReference<List<Usuario>>(){
                    });
                } catch (IOException e) {
                    throw new CustomException("Error al leer el archivo: " + REGISTER_FILE_NAME, e);
                }
            }
        }
        return usuarios;
    }

    public Usuario obtener(Usuario usuario) {
        List<Usuario> usuarios = obtenerUsuarios();

        for (Usuario usr : usuarios) {
            if (usr.equals(usuario)) {
                return usr;
            }
        }
        return null;
    }

    public boolean existeArchivo() {
        return manejarArchivos.existeArchivo(REGISTER_FILE_NAME);
    }

    public boolean existe(Usuario usuario) {
        return obtenerUsuarios().contains(usuario);
    }
}
