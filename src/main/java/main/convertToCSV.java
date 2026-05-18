package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.*;


class convertToCSV {
    //Escribir en el archivo CSV
    private static String writeLineByLine(String[][] lines, Path path) throws Exception {
        //FileWriter para escribir sobre el archivo
        try (FileWriter filewriter = new FileWriter(path.toString(),StandardCharsets.UTF_8)) {
            filewriter.write('\ufeff');
            //Creamos un CSVwriter configurado con ";" para ir escribiendo el archivo
            try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(filewriter)
                .withSeparator(';')
                .build()) {
                for (String[] line : lines) {
                    writer.writeNext(line);
                }
            }
        return readFile(path);
        }
    }

    private static String readFile(Path path) throws Exception {
        //Lectura del archivo
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                stringBuilder.append(linea).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    public static String writeLineByLineExample(String[][] datos) throws Exception {
        //Formato para el nombre del archivo
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fechaTexto = ahora.format(formato);
        //Creación del nombre del archivo.
        String nombreArchivo = "benchmark_" + fechaTexto + ".csv";
        
        //Comprobación de la carpeta output
        Path carpetaOutput = Paths.get("src", "main", "resources", "main", "output");
        if (!Files.exists(carpetaOutput)) {
            Files.createDirectories(carpetaOutput);
            System.out.println("Carpeta no existía, creada con éxito");
        }

        //Path final del archivo
        Path path = Paths.get("src", "main", "resources", "main", "output", nombreArchivo);
        
        return writeLineByLine(datos, path); 
    }

}