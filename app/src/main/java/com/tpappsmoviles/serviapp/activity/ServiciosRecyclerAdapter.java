package com.tpappsmoviles.serviapp.activity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.tpappsmoviles.serviapp.R;
import java.util.List;
import domain.Servicio;

public class ServiciosRecyclerAdapter extends RecyclerView.Adapter<ServiciosRecyclerAdapter.ServicioViewHolder> {

    public List<Servicio> mDataSet;

    public ServiciosRecyclerAdapter(List<Servicio> myDataSet){
        mDataSet = myDataSet;
    }

    public ServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_servicio, parent, false);
        ServicioViewHolder vh = new ServicioViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ServicioViewHolder holder, final int position) {
        final Servicio servicio = mDataSet.get(position);
        holder.nombreServicio.setText(servicio.getNombre());
        holder.descripcion.setText(servicio.getDescripcion());
        holder.precio.setText("$" + servicio.getPrecio().toString());
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ServicioViewHolder extends RecyclerView.ViewHolder{
        TextView nombreServicio;
        TextView descripcion;
        TextView precio;

        ServicioViewHolder(View base){
            super(base);
                this.nombreServicio=(TextView) base.findViewById(R.id.fs_nombreServicio);
                this.descripcion=(TextView) base.findViewById(R.id.fd_descripcionServicio);
                this.precio=(TextView) base.findViewById(R.id.fs_precioServicio);
        }


    }

/*
    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            mDataSet = PlatoRepository.getInstance().getListaPlatos();
            switch (m.arg1){
                case PlatoRepository._CONSULTA_PLATO:
                    break;
                case PlatoRepository._BORRADO_PLATO:

                    break;
            }
        }
    };
    */

}
