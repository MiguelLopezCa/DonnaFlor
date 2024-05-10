package com.example.donnaflor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Producto> listaItems;
    private Context context;
    private OnItemClickListener listener;
    private List<Integer> cantidadesSeleccionadas; // Lista de contadores para cada producto


    public MyAdapter(List<Producto> listaItems, Context context) {
        this.listaItems = listaItems;
        this.context = context;
        this.cantidadesSeleccionadas = new ArrayList<>(); // Inicializar la lista de contadores
        for (int i = 0; i < listaItems.size(); i++) {
            cantidadesSeleccionadas.add(0); // Inicializar todos los contadores en 0
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Asignar datos a las vistas del ViewHolder
        Producto producto = listaItems.get(position);
        holder.txtNombre.setText(producto.getNombre());
        holder.txtprecio.setText(String.valueOf(producto.getPrecio()));
        holder.imgFoto.setImageResource(producto.getImagen());
        holder.txtCantidadSeleccionada.setText(String.valueOf(producto.getCantidadSeleccionada()));

        // Establecer el evento de clic para el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition(); // Obtener la posición actual del elemento
                if (listener != null) {
                    listener.onItemClick(clickedPosition);
                    int nuevaCantidad = cantidadesSeleccionadas.get(clickedPosition) + 1; // Incrementar el contador
                    cantidadesSeleccionadas.set(clickedPosition, nuevaCantidad); // Actualizar el contador en la lista
                    notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

