package com.example.donnaflor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
private List<Producto> listaItems;
private Context context;

    public MyAdapter(List<Producto> listaItems, Context context) {
        this.listaItems = listaItems;
        this.context = context;
    }

    public List<Producto> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<Producto> listaItems) {
        this.listaItems = listaItems;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflación o haciendo visible el layout por cada registro
        return new MyViewHolder(LayoutInflater.from(context).inflate(
            R.layout.layout, parent,false));
        //siemre va ser falso el boolean
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//Relación de elementos del layout con la lista
        holder.txtNombre.setText(listaItems.get(position).getNombre());
        holder.txtprecio.setText(String.valueOf(listaItems.get(position).getPrecio()));
        holder.imgFoto.setImageResource(listaItems.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }
}
