package Entidades;

import java.util.ArrayList;

public class Juego {
    private String [][] tablero = new String [6][7];
    private ArrayList<Jugador> jugadores;
    private int [][] fichasGanador = new int [2][4];


    public Juego() {
        jugadores = new ArrayList();
    }

    public Juego(String[][] tablero, ArrayList<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public String[][] getTablero() {
        return tablero;
    }

    public void setTablero(String[][] tablero) {
        this.tablero = tablero;
    }
}
