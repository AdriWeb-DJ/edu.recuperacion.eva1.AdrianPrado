package controladores;

import servicios.*;

public class Main {
    public static void main(String[] args) {
        MenuInterfaz menu = new MenuImplementacion();
        menu.iniciar();
    }
}
