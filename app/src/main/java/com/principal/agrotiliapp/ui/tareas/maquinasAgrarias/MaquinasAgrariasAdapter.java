package com.principal.agrotiliapp.ui.tareas.maquinasAgrarias;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.agrotiliapp.R;
import com.principal.agrotiliapp.auxiliares.Navegacion;
import com.principal.agrotiliapp.clases.Maquinas_Agrarias;
import com.principal.agrotiliapp.request.ApiClient;
import com.principal.agrotiliapp.ui.tareas.campos.CamposAdapter;

import java.util.List;

public class MaquinasAgrariasAdapter extends RecyclerView.Adapter<MaquinasAgrariasAdapter.MaquinasAgrariasViewHolder> {
    private List<Maquinas_Agrarias> listaMaquinas;
    private Context context;

    public MaquinasAgrariasAdapter(List<Maquinas_Agrarias> listaMaquinas, Context context) {
        this.listaMaquinas = listaMaquinas;
        this.context = context;
    }

    @NonNull
    @Override
    public MaquinasAgrariasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquina, parent, false);
        return new MaquinasAgrariasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MaquinasAgrariasViewHolder holder, int position) {
          Maquinas_Agrarias m=listaMaquinas.get(position);
          holder.tvIdMaqina.setText(m.getId_maquina_agraria()+"");
          holder.tvPatente.setText(m.getPatente());
          holder.tvTipoTareaMaquina.setText(m.getTipos_Tareas().getNombre_tipo_tarea());
          holder.cardView.setOnClickListener(v->{
              ApiClient.guardarObjeto(context,"maquinaAgraria",m);
              Navegacion.navegarBorrandoStack( (Activity) v.getContext(),
                      R.id.nav_host_fragment_content_main,
                      R.id.crearTareaFragment,
                      R.id.tareasFragment,
                      false);
              /*NavOptions navOptions = new NavOptions.Builder()
                      .setPopUpTo(R.id.tareasFragment, false)
                      .build();
              Navigation.findNavController((Activity)v.getContext(),
                      R.id.nav_host_fragment_content_main)
                      .navigate(R.id.crearTareaFragment,null,navOptions);*/
          });
    }

    @Override
    public int getItemCount() {
        return listaMaquinas.size();
    }

    public class MaquinasAgrariasViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdMaqina, tvPatente, tvTipoTareaMaquina;
        private CardView cardView;
        public MaquinasAgrariasViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardMaquina);
            tvIdMaqina=itemView.findViewById(R.id.tvIdMaquina);
            tvPatente=itemView.findViewById(R.id.tvPatenta);
            tvTipoTareaMaquina=itemView.findViewById(R.id.tvTipoTareaMaquina);
        }
    }
}
