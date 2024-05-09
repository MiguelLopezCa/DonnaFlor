package com.example.donnaflor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import data_base.Constantes_db;
import data_base.db_helper;

public class ListaProductos extends AppCompatActivity {
    RecyclerView rcvVista;
    ArrayList<Producto> arrayProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        arrayProductos = new ArrayList<>();

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
        rcvVista.setAdapter(new MyAdapter(alItems, getApplicationContext()));

    }
    private void consultarListaProductos(int tipoProducto) {
        try{
            //Conexión
            db_helper helper = new db_helper(this);
            //Objeto para leer los datos
            SQLiteDatabase data_base = helper.getReadableDatabase();

            //Cursor con el resultado de la consulta
            Cursor cursor = data_base.rawQuery("SELECT * FROM " + Constantes_db.TABLA_PRODUCTOS +
                    " WHERE TIPO = " + tipoProducto + " AND ESTADO = 1", null);


            if(cursor!=null && cursor.getCount()>0){
                while (cursor.moveToNext()){
                    arrayProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), R.drawable.bebidas));
                }
            }
            else{
                Toast.makeText(this,"PRODUCTO INEXISTENTE",Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception ex){
            Toast.makeText(this,"Error general " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


}