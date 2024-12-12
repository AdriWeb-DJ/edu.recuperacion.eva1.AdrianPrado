package servicios;
import java.util.*;

public interface OperativaInterfaz {
    boolean validarDNI(String dni);

    String registrarLlegada(String dni);

    List<String> mostrarConsultas(int especialidad, Date fecha);

    String imprimirConsultas(int especialidad, Date fecha);
}
