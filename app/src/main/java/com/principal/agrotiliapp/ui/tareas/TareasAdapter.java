package com.principal.agrotiliapp.ui.tareas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.agrotiliapp.R;

import com.principal.agrotiliapp.clases.Tareas;
import com.principal.agrotiliapp.request.ApiClient;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder> {
    private List<Tareas> listaTareas;
    private Context context;

    public TareasAdapter(List<Tareas> listaTareas, Context context) {
        this.listaTareas = listaTareas;
        this.context = context;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        Tareas t=listaTareas.get(position);
        holder.tvIdTarea.setText(t.getId_tarea()+"");
        holder.tvNombreCampoItemTarea.setText(t.getCampo().getNombre_campo());
        holder.tvTipoTareaItemTarea.setText(t.getTipo_Tarea().getNombre_tipo_tarea());
        holder.tvMaquinaAgrariaItemTarea.setText(t.getMaquina_Agraria().getPatente());
        String operario=t.getEmpleado().getNombre()+"  "+ t.getEmpleado().getApellido();
        holder.tvOperarioItemTarea.setText(operario);
        holder.tvFechaInicioItemTarea.setText(t.getFecha_inicio());
        if(t.getFecha_fin()==null){
            holder.tvFechaFinItemTarea.setText("La Tarea no esta finalizada");
        }else{
            holder.tvFechaFinItemTarea.setText(t.getFecha_fin());
        }
        if(t.getObservaciones()==null){
            holder.tvObservacionItemTarea.setText("No contiene");
        }else{
            holder.tvObservacionItemTarea.setText(t.getObservaciones());
        }
        holder.cardView.setOnClickListener(v->{
            ApiClient.guardarObjeto(context,"tarea",t);
            Bundle bundle=new Bundle();
            bundle.putSerializable("tarea",t);
            Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.tareaSeleccionadaFragment,bundle);
        });

    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public class TareasViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdTarea, tvNombreCampoItemTarea, tvTipoTareaItemTarea,tvMaquinaAgrariaItemTarea,
                tvOperarioItemTarea,tvFechaInicioItemTarea,
                tvFechaFinItemTarea,tvObservacionItemTarea;
        private CardView cardView;
        public TareasViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardTarea);
            tvIdTarea=itemView.findViewById(R.id.tvIdTareaItemTarea);
            tvNombreCampoItemTarea=itemView.findViewById(R.id.tvNombreCampoItemTarea);
            tvTipoTareaItemTarea=itemView.findViewById(R.id.tvTipoTareaItemTarea);
            tvMaquinaAgrariaItemTarea=itemView.findViewById(R.id.tvMaquinaAgrariaItemTarea);
            tvOperarioItemTarea=itemView.findViewById(R.id.tvOperarioItemTarea);
            tvFechaInicioItemTarea=itemView.findViewById(R.id.tvFechaInicioItemTarea);
            tvFechaFinItemTarea=itemView.findViewById(R.id.tvFechaFinItemTarea);
            tvObservacionItemTarea=itemView.findViewById(R.id.tvObservacionItemTarea);

        }
    }
}
