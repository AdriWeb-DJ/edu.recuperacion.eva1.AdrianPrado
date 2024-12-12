package servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuImplementacion implements MenuInterfaz {
    private OperativaInterfaz operativa = new OperativaImplementacion();
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    FicheroInterfaz fi = new FicheroImplementacion();
    
    
    @Override
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
        	/*
        	 * Registro de apertura del menú en el log
        	 * apa-81224
        	 */
        	fi.ficheroLog("Se abre el menu");
        	System.out.println("================================MENU=============================");
            System.out.println("0. Salir");
            System.out.println("1. Registro de llegada");
            System.out.println("2. Listado de consultas");
            System.out.println("=================================================================");
            System.out.println("Seleccione una opcion:");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                	/*
                	 * Log de inicio de registro de llegada
                	 * apa-81224
                	 */
                	fi.ficheroLog("Se va a registrar una llegada");
                    registrarLlegada(scanner);
                    break;
                case 2:
                    mostrarListadoConsultas(scanner);
                    break;
                case 0:
                	/*
                	 * Registro de cierre del menú en el log
                	 * apa-81224
                	 */
                	fi.ficheroLog("Se cierra el menu");
                    System.out.println("Saliendo...");
                    break;
                default:
                	/*
                	 * Log de opción inválida seleccionada
                	 * apa-81224
                	 */
                	fi.ficheroLog("Error al elegir una opcion");
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrarLlegada(Scanner scanner) {
        /*
         * Solicita y valida el DNI del usuario
         * apa-81224
         */
        System.out.print("Introduce su DNI: ");
        String dni = scanner.nextLine();

        if (!operativa.validarDNI(dni)) {
        	/*
        	 * Log de registro fallido por DNI inválido
        	 * apa-81224
        	 */
        	fi.ficheroLog("Registro de llegada erroneo");
            System.out.println("DNI no válido. Inténtelo de nuevo.");
            return;
        }
        /*
         * Log de registro exitoso
         * apa-81224
         */
        fi.ficheroLog("Registro de llegada exitoso");
        String mensaje = operativa.registrarLlegada(dni);
        System.out.println(mensaje);
    }

    private void mostrarListadoConsultas(Scanner scanner) {
        int accion;
        do {
        	/*
        	 * Log de apertura del listado de consultas
        	 * apa-81224
        	 */
        	fi.ficheroLog("Se abre el listado de consultas");
	            System.out.println("======================Listado de Consultas=======================");
	            System.out.println("0. Volver");
	            System.out.println("1. Mostrar consultas");
	            System.out.println("2. Imprimir consultas");
	            System.out.println("=================================================================");
	            System.out.println("Seleccione una opcion:");
            accion = scanner.nextInt();
            scanner.nextLine();
            
            if (accion == 0) return;
            
	            System.out.println("====================Elija la Especialidad a ver==================");
	            System.out.println("0. Psicología");
	            System.out.println("1. Traumatología");
	            System.out.println("2. Fisioterapia");
	            System.out.println("=================================================================");
	            System.out.println("Seleccione una opcion:");
            int especialidad = scanner.nextInt();
            /*
             * Log de elección de especialidad
             * apa-81224
             */
            fi.ficheroLog("Se elije la especialidad " + especialidad);
            scanner.nextLine();

            System.out.print("Elija una fecha (dd-MM-yyyy): ");
            String fechaInput = scanner.nextLine();

            try {
                Date fecha = formatoFecha.parse(fechaInput);

                if (accion == 1) {
                    List<String> consultas = operativa.mostrarConsultas(especialidad, fecha);
                    if (consultas.isEmpty()) {
                        System.out.println("No hay datos disponibles para la especialidad y fecha indicada.");
                    } else {
                        consultas.forEach(System.out::println);
                    }
                } else if (accion == 2) {
                    String archivo = operativa.imprimirConsultas(especialidad, fecha);
                    System.out.println("Consultas exportadas a: " + archivo);
                }
            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Inténtelo de nuevo.");
            }
        } while (true);
    }
}
