package com.tpappsmoviles.serviapp.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.List;

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Tienda;
import domain.TiendaFavorita;

public class TiendaRecyclerAdapter extends RecyclerView.Adapter<TiendaRecyclerAdapter.TiendaViewHolder> {

    public List<Tienda> mDataSet;
    public int idUsuario;

    public TiendaRecyclerAdapter(List<Tienda> myDataSet,int idU){
        mDataSet = myDataSet;
        idUsuario=idU;
    }

    public TiendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_tiendas, parent, false);
        TiendaViewHolder vh = new TiendaViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TiendaViewHolder holder, final int position) {
        final Tienda tienda = mDataSet.get(position);
        holder.nombreTienda.setText(tienda.getNombre());
        holder.rubro.setText(tienda.getRubro().toString());
        holder.horario.setText(tienda.getHorarioDeAtencion());
        holder.imagen.setImageBitmap(tienda.getImagen());
        holder.imagen.setTag(position);
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                    Intent i1 = new Intent(view.getContext(), TiendaPerfil.class);
                    i1.putExtra("ID_USUARIO", idUsuario);
                    i1.putExtra("ID_TIENDA", tienda.getId());
                    view.getContext().startActivity(i1);
            }});
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class TiendaViewHolder extends RecyclerView.ViewHolder{
        TextView nombreTienda;
        TextView rubro;
        TextView horario;
        ImageView imagen;

        TiendaViewHolder(View base){
            super(base);
                this.nombreTienda=(TextView) base.findViewById(R.id.ft_nombreTienda);
                this.rubro=(TextView) base.findViewById(R.id.ft_rubroTienda);
                this.horario=(TextView) base.findViewById(R.id.ft_horarioAtencion);
                this.imagen=(ImageView)base.findViewById(R.id.ft_imagen);
        }


    }


}
