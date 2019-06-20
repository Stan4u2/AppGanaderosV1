package com.example.appganaderosv1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Raza;

import java.util.ArrayList;

public class Adapter_animals extends RecyclerView.Adapter<Adapter_animals.ViewHolderAnimals> implements View.OnClickListener {

    ArrayList<CompraDetalle> listaCompraDetalle;
    ArrayList<Ganado> listaGanado;
    ArrayList<Raza> listaRaza;

    private View.OnClickListener listener;

    public Adapter_animals(ArrayList<CompraDetalle> listaCompraDetalle, ArrayList<Ganado> listaGanado, ArrayList<Raza> listaRaza) {
        this.listaCompraDetalle = listaCompraDetalle;
        this.listaGanado = listaGanado;
        this.listaRaza = listaRaza;
    }

    @NonNull
    @Override
    public ViewHolderAnimals onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_animals_purchase, null, false);
        view.setOnClickListener(this);

        return new ViewHolderAnimals(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnimals holder, int i) {
        holder.tipo_ganado.setText(listaGanado.get(i).getTipo_ganado());
        holder.raza_ganado.setText(listaRaza.get(i).getTipo_raza());
        if((listaCompraDetalle.get(i).getPeso_pie_compra().toString().equals("0.0"))&&(listaCompraDetalle.get(i).getPeso_canal_compra().toString().equals("0.0"))){
            holder.peso_ganado.setText(listaCompraDetalle.get(i).getPeso_pie_compra().toString());
        }else if (!listaCompraDetalle.get(i).getPeso_pie_compra().toString().equals("0.0")){
            holder.peso_ganado.setText(listaCompraDetalle.get(i).getPeso_pie_compra().toString());
        }else if(!listaCompraDetalle.get(i).getPeso_canal_compra().toString().equals("0.0")){
            holder.peso_ganado.setText(listaCompraDetalle.get(i).getPeso_canal_compra().toString());
        }
    }

    @Override
    public int getItemCount() {
        return listaCompraDetalle.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolderAnimals extends RecyclerView.ViewHolder {

        TextView tipo_ganado, raza_ganado, peso_ganado;

        public ViewHolderAnimals(@NonNull View itemView) {
            super(itemView);

            tipo_ganado = itemView.findViewById(R.id.tipo_ganado);
            raza_ganado = itemView.findViewById(R.id.raza_ganado);
            peso_ganado = itemView.findViewById(R.id.peso_ganado);
        }
    }
}
