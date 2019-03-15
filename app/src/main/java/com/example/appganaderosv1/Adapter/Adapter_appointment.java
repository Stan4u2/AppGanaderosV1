package com.example.appganaderosv1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Persona;

import java.util.ArrayList;

public class Adapter_appointment extends RecyclerView.Adapter<Adapter_appointment.ViewHolderAppointment> implements View.OnClickListener{

    ArrayList<Persona> listaPersona;
    ArrayList<Citas> listaCitas;
    private View.OnClickListener listener;

    public Adapter_appointment(ArrayList<Persona> listaPersona, ArrayList<Citas> listaCitas) {
        this.listaPersona = listaPersona;
        this.listaCitas = listaCitas;
    }

    @NonNull
    @Override
    public Adapter_appointment.ViewHolderAppointment onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_citas, null, false);

        view.setOnClickListener(this);

        return new ViewHolderAppointment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_appointment.ViewHolderAppointment holder, int i) {
        holder.nombre_persona.setText(listaPersona.get(i).getNombre());
        holder.cantidad_ganado.setText(listaCitas.get(i).getCantidad_ganado());
        holder.fecha.setText(listaCitas.get(i).getFecha());
    }

    @Override
    public int getItemCount() {
        return listaCitas.size();
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

    public class ViewHolderAppointment extends RecyclerView.ViewHolder {

        TextView nombre_persona, cantidad_ganado, fecha;

        public ViewHolderAppointment(@NonNull View itemView) {
            super(itemView);
            nombre_persona = itemView.findViewById(R.id.nombre_persona);
            cantidad_ganado = itemView.findViewById(R.id.cantidad_ganado);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }
}
