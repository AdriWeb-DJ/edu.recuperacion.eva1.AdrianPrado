package servicios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FicheroImplementacion implements FicheroInterfaz {
	SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
	String fechaHoy = formatoFecha.format(new Date());
	
	@Override
	public void ficheroLog(String dato) {
		String ruta= "C:\\Users\\Adrian Prado\\Documents\\logs\\log-"+ fechaHoy + ".txt";
		try {
			BufferedWriter escribe= new BufferedWriter(new FileWriter(ruta,true));
			escribe.write(dato.concat("\n"));
			escribe.close();
		} catch (IOException e) {
			System.out.println("Ocurrio un error en el fichero Log");
		}
		
	}
	
	@Override
	public void ficheroConsulta(String dato, Date fecha) {
	    // Usar la fecha pasada como parámetro para generar el nombre del archivo
	    SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
	    String fechaFormateada = formatoFecha.format(fecha);  // Formatea la fecha que se pasa como parámetro
	    String ruta = "C:\\Users\\Adrian Prado\\Documents\\consultas\\citasConAsistencia-" + fechaFormateada + ".txt";
	    
	    try {
	        BufferedWriter escribe = new BufferedWriter(new FileWriter(ruta, true));
	        escribe.write(dato.concat("\n"));
	        escribe.close();
	    } catch (IOException e) {
	        System.out.println("Ocurrió un error en el fichero de consultas");
	    }
	}
}