package org.HGAD;
import java.util.Scanner;
public class Main {

    // Declaración de Arreglos, variables y "Objetos" globales
    // Columnas:
    // 0 = Nombre
    // 1 = Correo
    // 2 = Rango
    // 3 = Contraseña
    static String[][] ListaUsuarios = new String[1000][4];
    static int contadorUsuarios = 0; //
    // Lleva el control de cuántos usuarios se han registrado
    static Scanner scanner = new Scanner(System.in);
    // Escaner global
    static boolean EsAdmin = false;
    // Variable a usar en el futuro

    // INICIO DEL CODIGO DEL SISTEMA
    // MENU PRINCIPAL
    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("===========================");
            System.out.println("      DISCOS SALVAJES");
            System.out.println("===========================");
            System.out.println("0. Salir");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Opción inválida. Ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 0:
                    System.out.println("Fin del programa");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    // MODULO DE REGISTRO DE USUARIOS
    public static void registrarUsuario() {
        System.out.println("--- MÓDULO DE REGISTRO ---");
        System.out.println("Ingrese su nombre");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su correo");
        String correo = scanner.nextLine();

        // Valida si el correo ya está registrado previamente
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correo)) {
                System.out.println("Este correo ya se encuentra registrado.");
                return;
            }
        }

        System.out.println("Ingrese su rango");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        String rango = scanner.nextLine();
        while(!rango.equals("1") && !rango.equals("2")){
            System.out.println("Rango incorrecto");
            System.out.println("1. Usuario");
            System.out.println("2. Administrador");
            rango = scanner.nextLine();
        }
        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        // Almacena datos en la matriz global
        ListaUsuarios[contadorUsuarios][0] = nombre;
        ListaUsuarios[contadorUsuarios][1] = correo;
        ListaUsuarios[contadorUsuarios][2] = rango;
        ListaUsuarios[contadorUsuarios][3] = contraseña;

        contadorUsuarios++;

        System.out.println("¡Usuario registrado con éxito!");
    }

    // MODULO DE INICIO DE SESION
    public static void iniciarSesion() {
        System.out.println("--- MÓDULO DE INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo: ");
        String correoIngresado = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseñaIngresada = scanner.nextLine();

        boolean usuarioEncontrado = false;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correoIngresado)) {
                usuarioEncontrado = true;

                // Verifica si la contraseña coincide
                if (ListaUsuarios[i][3].equals(contraseñaIngresada)) {
                    System.out.println("¡Inicio de sesión exitoso!");
                    if(ListaUsuarios[i][2].equals("1")){
                        EsAdmin = false;
                    }
                    else if(ListaUsuarios[i][2].equals("2")){
                        EsAdmin = true;
                    }
                    System.out.println("Bienvenido, " + ListaUsuarios[i][0] + " Admin: " + EsAdmin);
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("El correo ingresado no existe en el sistema.");
        }
    }
}


// Tratar de hacer los siguientes modulos en este mismo archivo
// para asi verificar la compatibilidad entre todos los modulos
