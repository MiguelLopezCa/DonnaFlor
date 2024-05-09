package com.example.donnaflor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductosOfertados extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;//Toolbar para el menú


    ImageButton btnPlatos , btnBebidas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_ofertados);
        //
        toolbar = findViewById(R.id.include_tollbar);
        setSupportActionBar(toolbar);
        // relacionar layout em clss

        btnBebidas=findViewById(R.id.btnBebidas);
        btnPlatos =findViewById(R.id.btnPlatos);

        btnPlatos.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaProductos.class);
            intent.putExtra("TIPO_PRODUCTO", 0); // 0 para platos
            startActivity(intent);
        });
        btnBebidas.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaProductos.class);
            intent.putExtra("TIPO_PRODUCTO", 1); // 1 para bebidas
            startActivity(intent);
        });
    }
    //Visualización del menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.config_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Click en opciones de menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Menu){
            startActivity(new Intent(this,ProductosRes.class));
        }
        return super.onOptionsItemSelected(item);
    }



}