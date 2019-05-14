package com.example.appganaderosv1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Persona;

import java.util.ArrayList;

public class Adapter_people extends RecyclerView.Adapter<Adapter_people.ViewHolderPeople> implements View.OnClickListener {

    ArrayList<Persona> listaPersona;

    private View.OnClickListener listener;

    public Adapter_people(ArrayList<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    @NonNull
    @Override
    public Adapter_people.ViewHolderPeople onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_people, null, false);

        view.setOnClickListener(this);

        return new ViewHolderPeople(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPeople holder, int i) {
        holder.nombre.setText(listaPersona.get(i).getNombre());
        holder.telefono.setText(listaPersona.get(i).getTelefono());
        holder.domicilio.setText(listaPersona.get(i).getDomicilio());
        holder.datos_extras.setText(listaPersona.get(i).getDatos_extras());
    }

    @Override
    public int getItemCount() {
        return listaPersona.size();
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

    public class ViewHolderPeople extends RecyclerView.ViewHolder {

        TextView nombre, telefono, domicilio, datos_extras;

        public ViewHolderPeople(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            telefono = itemView.findViewById(R.id.telefono);
            domicilio = itemView.findViewById(R.id.domicilio);
            datos_extras = itemView.findViewById(R.id.datos_extras);
        }
    }
}
