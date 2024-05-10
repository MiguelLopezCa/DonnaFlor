package com.example.donnaflor;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView txtNombre, txtprecio,txtCantidadSeleccionada;
    ImageView imgFoto;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        //Relación con elementos del layout
        txtNombre = itemView.findViewById(R.id.txtNombre);
        txtprecio = itemView.findViewById(R.id.txtprecio);
        imgFoto = itemView.findViewById(R.id.imgFoto);
        txtCantidadSeleccionada = itemView.findViewById(R.id.txtCantidadSeleccionada); // Inicializa el campo txtCantidadSeleccionada

    }
}
