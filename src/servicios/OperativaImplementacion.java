package servicios;

import dto.Cita;
import dto.Especialidad;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OperativaImplementacion implements OperativaInterfaz {
	FicheroInterfaz fi = new FicheroImplementacion();
	 /*
     * String de las letras que puede tener un DNI
     * apa-81224
     */
	
    private static final String[] LETRAS_DNI = {
        "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B",
        "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
    };

    private List<Cita> citas = new ArrayList<>();
    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public OperativaImplementacion() {
        cargarCitas();
    }

    /*
     * Método para validar un DNI comprobando que la letra proporcionada coincida con la calculada.
     * apa-81224
     */
    
    @Override
    public boolean validarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            System.out.println("DNI inválido: longitud incorrecta o nulo");
            return false;
        }

        try {
            int numero = Integer.parseInt(dni.substring(0, 8));
            String letraProporcionada = dni.substring(8).toUpperCase();

            int indiceLetra = numero % 23;
            String letraCalculada = LETRAS_DNI[indiceLetra];

            return letraCalculada.equals(letraProporcionada);
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el número del DNI");
            return false;
        }
    }
    
    /*
     * Método para registrar la llegada de un paciente a su cita.
     * apa-81224
     */

    @Override
    public String registrarLlegada(String dni) {
        Date fechaActual = new Date();
        for (Cita cita : citas) {
            if (cita.getDni().equals(dni) && cita.getFecha().after(fechaActual)) {
                return "Espere su turno para la consulta de " + cita.getEspecialidad() + " en la sala de espera. Su especialista le avisará.";
            }
        }
        return "No dispone de cita previa para hoy.";
    }
    
    /*
     * Método para mostrar las consultas programadas para una especialidad en una fecha específica.
     * apa-81224
     */

    @Override
    public List<String> mostrarConsultas(int especialidad, Date fecha) {
        List<String> resultados = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getEspecialidad().ordinal() == especialidad && formatoFechaHora.format(cita.getFecha()).startsWith(new SimpleDateFormat("dd-MM-yyyy").format(fecha))) {
                resultados.add("Nombre del Paciente: " + cita.getNombreCompleto() + ", Hora: " + new SimpleDateFormat("HH:mm").format(cita.getFecha()));
            }
        }
        return resultados;
    }
    
    /*
     * Método para imprimir las consultas asistidas en un archivo de texto.
     * apa-81224
     */
    
    @Override
    public String imprimirConsultas(int especialidad, Date fecha) {
        
        fi.ficheroConsulta("Se abre el menú de consultas para la especialidad " + especialidad + " en la fecha " + new SimpleDateFormat("dd-MM-yyyy").format(fecha), fecha);

        String archivo = "citasConAsistencia-" + new SimpleDateFormat("ddMMyyyy").format(fecha) + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Cita cita : citas) {
                if (cita.getEspecialidad().ordinal() == especialidad &&
                    formatoFechaHora.format(cita.getFecha()).startsWith(new SimpleDateFormat("dd-MM-yyyy").format(fecha)) &&
                    cita.isAsistencia()) {

                    writer.println("Nombre completo: " + cita.getNombreCompleto() + ", Hora: " + new SimpleDateFormat("HH:mm").format(cita.getFecha()));
                    
                   
                    fi.ficheroConsulta("Consulta asistida: " + cita.getNombreCompleto() + ", Especialidad: " + cita.getEspecialidad() + ", Hora: " + new SimpleDateFormat("HH:mm").format(cita.getFecha()), fecha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return archivo;
    }



    /*
     * Método para cargar las citas desde un archivo externo.
     * apa-81224
     */
    
    private void cargarCitas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length < 5) {
                    System.err.println("Línea inválida en el archivo de citas: " + linea);
                    continue;
                }

                String dni = campos[0];
                String nombre = campos[1];
                String apellidos = campos[2];
                Especialidad especialidad = Especialidad.valueOf(campos[3].trim().toUpperCase());
                Date fecha = formatoFechaHora.parse(campos[4].trim());
                boolean asistencia = campos.length > 5 && !campos[5].trim().isEmpty() && Boolean.parseBoolean(campos[5].trim());

                citas.add(new Cita(dni, nombre, apellidos, especialidad, fecha, asistencia));
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error al cargar el archivo de citas: " + e.getMessage());
        }
    }
}

