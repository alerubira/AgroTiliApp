package com.principal.agrotiliapp.ui.tareas.campos;

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
import com.principal.agrotiliapp.clases.Campos;

import java.util.List;

public  class CamposAdapter extends RecyclerView.Adapter<CamposAdapter.CamposViewHolder>{
    private List<Campos> listaCampos;
    private Context context;

    public CamposAdapter(List<Campos> listaCampos, Context context) {
        this.listaCampos = listaCampos;
        this.context = context;
    }

    @NonNull
    @Override
    public CamposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campo, parent, false);
        return new CamposViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CamposViewHolder holder, int position) {
         Campos c =listaCampos.get(position);
         holder.tvIdCampo.setText(c.getId_campo()+"");
         holder.tvNombreCampo.setText(c.getNombre_campo());
         holder.tvSuperficie.setText(c.getSuperficie()+"");
         holder.cardView.setOnClickListener(v ->{
             Bundle bundle = new Bundle();
             bundle.putSerializable("campo", c);
             Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_tareas, bundle);
         });
    }

    @Override
    public int getItemCount() {
        return listaCampos.size();
    }

    public class CamposViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIdCampo, tvNombreCampo, tvSuperficie;
        private CardView cardView;
        public CamposViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.idCardCampo);
            tvIdCampo=itemView.findViewById(R.id.tvIdCampo);
            tvNombreCampo=itemView.findViewById(R.id.tvNombreCampo);
            tvSuperficie=itemView.findViewById(R.id.tvSuperficie);
        }
    }
}
