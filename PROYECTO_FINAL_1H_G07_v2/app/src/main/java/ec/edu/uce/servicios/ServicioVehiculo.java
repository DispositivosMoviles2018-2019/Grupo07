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
import ec.edu.uce.modelo.Vehiculo;

public class ServicioVehiculo{
    private ObjectMapper MAPPER = new ObjectMapper();
    private final String VEHICULO_FILE_NAME = "vehiculos.txt";

    private ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

    private File archivoVehiculos;

    public ServicioVehiculo() {
        this.archivoVehiculos = manejadorArchivos.obtenerArchivo(VEHICULO_FILE_NAME);
    }

    public void inicializarRecursos(Context context) {
        try {
            if (!manejadorArchivos.existeArchivo(VEHICULO_FILE_NAME)) {
                manejadorArchivos.crearArchivo(VEHICULO_FILE_NAME);
                Toast.makeText(context, "Creando el archivo: " + VEHICULO_FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public List<Vehiculo> guardar(List<Vehiculo> vehiculos) {
        try {
            manejadorArchivos.escrituraArchivo(archivoVehiculos, MAPPER.writeValueAsString(vehiculos));
            return getVehiculos();
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del vehiculo", e);
        }
    }

    public List<Vehiculo> getVehiculos() {
        String vehiculoData = manejadorArchivos.lecturaArchivo(archivoVehiculos);

        List<Vehiculo> vehiculos = new ArrayList<>();
        if (!vehiculoData.isEmpty()) {
            try {
                vehiculos = MAPPER.readValue(vehiculoData, new TypeReference<List<Vehiculo>>() {
                });
            } catch (IOException e) {
                throw new CustomException("Error al leer el archivo: " + VEHICULO_FILE_NAME, e);
            }
        }
        return vehiculos;
    }

    public boolean existeArchivo() {
        return manejadorArchivos.existeArchivo(VEHICULO_FILE_NAME);
    }
}
