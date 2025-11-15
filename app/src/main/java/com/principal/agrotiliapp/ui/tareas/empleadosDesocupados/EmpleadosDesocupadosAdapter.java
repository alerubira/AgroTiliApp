package com.principal.agrotiliapp.ui.tareas.empleadosDesocupados;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.clases.Empleados;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.ui.tareas.campos.CamposAdapter;

import java.util.List;

public class EmpleadosDesocupadosAdapter extends RecyclerView.Adapter<EmpleadosDesocupadosAdapter.EmpleadosDesocupadosViewHolder>{
    private List<Empleados> listaEmpleados;
    private Context context;

    public EmpleadosDesocupadosAdapter(List<Empleados> listaEmpleados, Context context) {
        this.listaEmpleados = listaEmpleados;
        this.context = context;
    }

    @NonNull
    @Override

    public EmpleadosDesocupadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleado, parent, false);
        return new EmpleadosDesocupadosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadosDesocupadosViewHolder holder, int position) {
         Empleados e=listaEmpleados.get(position);
         holder.tvIdEmpleado.setText(e.getId_empleado()+"");
         holder.tvApellidoEmpleado.setText(e.getApellido());
         holder.tvNombreEmpleado.setText(e.getNombre());
         holder.tvFechaIngreso.setText(e.getFecha_egreso());
         holder.tvRol.setText(e.getNombre_role());
         holder.cardView.setOnClickListener(v->{
             ApiClient.guardarObjeto(context,"empleado",e);
             Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_tareas);
         });
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }

    public class EmpleadosDesocupadosViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIdEmpleado, tvNombreEmpleado, tvApellidoEmpleado,tvFechaIngreso,tvRol;
        private CardView cardView;
        public EmpleadosDesocupadosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardEmpleado);
            tvIdEmpleado=itemView.findViewById(R.id.tvIdEmpleado);
            tvApellidoEmpleado=itemView.findViewById(R.id.tvApellido);
            tvNombreEmpleado=itemView.findViewById(R.id.tvNombre);
            tvFechaIngreso=itemView.findViewById(R.id.tvFechaIngreso);
            tvRol=itemView.findViewById(R.id.tvRol);
        }
    }
}
