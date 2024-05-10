package com.example.donnaflor;



public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int tipo;
    private int estado;
    private int imagen;
    private int cantidadSeleccionada; // Nuevo campo para la cantidad seleccionada

    public Producto(int id, String nombre, double precio, int tipo, int estado, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.estado = estado;
        this.imagen = imagen;
        this.cantidadSeleccionada = 0; // Inicializar la cantidad seleccionada en 0
    }

    // Getters y setters para todos los campos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }
}
