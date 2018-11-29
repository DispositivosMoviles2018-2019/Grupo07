package ec.edu.uce.servicios;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ec.edu.uce.componentes.StorageException;

public class ManejadorArchivos{
    private final File ROOT_PATH = Environment.getExternalStorageDirectory();
    private final String FOLDER_NAME = "DatosFinal";
    private ObjectMapper MAPPER = new ObjectMapper();

    public File carpetaBase() {
        return new File(ROOT_PATH, FOLDER_NAME);
    }

    public boolean existeCarpetaBase() {
        return carpetaBase().exists();
    }

    public void crearCarpetaBase() {
        if (!carpetaBase().mkdirs()) {
            throw new StorageException("Error al crear la carpeta: " + FOLDER_NAME);
        }
    }

    public boolean existeArchivo(String fileName) {
        return new File(carpetaBase(), fileName).exists();
    }

    public void crearArchivo(String fileName) {
        File file = new File(carpetaBase(), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error al crear el archivo: " + fileName, e);
        }
    }

    public File obtenerArchivo(String fileName) {
        return new File(carpetaBase(), fileName);
    }

    public String lecturaArchivo(File file) {
        String data = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new StorageException("No existe el archivo: " + file.getName(), e);
        } catch (IOException e) {
            throw new StorageException("Ocurrio un error al leer el archivo: " + file.getName(), e);
        }
        return data;
    }

    public void escrituraArchivo(File file, String data) {
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.print(data);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            throw new StorageException("Error al escribir en el archivo: " + file.getName());
        }
    }
}
