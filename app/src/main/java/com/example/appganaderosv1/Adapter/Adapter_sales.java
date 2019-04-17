package com.example.appganaderosv1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Ventas;

import java.util.ArrayList;

public class Adapter_sales extends RecyclerView.Adapter<Adapter_sales.ViewHolderSales> implements View.OnClickListener {

    ArrayList<Persona> listaPersonas;
    ArrayList <Ventas> listaVentas;

    private View.OnClickListener listener;

    public Adapter_sales(ArrayList<Persona> listaPersonas, ArrayList<Ventas> listaVentas){
        this.listaPersonas = listaPersonas;
        this.listaVentas = listaVentas;
    }

    @NonNull
    @Override
    public Adapter_sales.ViewHolderSales onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_sales, null, false);

        view.setOnClickListener(this);

        return new ViewHolderSales(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_sales.ViewHolderSales holder, int i) {
        holder.nombre_persona_venta.setText(listaPersonas.get(i).getNombre());
        holder.cantidad_ganado_venta.setText(listaVentas.get(i).getCantidad_animales().toString());
        holder.fecha_venta.setText(listaVentas.get(i).getFecha());
    }

    @Override
    public int getItemCount() {
        return listaVentas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderSales extends RecyclerView.ViewHolder {

        TextView nombre_persona_venta, cantidad_ganado_venta, fecha_venta;

        public ViewHolderSales(View itemView) {
            super(itemView);

            nombre_persona_venta = itemView.findViewById(R.id.nombre_persona_venta);
            cantidad_ganado_venta = itemView.findViewById(R.id.cantidad_ganado_venta);
            fecha_venta = itemView.findViewById(R.id.fecha_venta);
        }
    }
}
