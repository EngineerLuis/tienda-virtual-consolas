package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        Path archivo = Paths.get(rutaArchivo);
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        List<T> datos = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            T[] arrayDatos = gson.fromJson(reader, tipoClase);
            if (arrayDatos != null) {
                for (T dato : arrayDatos) {
                    datos.add(dato);
                }
            }
        } catch (IOException | JsonParseException e) {
            System.err.println("No se pudo leer el archivo JSON " + archivo.toAbsolutePath() + ": " + e.getMessage());
        }
        return datos;
    }

    public void escribirDatos(List<T> datos) {
        Path archivo = Paths.get(rutaArchivo);
        Path directorio = archivo.toAbsolutePath().getParent();
        Path temporal = null;

        try {
            if (directorio != null) {
                Files.createDirectories(directorio);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            temporal = Files.createTempFile(directorio, archivo.getFileName().toString(), ".tmp");
            try (Writer writer = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                gson.toJson(datos, writer);
            }

            try {
                Files.move(temporal, archivo, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("No se pudo guardar el archivo JSON " + archivo.toAbsolutePath() + ": " + e.getMessage());
            if (temporal != null) {
                try {
                    Files.deleteIfExists(temporal);
                } catch (IOException cleanupError) {
                    System.err.println("No se pudo eliminar el archivo temporal " + temporal + ": "
                            + cleanupError.getMessage());
                }
            }
        }
    }
}