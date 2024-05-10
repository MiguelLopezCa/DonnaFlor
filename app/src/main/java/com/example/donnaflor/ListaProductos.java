package com.example.donnaflor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data_base.Constantes_db;
import data_base.db_helper;

public class ListaProductos extends AppCompatActivity {
    RecyclerView rcvVista;
    ArrayList<Producto> arrayProductos;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        arrayProductos = new ArrayList<>();

        // Reiniciar los contadores a 0 antes de cargar la lista de productos
        reiniciarCantidadesSeleccionadas();
        // Recibir el valor del tipo de producto del intent

        // -1 es un valor predeterminado en caso de que no se reciba el tipo correctamente
        int tipoDeProducto = getIntent().getIntExtra("TIPO_PRODUCTO", -1);
        consultarListaProductos(tipoDeProducto);

        ArrayList<Producto> alItems = new ArrayList<>();
        for (Producto producto : arrayProductos) {
            alItems.add(producto);
        }

        //Activación de reyclerview y asignación del adapter
        rcvVista = findViewById(R.id.recyclerView);
        rcvVista.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(alItems, getApplicationContext());
        rcvVista.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Aquí colocas el código que deseas ejecutar cuando se hace clic en un elemento del RecyclerView
            }
        });

        // Configuración del botón flotante
        FloatingActionButton floatButton = findViewById(R.id.btnFloatRegistrar);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                // Aquí colocas el código que deseas ejecutar cuando se hace clic en el FloatingActionButton
                List<Producto> productosSeleccionados = obtenerProductosSeleccionados();

                // Llamar al método registrarVenta() pasando la lista de productos seleccionados
                registrarVenta(productosSeleccionados);
                reiniciarCantidadesSeleccionadas();
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void consultarListaProductos(int tipoProducto) {
        try {
            //Conexión
            db_helper helper = new db_helper(this);
            //Objeto para leer los datos
            SQLiteDatabase data_base = helper.getReadableDatabase();

            //Cursor con el resultado de la consulta
            Cursor cursor = data_base.rawQuery("SELECT * FROM " + Constantes_db.TABLA_PRODUCTOS +
                    " WHERE TIPO = " + tipoProducto + " AND ESTADO = 1", null);


            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int imagen;
                    if (tipoProducto == 0) {
                        imagen = R.drawable.platos; // Asigna la imagen de plato
                    } else {
                        imagen = R.drawable.bebidas; // Asigna la imagen de bebida
                    }
                    arrayProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), imagen));
                }
            } else {
                Toast.makeText(this, "PRODUCTO INEXISTENTE", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Toast.makeText(this, "Error general " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrarVenta(List<Producto> productos) {
        try {
            // Obtener la fecha actual
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaActual = dateFormat.format(Calendar.getInstance().getTime());

            // Conexión a la base de datos
            db_helper dbHelper = new db_helper(this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            // Iniciar una transacción
            database.beginTransaction();

            try {
                // Crear un ContentValues para almacenar los datos de la venta
                ContentValues valuesVenta = new ContentValues();
                valuesVenta.put("FECHA", fechaActual); // Fecha de la venta

                // Insertar la venta en la tabla de ventas
                long idVenta = database.insert(Constantes_db.TABLA_VENTAS, null, valuesVenta);
                if (idVenta != -1) {
                    // Éxito al insertar la venta

                    // Recorrer la lista de productos y registrar la venta de cada producto en la tabla ventaProducto
                    for (Producto producto : productos) {
                        long idProducto = producto.getId(); // Obtener el ID del producto
                        int cantidad = producto.getCantidadSeleccionada(); // Obtener la cantidad seleccionada

                        // Crear un ContentValues para almacenar los datos de la venta de productos
                        ContentValues valuesVentaProducto = new ContentValues();
                        valuesVentaProducto.put("VENTA_ID", idVenta); // ID de la venta
                        valuesVentaProducto.put("PRODUCTO_ID", idProducto); // ID del producto
                        valuesVentaProducto.put("CANTIDAD", cantidad); // Cantidad vendida

                        // Insertar la venta de productos en la tabla ventaProducto
                        long idVentaProducto = database.insert(Constantes_db.TABLA_VENTAS_PRODUCTOS, null, valuesVentaProducto);
                        if (idVentaProducto == -1) {
                            // Error al insertar la venta de productos
                            Toast.makeText(this, "Error al registrar la venta de productos", Toast.LENGTH_SHORT).show();
                            // Cancelar la transacción y salir del método
                            database.endTransaction();
                            return;
                        }
                    }

                    // Establecer la transacción como exitosa
                    database.setTransactionSuccessful();
                    Toast.makeText(this, "Venta registrada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Error al insertar la venta
                    Toast.makeText(this, "Error al registrar la venta", Toast.LENGTH_SHORT).show();
                }
            } finally {
                // Finalizar la transacción
                database.endTransaction();
            }

            // Cerrar la conexión con la base de datos
            database.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al registrar la venta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener la lista de productos seleccionados
    private List<Producto> obtenerProductosSeleccionados() {
        List<Producto> productosSeleccionados = new ArrayList<>();
        for (Producto producto : arrayProductos) {
            if (producto.getCantidadSeleccionada() > 0) {
                productosSeleccionados.add(producto);
            }
        }
        return productosSeleccionados;
    }

    // Método para reiniciar todas las cantidades seleccionadas a cero
    private void reiniciarCantidadesSeleccionadas() {
        for (Producto producto : arrayProductos) {
            producto.setCantidadSeleccionada(0); // Reiniciar la cantidad seleccionada
        }
    }
}
