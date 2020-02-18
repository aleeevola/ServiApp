package com.tpappsmoviles.serviapp.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.List;

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;
import domain.Tienda;
import domain.TiendaFavorita;

public class FavoritosRecyclerAdapter extends RecyclerView.Adapter<FavoritosRecyclerAdapter.TiendaViewHolder> {

    public List<TiendaFavorita> mDataSet;

    public FavoritosRecyclerAdapter(List<TiendaFavorita> myDataSet){
        mDataSet = myDataSet;
    }

    public TiendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_favoritos, parent, false);
        TiendaViewHolder vh = new TiendaViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TiendaViewHolder holder, final int position) {
        final TiendaFavorita tienda = mDataSet.get(position);
        holder.nombreTienda.setText(tienda.getNombre());
        holder.rubro.setText(tienda.getRubro().toString());
        holder.horario.setText(tienda.getHorarioDeAtencion());

            holder.btnEliminar.setTag(position);
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(final View view) {
                                                          final Context c = view.getContext();
                                                          final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                                          builder.setMessage("¿Desea eliminar la tienda \" " + tienda.getNombre() + "\"?")
                                                                  .setTitle("Eliminar de favoritos")
                                                                  .setPositiveButton("Eliminar",
                                                                          new DialogInterface.OnClickListener() {
                                                                              @Override
                                                                              public void onClick(DialogInterface dlgInt, int i) {

                                                                                  mDataSet.remove(position);

                                                                                  FavoritosDao pdao= FavoritosRepository.getInstance(c).getFavoritosBD().favoritosDao();
                                                                                 pdao.deleteTiendaFavoria(tienda);

                                                                                  notifyDataSetChanged();
                                                                                  Toast.makeText(builder.getContext(), "La tienda fue eliminada de favoritos", Toast.LENGTH_LONG).show();

                                                                              }
                                                                          });
                                                          builder.setNegativeButton("Cancelar",
                                                                  new DialogInterface.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(DialogInterface dlgInt, int i) {
                                                                          //Toast.makeText(builder.getContext(), "Cancelado", Toast.LENGTH_LONG).show();
                                                                      }
                                                                  });
                                                          AlertDialog dialog = builder.create();
                                                          dialog.show();
                                                      }
                                                  }
            );
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class TiendaViewHolder extends RecyclerView.ViewHolder{
        TextView nombreTienda;
        TextView rubro;
        TextView horario;

        ImageButton btnEliminar;

        TiendaViewHolder(View base){
            super(base);
                this.nombreTienda=(TextView) base.findViewById(R.id.ff_nombreTienda);
                this.rubro=(TextView) base.findViewById(R.id.ff_rubroTienda);
                this.horario=(TextView) base.findViewById(R.id.ff_horarioAtencion);
                this.btnEliminar = (ImageButton) base.findViewById(R.id.ff_eliminarFavorito);
        }


    }


}
