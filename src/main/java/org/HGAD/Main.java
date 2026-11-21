package org.HGAD;
import java.util.Scanner;
public class Main 
{
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
    static boolean MaxAdmin = false;
    static String[][] datosdisco = new String[1000][9];
    static int contadorDiscos = 0;
    static String Nombre = " ";
    static boolean InicioSesion = false;
    static String[][] usuarios = new String[100][3];
    static int totalUsuarios = 0;
    static String[][] wishlist = new String[100][3];
    static int totalDeseos = 0;
    static int usuarioActual = -1;

    // INICIO DEL CODIGO DEL SISTEMA
    // MENU PRINCIPAL
    public static void main(String[] args) {
        int opcion = 0;
        boolean Operacion = false;
        do {
            Operacion = false;
            System.out.println("===========================");
            System.out.println("      DISCOS SALVAJES");
            System.out.println("===========================");
            System.out.println("0. Salir");
            if (InicioSesion == false) {
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar Sesión");
            }
            if(InicioSesion == true) {
                System.out.println("1. Cerrar Session.");
                System.out.println("2.-Consulta de discos ");
                System.out.println("3.-Mi Whishlist");
                if(EsAdmin == true) {
                    System.out.println("4.- Registro de discos");
                    System.out.println("5.- Gestion de usuarios");
                }
            }
            System.out.println("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Opcion no valida, ingrese un numero entero.");
                opcion = -1;
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                continue;
            }
            switch (opcion) {
                case 0:
                    System.out.println("Fin del programa");
                    Operacion = true;
                    break;
                case 1:
                    if (InicioSesion == false) {
                        registrarUsuario();
                    } else {
                        CierreSession();
                    }
                    Operacion = true;
                    break;
                case 2:
                    if (InicioSesion == false) {
                        iniciarSesion();
                    } else {
                        ordenamiento();
                    }
                    Operacion = true;
                    break;
            }
            if (InicioSesion == true) {
                switch (opcion) {
                    case 3:
                        menuWishlist();
                        Operacion = true;
                        break;
                }
                if(EsAdmin == true) {
                    switch (opcion) {
                        case 4:
                            Operacion = true;
                            break;
                        case 5:
                           gestionarUsuariosAdmin();
                           Operacion = true;
                           break;
                    }
                }
            }
            if(Operacion == false) {
                switch (opcion) {
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            }
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
        } while (opcion != 0);
    }

    // MODULO DE REGISTRO DE USUARIOS
    public static void registrarUsuario() {
        System.out.println("--- MÓDULO DE REGISTRO ---");
        System.out.println("Ingrese su nombre");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su correo");
        String correo = scanner.nextLine();
      /*
      • ^ : Marca el inicio del texto. Asegura que no haya espacios ni caracteres raros antes del usuario.
      • [a-zA-Z0-9._-] : Es el grupo de caracteres permitidos para el nombre de usuario:
	    • a-z y A-Z: Letras minúsculas y mayúsculas (sin ñ ni tildes).
	    • 0-9: Números del 0 al 9.
	    • ._-: Punto, guion bajo y guion medio.
      • + : Significa "uno o más". Obliga a que el usuario escriba al menos un carácter válido antes del arroba (no permite dejarlo vacío).
      • @ : Exige el símbolo de arroba literal en esa posición exacta.
      • (...) : Agrupa las opciones válidas para el dominio.
      • | : Funciona como un "O" lógico (acepta gmail o hotmail o outlook o yahoo).
      • \\. : Representa un punto literal (.). En las expresiones regulares, un punto solo significa "cualquier carácter"; las barras invertidas (\\) le quitan ese poder especial en Java para que busque estrictamente un punto.
      • $ : Marca el final del texto. Impide que se escriba algo extra después del .com (como .com.mx o espacios).
      •.matches(), evalua que la cadena de texto ingresada cumpla con las características ingresadas en los paraentesis 
      */
       while (!correo.matches("^[a-zA-Z0-9._-]+@(gmail\\.com|hotmail\\.com|outlook\\.com|yahoo\\.com)$" ) )
        {
            System.out.println("Error: Formato inválido, el sistema no acepta este formato, siga el siguiente formato ( ejemplo: usuario@gmail.com).");
            System.out.println("Error: Solo se permiten correos @gmail.com, @hotmail.com , @outlook.com. y  @yahoo.com");
            System.out.println("Ingrese su correo nuevamente:");
            correo= scanner.nextLine().trim();
        }
        // Valida si el correo ya está registrado previamente
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correo)) {
                System.out.println("Este correo ya se encuentra registrado.");
                return;
            }
        }
        String rango = " ";
        if (MaxAdmin == false) {
        System.out.println("Ingrese su rango");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        rango = scanner.nextLine();
            while (!rango.equals("1") && !rango.equals("2")) {
                System.out.println("Rango incorrecto");
                System.out.println("1. Usuario");
                System.out.println("2. Administrador");
                rango = scanner.nextLine();
            }
            if (rango.equals("2")) {
                MaxAdmin = true;
            }
        }
        else{
            System.out.println("El sistema llego al numero maximo de administradores");
            System.out.println("Tu rango por defecto sera usuario");
            rango = "1";
        }
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        // Almacena datos en la matriz global
        ListaUsuarios[contadorUsuarios][0] = nombre;
        ListaUsuarios[contadorUsuarios][1] = correo;
        ListaUsuarios[contadorUsuarios][2] = rango;
        ListaUsuarios[contadorUsuarios][3] = contrasena;

        contadorUsuarios++;

        System.out.println("¡Usuario registrado con éxito!");
    }

    // MODULO DE INICIO DE SESION
    public static void iniciarSesion() {
        System.out.println("--- MÓDULO DE INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo: ");
        String correoIngresado = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasenaIngresada = scanner.nextLine();

        boolean usuarioEncontrado = false;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correoIngresado)) {
                usuarioEncontrado = true;

                // Verifica si la contraseña coincide
                if (ListaUsuarios[i][3].equals(contrasenaIngresada)) {
                    System.out.println("¡Inicio de sesión exitoso!");
                    if (ListaUsuarios[i][2].equals("1")) {
                        EsAdmin = false;
                    } else if (ListaUsuarios[i][2].equals("2")) {
                        EsAdmin = true;
                    }
                    Nombre = ListaUsuarios[i][0];
                    usuarioActual=i;
                    System.out.println("Bienvenido, " + Nombre + " Admin: " + EsAdmin);
                    InicioSesion = true;
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
    public static void CierreSession( ){
        String OpcionL = " ";
        boolean Exito = false;
        while(Exito == false){
            System.out.println("Estas seguro que quieres cerrar sesion?");
            System.out.println("1. Si");
            System.out.println("2. No");
            OpcionL = scanner.nextLine();
            if (OpcionL.equals("1")) {
                EsAdmin = false;
                InicioSesion = false;
                Nombre = " ";
                usuarioActual=-1;
                System.out.println("Sesion cerrada");
                Exito = true;
            } else if (OpcionL.equals("2")) {
                Exito = true;
                break;
            }
            else{
                System.out.println("Opcion incorrecta, intenta de nuevo");
            }
        }
    }

    //MODULO DE ORDENAMIENTO
    public static void ordenamiento() {
        System.out.println("========Modulo de consulta=========");
        if (contadorDiscos == 0) {
            System.out.println("Lo sentimos, el sistema no posee registro de algún disco");
        } else
        {
            boolean Condicion = false;
            int numero = 0;
            int opcion;
            do
            {
                System.out.println("Ingrese el Título del Disco:");
                String buscatitle=scanner.nextLine();
                System.out.println("Ingrese el artista:");
                String buscaartist = scanner.nextLine();
                System.out.println("Ingrese el género:");
                String buscagenero = scanner.nextLine();
                System.out.println("Ingrese Subgenero:");
                String buscarsubg = scanner.nextLine();
                System.out.println("Ingrese año de publicación:");
                String buscaraniop = scanner.nextLine();
                while (!Condicion) {
                    try {
                        numero = Integer.parseInt(buscaraniop);
                        if (numero > 0)
                        {
                            Condicion = true;
                        } else {
                            System.out.println("Error. el año de publicación debe ser mayor a 0");
                            buscaraniop = scanner.nextLine();
                        }

                    } catch (NumberFormatException e)
                    {
                        System.out.println("Error, inserta un número valido");
                        buscaraniop = scanner.nextLine();
                    }
                }
                Condicion = false;
                boolean encontrado = false;
                for (int i = 0; i < contadorDiscos; i++) {
                /*
                    usamos un boolea para encontrar las coincidencias de los discos que busca el usuario,
                    donde usamos un iqualsIgnoreCase para que sin importar que increse con mayusculas o minúsculas
                    los datos, se pueda ingresar los datos correctamente, y .trim para que si se ingres un espacio adicional
                    no haya algún error o inconcistencia, es decir, lo convierte tal cual esta registrado
                 */
                    boolean coincideTitulo = datosdisco[i][0].equalsIgnoreCase(buscatitle.trim());
                    boolean coincideArtista = datosdisco[i][1].equalsIgnoreCase(buscaartist.trim());
                    boolean coincideGenero = datosdisco[i][2].equalsIgnoreCase(buscagenero.trim());
                    boolean coincideSubg = datosdisco[i][3].equalsIgnoreCase(buscarsubg.trim());
                /*
                .equals se usa unicamente para evaluar la condición a cumplir, String.valueOf,
                vuelve a transformar la variable de tipo int (entero) a String (texto).
                 */
                    boolean coincideAnio = datosdisco[i][4].equals(String.valueOf(numero));

                    if (coincideTitulo && coincideArtista && coincideGenero && coincideSubg && coincideAnio) {
                        System.out.println("===========Datos del Disco===========");
                        System.out.println("Título: " + datosdisco[i][0]);
                        System.out.println("Artista: " + datosdisco[i][1]);
                        System.out.println("Genero: " + datosdisco[i][2]);
                        System.out.println("Subgenero: " + datosdisco[i][3]);
                        System.out.println("Año: " + datosdisco[i][4]);
                        System.out.println("Estado del Disco: " + datosdisco[i][5]);
                        System.out.println("Precio: $" + datosdisco[i][6]);
                        System.out.println("Formato Físico: " + datosdisco[i][7]);
                        System.out.println("Cantidad en Stock: " + datosdisco[i][8] + " unidades");
                        encontrado = true;
                    }
                }
                if (!encontrado)
                {
                    System.out.println("No se encuentra en existencia el disco solicitado");
                }
                System.out.println("¿Desea continuar?");
                System.out.println("1.-Si");
                System.out.println("2.-No");
                opcion=0;
                while (opcion!=1 && opcion!=2)
                {
                    if (scanner.hasNextInt())
                    {
                        opcion = scanner.nextInt();
                        scanner.nextLine();
                        if (opcion != 1 && opcion != 2) {
                            System.out.println("Error. Ingrese un número entre el 1 y el 2:");
                        }
                    }
                    else
                    {
                        scanner.nextLine();
                        System.out.println("Error. Ingrese un número válido (1 o 2):");
                    }
                }
            }while(opcion!=2);
        }

    }

    //MODULO DE REGISTRO DE DISCOS
    public static void registrodiscos() {
        int opcion;
        int numero;
        double numero2;
        boolean Condicion = false;
        if (EsAdmin) {
            if (contadorDiscos >= datosdisco.length) {
                System.out.println("Error: La base de datos de discos está llena. No se pueden registrar más álbumes.");

            } else
            {
                System.out.println("======Modulo de registro de discos======");
                System.out.println("Bienvenido Administrador, ingrese los datos de los dicos a registrar");
                do
                {
                    System.out.println("1.-Título del disco:");
                    datosdisco[contadorDiscos][0] = scanner.nextLine();
                    for (int i = 0; i < contadorDiscos; i++) {
                        if (datosdisco[i][0].equalsIgnoreCase(datosdisco[contadorDiscos][0])) {
                            System.out.println("Disco con título en existencia");
                            return;
                        }
                    }
                    System.out.println("2.-Artista:");
                    datosdisco[contadorDiscos][1] = scanner.nextLine();
                    System.out.println("3.-Género:");
                    datosdisco[contadorDiscos][2] = scanner.nextLine();
                    System.out.println("4.-Subgénero:");
                    datosdisco[contadorDiscos][3] = scanner.nextLine();
                    System.out.println("5.-Año de publicación:");
                    datosdisco[contadorDiscos][4] = scanner.nextLine().trim();

                    while (!Condicion) {
                        String entrada=datosdisco[contadorDiscos][4];
                        if(entrada.matches("\\d+"))
                        {
                            try {
                                numero = Integer.parseInt(datosdisco[contadorDiscos][4]);
                                if (numero > 0) {
                                    Condicion = true;
                                } else {
                                    System.out.println("Error, el año debe ser mayor a 0. Intente de nuevo:");
                                    datosdisco[contadorDiscos][4] = scanner.nextLine().trim();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error, inserta un numero valido");
                                datosdisco[contadorDiscos][4] = scanner.nextLine();
                            }
                        }
                        else
                        {
                            System.out.println("Error, inserta un año válido (solo números enteros sin símbolos ni decimales):");
                            datosdisco[contadorDiscos][4] = scanner.nextLine().trim();
                        }
                    }
                    Condicion = false;
                    System.out.println("6.-Estado del Disco (Mint, Near Mint, VG+, VG, G):");
                    datosdisco[contadorDiscos][5] = scanner.nextLine();
                    while (!datosdisco[contadorDiscos][5].equalsIgnoreCase("Mint") &&
                            !datosdisco[contadorDiscos][5].equalsIgnoreCase("Near Mint") &&
                            !datosdisco[contadorDiscos][5].equalsIgnoreCase("VG+") &&
                            !datosdisco[contadorDiscos][5].equalsIgnoreCase("VG") &&
                            !datosdisco[contadorDiscos][5].equalsIgnoreCase("G")) {
                        System.out.println("Error, ingrese una opción valida. Ejemplo: Mint, Near Mint, VG+, VG, G" );
                        datosdisco[contadorDiscos][5] = scanner.nextLine();
                    }
                    System.out.println("7.-Precio ($):");
                    datosdisco[contadorDiscos][6] = scanner.nextLine();
                    while (!Condicion ) {
                        try {
                            numero2 = Double.parseDouble(datosdisco[contadorDiscos][6]);
                            if (numero2 > 0) {
                                Condicion = true;
                            } else {
                                System.out.println("Error, el precio debe ser mayor a 0. Intente de nuevo");
                                datosdisco[contadorDiscos][6] = scanner.nextLine();

                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error, inserta un numero valido");
                            datosdisco[contadorDiscos][6] = scanner.nextLine();
                        }
                    }
                    Condicion = false;
                    System.out.println("8.-Formato Físico");
                    datosdisco[contadorDiscos][7] = scanner.nextLine();
                    while (!datosdisco[contadorDiscos][7].equalsIgnoreCase("Vinilo") &&
                            !datosdisco[contadorDiscos][7].equalsIgnoreCase("CD") &&
                            !datosdisco[contadorDiscos][7].equalsIgnoreCase("Casete") )
                    {
                        System.out.println("Error, ingrese una opción valida. Ejemplo: Vinilo, CD, Casete" );
                        datosdisco[contadorDiscos][7] = scanner.nextLine();
                    }
                    System.out.println("9.-Cantidad en Stock");
                    datosdisco[contadorDiscos][8] = scanner.nextLine();
                    while (!Condicion) {
                        try {
                            numero = Integer.parseInt(datosdisco[contadorDiscos][8]);
                            if (numero > 0) {
                                Condicion = true;
                            } else {
                                System.out.println("Error, la cantidad en stock debe ser mayor a 0. Intente de nuevo");
                                Integer.parseInt(datosdisco[contadorDiscos][8]);
                                datosdisco[contadorDiscos][8] = scanner.nextLine();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error, inserta un numero valido");
                            datosdisco[contadorDiscos][8] = scanner.nextLine();
                        }
                    }
                    contadorDiscos++;
                    System.out.println("¡Disco registrado con éxito!");
                    System.out.println("¿Desea registrar otro disco?");
                    System.out.println("1.-Si");
                    System.out.println("2.-No");
                    opcion=0;
                    while (opcion!=1 && opcion!=2)
                        {
                        if (scanner.hasNextInt())
                        {
                            opcion = scanner.nextInt();
                            scanner.nextLine();
                            if (opcion != 1 && opcion != 2)
                            {
                                System.out.println("Error. Ingrese un número entre el 1 y el 2:");
                            }
                        }
                        else
                        {
                            scanner.nextLine();
                            System.out.println("Error. Ingrese un número válido (1 o 2):");
                        }
                    }
                    /*scanner.hasNextInt(), revisa lo que escribió el usuario en el teclado antes de intentar guardarlo en una variable int.
                    Devuelve true si el dato es un número entero válido y false si el usuario escribió letras o símbolo.
                     */
                }
                while (opcion!=2);
            }
        }
    }
    public static void menuWishlist() {
        int opcion = 0;
        do {
            System.out.println("\n--- MI WISHLIST DE VINILOS ---");
            System.out.println("1. Ver mi wishlist");
            System.out.println("2. Agregar disco");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Opción inválida.");
                continue;
            }

            if (opcion == 1) {
                mostrarWishlist();
            } else if (opcion == 2) {
                agregarDisco();
            } else if (opcion == 0) {
                System.out.println("Volviendo al menú principal...");
            } else {
                System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }
    public static void mostrarWishlist() {
        String correoActual = ListaUsuarios[usuarioActual][1];
        System.out.println("\n--- WISHLIST DE " + ListaUsuarios[usuarioActual][0].toUpperCase() + " ---");

        boolean hayDiscos = false;
        double totalPrecio = 0;
        int contador = 1;

        for (int i = 0; i < totalDeseos; i++) {
            if (wishlist[i][0].equals(correoActual)) {
                double precio = Double.parseDouble(wishlist[i][2]);
                System.out.println(contador + ". Álbum: " + wishlist[i][1] + " | Precio: $" + wishlist[i][2]);
                totalPrecio += precio;
                contador++;
                hayDiscos = true;
            }
        }

        if (!hayDiscos) {
            System.out.println("Tu wishlist está vacía.");
        } else {
            System.out.println("Total estimado a gastar: $" + totalPrecio);
        }
    }
    public static void agregarDisco() {
        if (totalDeseos >= 100) {
            System.out.println("La lista de deseos global está llena.");
            return;
        }

        System.out.println("--- AGREGAR DISCO ---");

        String album = "";
        while (album.isEmpty()) {
            System.out.print("Nombre del álbum: ");
            album = scanner.nextLine().trim();
            if (album.isEmpty()) {
                System.out.println("El nombre del álbum no puede estar vacío.");
            }
        }

        String precioStr = "";
        boolean precioValido = false;
        while (!precioValido) {
            System.out.print("Precio del vinilo: ");
            precioStr = scanner.nextLine().trim().replace(",", ".");
            if (precioStr.matches("\\d+(\\.\\d+)?")) {
                try {
                    double precio = Double.parseDouble(precioStr);
                    if (precio >= 0) {
                        precioValido = true;
                    } else {
                        System.out.println("Error: El precio no puede ser negativo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: El número ingresado es demasiado grande.");
                }
            } else {
                System.out.println("Error: Ingresa un precio numérico válido (ej. 450.50).");
            }
        }

        wishlist[totalDeseos][0] = ListaUsuarios[usuarioActual][1];
        wishlist[totalDeseos][1] = album;
        wishlist[totalDeseos][2] = precioStr;
        totalDeseos++;

        System.out.println("¡Disco agregado a tu wishlist con éxito!");
    }
   public static void gestionarUsuariosAdmin() {
        System.out.println("--- GESTIÓN DE USUARIOS REGISTRADOS ---");
        System.out.printf("%-20s | %-25s | %-15s\n", "Nombre", "Correo", "Rol");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < contadorUsuarios; i++) {
            String rolStr = ListaUsuarios[i][2].equals("2") ? "Administrador" : "Cliente";
            System.out.printf("%-20s | %-25s | %-15s\n",
                    ListaUsuarios[i][0],
                    ListaUsuarios[i][1],
                    rolStr);
        }
        System.out.println("---------------------------------------------------------------");
    }
}


// Tratar de hacer los siguientes modulos en este mismo archivo
// para asi verificar la compatibilidad entre todos los modulos
