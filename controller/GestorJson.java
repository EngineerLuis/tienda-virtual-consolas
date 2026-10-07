package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorJson<T> {
    private final String rutaArchivo;
    private final Class<T[]> tipoClase;

    public GestorJson(String rutaArchivo, Class<T[]> tipoClase) {
        this.rutaArchivo = rutaArchivo;
        this.tipoClase = tipoClase;
    }

    public List<T> leerDatos() {
        if (!new File(rutaArchivo).exists()) {
            return new ArrayList<>();
        }
        List<T> datos = new ArrayList<>();
        try (Reader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            T[] arrayDatos = gson.fromJson(reader, tipoClase);
            if (arrayDatos != null) {
                for (T dato : arrayDatos) {
                    datos.add(dato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public void escribirDatos(List<T> datos) {
        try (Writer writer = new FileWriter(rutaArchivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(datos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}