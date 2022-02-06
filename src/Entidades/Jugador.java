package Entidades;

public class Jugador {
    private String nombre;
    private String ficha;
    private Boolean ganador = false;
    
    public Jugador() {
    }

    public Jugador(String nombre, String ficha, Boolean ganador) {
        this.nombre = nombre;
        this.ficha = ficha;
        this.ganador = ganador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public Boolean getGanador() {
        return ganador;
    }

    public void setGanador(Boolean ganador) {
        this.ganador = ganador;
    }
}
