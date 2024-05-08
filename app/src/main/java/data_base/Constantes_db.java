package data_base;

public class Constantes_db {

    // Nombre de la base de datos
    public static final String NOMBRE_BASE_DATOS = "donnaFlor.db";

    // Versión de la base de datos
    public static final int VERSION_BASE_DATOS = 1;

    // Tabla de productos
    public static final String TABLA_PRODUCTOS = "PRODUCTOS";
    public static final String CREAR_TABLA_PRODUCTOS = "CREATE TABLE " + TABLA_PRODUCTOS + " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NOMBRE TEXT," +
            "PRECIO REAL," +
            "TIPO INTEGER," + // 0 para platos, 1 para bebidas
            "ESTADO INTEGER)"; // 0 para inactivo, 1 para activo

    // Tabla de ventas
    public static final String TABLA_VENTAS = "VENTAS";
    public static final String CREAR_TABLA_VENTAS = "CREATE TABLE " + TABLA_VENTAS + " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "PRODUCTO_ID INTEGER," +
            "FECHA TEXT," +
            "CANTIDAD INTEGER)";

    // Comandos para eliminar las tablas
    public static final String ELIMINAR_TABLA_PRODUCTOS = "DROP TABLE IF EXISTS " + TABLA_PRODUCTOS;
    public static final String ELIMINAR_TABLA_VENTAS = "DROP TABLE IF EXISTS " + TABLA_VENTAS;

}


