package com.example.donnaflor;



public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int tipo; // 0 para platos, 1 para bebidas
    private int estado; // 0 para inactivo, 1 para activo
    private int imagen;
    ///Constructor


    public Producto(int id, String nombre, double precio, int tipo, int estado,int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.estado = estado;
        this.imagen = imagen;
    }

    //metodos getter and setter


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

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

}
