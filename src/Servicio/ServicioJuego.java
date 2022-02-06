package Servicio;

import Entidades.Juego;
import Entidades.Jugador;
import java.util.Scanner;
import java.io.*;

public class ServicioJuego {
    public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_CYAN = "\u001B[36m";

  public static void menu() throws InterruptedException {
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    String opcion;

    do {
      LimpiarPantalla.instantCleanScreen();
      System.out.print(ANSI_RED + "Conecta 4" + ANSI_RESET
          + "\n\n1. Iniciar juego\n2. Cómo jugar\n3. Créditos\nS. Salir\n\nEliga una opción: ");
      opcion = leer.next().toUpperCase();

      switch (opcion) {
        case "1":
          juego();
          break;
        case "2":
          LimpiarPantalla.instantCleanScreen();
          System.out.println(ANSI_RED + "Cómo jugar\n\nReglas" + ANSI_RESET
              + "\n\nAl comenzar el juego, a cada jugador le será asignada una ficha con la que jugará (" + ANSI_RED
              + "O" + ANSI_RESET + " ó " + ANSI_CYAN + "X" + ANSI_RESET
              + "). El jugador debe conectar 4 de sus fichas, ya sea en forma horizontal, vertical o diagonal. El primero que logre este resultado, gana la partida.\nSi se agotan todos los espacios del tablero y ningun jugador ha logrado su meta, se considera un empate.\n\n" + ANSI_RED + "Controles" + ANSI_RESET +"\n\nEn cada turno, el jugador debe escribir el número de la columna que se indica en la parte superior del tablero y presionar 'ENTER' para confirmar la elección. Acto seguido, la ficha caerá al fondo del tablero o encima de otra ficha siempre que la columna seleccionada tenga espacio disponible.");
          

          Thread.sleep(3000);
          LimpiarPantalla.cleanScreen();
          break;
        case "3":
          LimpiarPantalla.instantCleanScreen();
          impresoraDeFrases(ANSI_RED + "Créditos" + ANSI_RESET
              + "\n\nProgramador: Christian Bergometti\nTesters e Improvers: Maritta y Samuel Patiño\nProfe y apoyo moral: Gisela Galaburrí\n");
          Thread.sleep(3000);
          LimpiarPantalla.cleanScreen();
          break;
        case "S":
          impresoraDeFrases("\nGracias por jugar! :D\n");
          break;
        default:
          System.out.println("\nLa opción ingresada no es correcta.");
          Thread.sleep(3000);
          break;
      }
    } while (!opcion.equals("S"));

  }

  public static void juego() throws InterruptedException {
    Juego juego = new Juego();
    crearJugadores(juego);
    crearTablero(juego);
    partida(juego);
  }

  public static void crearJugadores(Juego juego) throws InterruptedException {
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    String respuesta;
    int ficha;

    for (int i = 0; i < 2; i++) {
      Jugador jugador = new Jugador();
      juego.getJugadores().add(jugador);

      do {
        do {
          LimpiarPantalla.instantCleanScreen();
          impresoraDeFrases("Hola jugador " + (i + 1) + "! Ingrese su nombre: ");
          juego.getJugadores().get(i).setNombre(leer.next());

          if (i == 1
              && jugador.getNombre().toUpperCase().equals(juego.getJugadores().get(0).getNombre().toUpperCase())) {
            System.out.println("\nEl nombre " + jugador.getNombre() + ANSI_RED + " ya está en uso" + ANSI_RESET +". Ingrese un nombre distinto.");

            Thread.sleep(3000);
          }

        } while (juego.getJugadores().get(i).getNombre().isEmpty() || (i == 1
            && jugador.getNombre().toUpperCase().equals(juego.getJugadores().get(0).getNombre().toUpperCase())));

        impresoraDeFrases("\nEl nombre ingresado es " + jugador.getNombre() + ". Es correcto? (s/n): ");
        respuesta = leer.next().toUpperCase();

        if (!respuesta.equals("S") && !respuesta.equals("N")) {
          System.out.println("\nDebe elegir una opción correcta.");
          Thread.sleep(3000);
        }

      } while (!respuesta.equals("S"));
    }
    
    LimpiarPantalla.instantCleanScreen();

    ficha = (int) (Math.random() * (10) + 1);

    if (ficha % 2 == 0) {
      juego.getJugadores().get(0).setFicha(ANSI_RED + "O" + ANSI_RESET);
      juego.getJugadores().get(1).setFicha(ANSI_CYAN + "X" + ANSI_RESET);
    } else {
      juego.getJugadores().get(0).setFicha(ANSI_CYAN + "X" + ANSI_RESET);
      juego.getJugadores().get(1).setFicha(ANSI_RED + "O" + ANSI_RESET);
    }

    impresoraDeFrases(juego.getJugadores().get(0).getNombre() + " jugará con las fichas '"
        + juego.getJugadores().get(0).getFicha() + "'.\n");
    Thread.sleep(500);
    impresoraDeFrases("\n" + juego.getJugadores().get(1).getNombre() + " jugará con las fichas '"
        + juego.getJugadores().get(1).getFicha() + "'.\n");

    Thread.sleep(1500);
    LimpiarPantalla.cleanScreenContinuar();

  }

  public static void crearTablero(Juego juego) {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        juego.getTablero()[i][j] = " ";
      }
    }
  }

  public static void mostrarTablero(Juego juego) {
    for (int i = -1; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        if (i == -1) {
          System.out.print(" " + j + " ");
        } else {
          System.out.print("[" + juego.getTablero()[i][j] + "]");
        }
      }
      System.out.println();
    }
  }

  public static void partida(Juego juego) throws InterruptedException {
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    String columnaString;
    int columna = 0;

    while (!juego.getJugadores().get(0).getGanador() && !juego.getJugadores().get(1).getGanador()
        && !comprobarEmpate(juego)) {
      for (int i = 0; i < 2; i++) {
        boolean columnaValida = false;

        do {
          mostrarTablero(juego);
          System.out.println("\nTurno de " + juego.getJugadores().get(i).getNombre() + ". Fichas '"
              + juego.getJugadores().get(i).getFicha() + "'.");

          System.out.print("Indique la columna donde colocar la ficha: ");
          columnaString = leer.next();

          if (!columnaString.equals("0") && !columnaString.equals("1") && !columnaString.equals("2")
              && !columnaString.equals("3") && !columnaString.equals("4") && !columnaString.equals("5")
              && !columnaString.equals("6")) {
            System.out.println("\nDebe indicar una columna válida.");
            Thread.sleep(3000);
            LimpiarPantalla.instantCleanScreen();
          } else {
            columna = Integer.valueOf(columnaString);
            columnaValida = true;
          }

        } while (!columnaValida);
        LimpiarPantalla.instantCleanScreen();
        insertarFicha(juego, columna, juego.getJugadores().get(i));
        mostrarTablero(juego);
        LimpiarPantalla.instantCleanScreen();

        confirmar4EnLinea(juego);

        if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
          mostrarTablero(juego);
          anunciarGanador(juego);
          break;
        } else {
          if (comprobarEmpate(juego)) {
            mostrarTablero(juego);
            impresoraDeFrases("\nEMPATE!\n");
            break;
          }
        }
      }
    }

    Thread.sleep(2000);
    LimpiarPantalla.cleanScreen();
  }

  public static void insertarFicha(Juego juego, int columna, Jugador jugador) throws InterruptedException {
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    for (int i = 5; i >= 0; i--) {

      while (!juego.getTablero()[0][columna].equals(" ")) {
        boolean columnaValida = false;

        do {
          mostrarTablero(juego);
          String columnaString;
          System.out
              .print("\n" + jugador.getNombre() + ", la columna indicada está llena. Ingrese una columna distinta: ");

          columnaString = leer.next();

          if (!columnaString.equals("0") && !columnaString.equals("1") && !columnaString.equals("2")
              && !columnaString.equals("3") && !columnaString.equals("4") && !columnaString.equals("5")
              && !columnaString.equals("6")) {
            System.out.println("\nDebe indicar una columna válida.");
            Thread.sleep(3000);
            LimpiarPantalla.instantCleanScreen();
          } else {
            columna = Integer.valueOf(columnaString);
            columnaValida = true;
          }
        } while (!columnaValida);

        LimpiarPantalla.instantCleanScreen();
      }

      if (juego.getTablero()[i][columna].equals(" ")) {
        juego.getTablero()[i][columna] = jugador.getFicha();
        break;
      }
    }

  }

  public static void confirmar4EnLinea(Juego juego) {
    int FichasO = 0;
    int FichasX = 0;

    leerHorizontal(juego, FichasO, FichasX);
    leerVertical(juego, FichasO, FichasX);
    leerDiagonalPrincipal(juego, FichasO, FichasX);
    leerDiagonalInversa(juego, FichasO, FichasX);
  }

  public static void leerHorizontal(Juego juego, int FichasO, int FichasX) {

    for (int i = 0; i < 6; i++) { // bucle para leer horizontalmente
      for (int j = 0; j < 7; j++) {

        if (j == 0 && (FichasO != 4 && FichasX != 4)) {
          FichasO = 0;
          FichasX = 0;
        }

        if (juego.getTablero()[i][j].equals(juego.getJugadores().get(0).getFicha())) {
          FichasO++;
        } else if (!juego.getTablero()[i][j].equals(juego.getJugadores().get(0).getFicha())) {
          FichasO = 0;
        }

        if (juego.getTablero()[i][j].equals(juego.getJugadores().get(1).getFicha())) {
          FichasX++;
        } else if (!juego.getTablero()[i][j].equals(juego.getJugadores().get(1).getFicha())) {
          FichasX = 0;
        }

        if (FichasO == 4) {
          juego.getJugadores().get(0).setGanador(true);
          break;
        } else if (FichasX == 4) {
          juego.getJugadores().get(1).setGanador(true);
          break;
        }

      }

      if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
        break;
      }
    }
  }

  public static void leerVertical(Juego juego, int FichasO, int FichasX) {
    for (int i = 0; i < 7; i++) { // bucle para leer verticalmente

      for (int j = 0; j < 6; j++) {

        if (j == 0 && (FichasO != 4 && FichasX != 4)) {
          FichasO = 0;
          FichasX = 0;
        }

        if (juego.getTablero()[j][i].equals(juego.getJugadores().get(0).getFicha())) {
          FichasO++;
        } else if (!juego.getTablero()[j][i].equals(juego.getJugadores().get(0).getFicha())) {
          FichasO = 0;
        }

        if (juego.getTablero()[j][i].equals(juego.getJugadores().get(1).getFicha())) {
          FichasX++;
        } else if (!juego.getTablero()[j][i].equals(juego.getJugadores().get(1).getFicha())) {
          FichasX = 0;
        }

        if (FichasO == 4) {
          juego.getJugadores().get(0).setGanador(true);
          break;
        } else if (FichasX == 4) {
          juego.getJugadores().get(1).setGanador(true);
          break;
        }

      }

      if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
        break;
      }

    }
  }

  public static void leerDiagonalPrincipal(Juego juego, int FichasO, int FichasX) {
    for (int i = 0; i < 3; i++) { // bucle para leer diagonal principal
      for (int j = 0; j < 4; j++) {
        for (int k = i; k < i + 4; k++) {
          for (int l = j; l < j + 4; l++) {

            if ((k - i == 0 && l - j == 0) && (FichasO != 4 && FichasX != 4)) {
              FichasO = 0;
              FichasX = 0;
            }

            if ((k - i == l - j) && juego.getTablero()[k][l].equals(juego.getJugadores().get(0).getFicha())) {
              FichasO++;
            } else if ((k - i == l - j) && !juego.getTablero()[k][l].equals(juego.getJugadores().get(0).getFicha())) {
              FichasO = 0;
            }

            if ((k - i == l - j) && juego.getTablero()[k][l].equals(juego.getJugadores().get(1).getFicha())) {
              FichasX++;
            } else if ((k - i == l - j) && !juego.getTablero()[k][l].equals(juego.getJugadores().get(1).getFicha())) {
              FichasX = 0;
            }

            if (FichasO == 4) {
              juego.getJugadores().get(0).setGanador(true);
              break;
            } else if (FichasX == 4) {
              juego.getJugadores().get(1).setGanador(true);
              break;
            }

          }

          if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
            break;
          }
        }

        if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
          break;
        }
      }

      if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
        break;
      }
    }

  }

  public static void leerDiagonalInversa(Juego juego, int FichasO, int FichasX) {
    for (int i = 0; i < 3; i++) { // bucle para leer diagonal inversa
      for (int j = 0; j < 4; j++) {
        for (int k = i; k < i + 4; k++) {
          for (int l = j + 3; l >= j; l--) {

            if ((k == i && l == j + 3) && (FichasO != 4 && FichasX != 4)) {
              FichasO = 0;
              FichasX = 0;
            }

            if ((((k - i) + (l - j)) == 3) && juego.getTablero()[k][l].equals(juego.getJugadores().get(0).getFicha())) {
              FichasO++;
            } else if ((((k - i) + (l - j)) == 3)
                && !juego.getTablero()[k][l].equals(juego.getJugadores().get(0).getFicha())) {
              FichasO = 0;
            }

            if ((((k - i) + (l - j)) == 3) && juego.getTablero()[k][l].equals(juego.getJugadores().get(1).getFicha())) {
              FichasX++;
            } else if ((((k - i) + (l - j)) == 3)
                && !juego.getTablero()[k][l].equals(juego.getJugadores().get(1).getFicha())) {
              FichasX = 0;
            }

            if (FichasO == 4) {
              juego.getJugadores().get(0).setGanador(true);
              break;
            } else if (FichasX == 4) {
              juego.getJugadores().get(1).setGanador(true);
              break;
            }

          }

          if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
            break;
          }
        }

        if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
          break;
        }
      }

      if (juego.getJugadores().get(0).getGanador() || juego.getJugadores().get(1).getGanador()) {
        break;
      }
    }

  }

  public static boolean comprobarEmpate(Juego juego) {
    boolean empate = true;

    for (int i = 0; i < 7; i++) {
      if (juego.getTablero()[0][i].equals(" ")) {
        empate = false;
      }
    }

    return empate;
  }

  public static void anunciarGanador(Juego juego) throws InterruptedException {
    if (juego.getJugadores().get(0).getGanador()) {
      impresoraDeFrases("\n" + juego.getJugadores().get(0).getNombre() + ", has ganado!\n");
    } else {
      impresoraDeFrases("\n" + juego.getJugadores().get(1).getNombre() + ", has ganado! \n");
    }
  }

  public static void impresoraDeFrases(String frase) throws InterruptedException {
    for (int i = 0; i < frase.length(); i++) {
      System.out.print(frase.substring(i, i + 1));
      Thread.sleep(50);
    }
  }
}
