package com.example.appganaderosv1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Persona;

import java.util.ArrayList;

public class Adapter_purchases extends RecyclerView.Adapter<Adapter_purchases.ViewHolderPurchases> implements View.OnClickListener {

    ArrayList<Persona> listaPersonas;
    ArrayList<Compras> listaCompras;

    private View.OnClickListener listener;

    public Adapter_purchases(ArrayList<Persona> listaPersonas, ArrayList<Compras> listaCompras) {
        this.listaPersonas = listaPersonas;
        this.listaCompras = listaCompras;
    }

    @NonNull
    @Override
    public Adapter_purchases.ViewHolderPurchases onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_purchases, null, false);

        view.setOnClickListener(this);

        return new ViewHolderPurchases(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_purchases.ViewHolderPurchases holder, int i) {
        holder.nombre_persona.setText(listaPersonas.get(i).getNombre());
        holder.cantidad_ganado.setText(listaCompras.get(i).getCantidad_animales_compra().toString());
        holder.fecha.setText(listaCompras.get(i).getFecha_compra());
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
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

    public class ViewHolderPurchases extends RecyclerView.ViewHolder {

        TextView nombre_persona, cantidad_ganado, fecha;

        public ViewHolderPurchases(@NonNull View itemView) {
            super(itemView);

            nombre_persona = itemView.findViewById(R.id.nombre_persona);
            cantidad_ganado = itemView.findViewById(R.id.cantidad_ganado);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }
}
