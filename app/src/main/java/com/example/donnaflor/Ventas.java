package com.example.donnaflor;

public class Ventas {

    private int id;

    private String fecha;

// metodo constructor

    public Ventas(int id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }


    // metodos getter and Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
