package com.example.donnaflor;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import data_base.Constantes_db;
import data_base.db_helper;

public class ProductosRes extends AppCompatActivity {

    EditText txtNombreProd, txtPrecioProd;
    Spinner spnTipoProdu, spnEstadoProd;
    Button btnGuardarProd, btnRegresarProducto;
    ListView lvListaProductos;
    ArrayList<Producto> arrayProductos;
    ArrayList<String> arrayTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_res);

        // Relación con elementos del layout
        txtNombreProd = findViewById(R.id.txtNombreProd);
        txtPrecioProd = findViewById(R.id.txtPrecioProd);
        spnTipoProdu = findViewById(R.id.spnTipoProdu);
        spnEstadoProd = findViewById(R.id.spnEstadoProd);
        btnGuardarProd = findViewById(R.id.btnGuardarProd);
        btnRegresarProducto = findViewById(R.id.btnRegresarProducto);
        lvListaProductos = findViewById(R.id.lvListaProductos);


        // Configuración del Spinner de tipo de producto
        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_producto, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoProdu.setAdapter(tipoAdapter);

        // Configuración del Spinner de estado de producto
        ArrayAdapter<CharSequence> estadoAdapter = ArrayAdapter.createFromResource(this,
                R.array.estados_producto, android.R.layout.simple_spinner_item);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstadoProd.setAdapter(estadoAdapter);
        // llamar el metodo de listar productos
        listarProductos();
        // Botón para guardar el producto
        btnGuardarProd.setOnClickListener(v -> {
            agregarProducto();
        });

        // Botón para regresar
        btnRegresarProducto.setOnClickListener(v -> {
            startActivity(new Intent(this, ProductosOfertados.class));
        });

        lvListaProductos.setOnItemClickListener((parent, view, position, id) -> {
            Producto productoSeleccionado = arrayProductos.get(position);
            int nuevoEstado = productoSeleccionado.getEstado() == 0 ? 1 : 0; // Cambiar estado

            // Actualizar el estado en la base de datos
            actualizarEstadoProducto(productoSeleccionado.getId(), nuevoEstado);

            // Actualizar la lista de productos
            listarProductos();
        });

    }

    private void agregarProducto() {
        // Validar que los campos no estén vacíos
        if (camposVacios()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Conexión con la base de datos
            db_helper dbHelper = new db_helper(this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            // Crear un objeto ContentValues para almacenar los valores del producto
            ContentValues values = new ContentValues();
            values.put("NOMBRE", txtNombreProd.getText().toString());
            values.put("PRECIO", Double.parseDouble(txtPrecioProd.getText().toString()));
            values.put("TIPO", spnTipoProdu.getSelectedItemPosition());
            values.put("ESTADO", spnEstadoProd.getSelectedItemPosition());

            // Insertar el producto en la base de datos
            long id = database.insert(Constantes_db.TABLA_PRODUCTOS, null, values);

            if (id != -1) {
                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
            }

            // Cerrar la conexión con la base de datos
            database.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayProductos);
        lvListaProductos.setAdapter(adapter);
        listarProductos();
    }

    private void limpiarCampos() {
        txtNombreProd.setText("");
        txtPrecioProd.setText("");
        spnTipoProdu.setSelection(0);
        spnEstadoProd.setSelection(0);
    }
    private boolean camposVacios() {
        return txtNombreProd.getText().toString().isEmpty() || txtPrecioProd.getText().toString().isEmpty();
    }

    private void listarProductos() {
        arrayProductos = new ArrayList<>();

        try {
            // Conexión
            db_helper dbHelper = new db_helper(this);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            // Cursor con el resultado de la consulta
            Cursor cursor = database.rawQuery("SELECT * FROM " +
                    Constantes_db.TABLA_PRODUCTOS, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    arrayProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), 0));
                }
                generarLista();
            } else {
                Toast.makeText(this, "No hay productos registrados", Toast.LENGTH_LONG).show();
            }

            // Cerrar cursor después de usarlo
            cursor.close();
            // Cerrar la conexión con la base de datos
            database.close();

        } catch (Exception ex) {
            Toast.makeText(this, "Error general: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String obtenerEstadoProducto(int estado) {
        return estado == 0 ? "Inactivo" : "Activo";
    }
    private void generarLista() {
        arrayTexto = new ArrayList<>();

        for (Producto producto : arrayProductos) {

            String estadoProducto = obtenerEstadoProducto(producto.getEstado());

            arrayTexto.add(producto.getNombre() + " $" + producto.getPrecio() + "-" + estadoProducto);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTexto);
        lvListaProductos.setAdapter(adapter);

        Log.i("array", "generarLista: " + arrayTexto.toString());
    }

    private void actualizarEstadoProducto(int idProducto, int nuevoEstado) {
        try {
            db_helper dbHelper = new db_helper(this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ESTADO", nuevoEstado);

            // Actualizar el estado del producto en la base de datos
            int filasActualizadas = database.update(Constantes_db.TABLA_PRODUCTOS, values,
                    "ID = ?", new String[]{String.valueOf(idProducto)});

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Estado del producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el estado del producto", Toast.LENGTH_SHORT).show();
            }

            database.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}
