package com.tpappsmoviles.serviapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;

import dao.TiendaRepository;
import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;
import domain.Rubro;
import domain.Tienda;
import domain.TiendaFavorita;

public class ListaTiendas extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TiendaRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
private Integer idUsuario;

    public static List<Tienda> listaTiendas =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Buscar Tiendas");


        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getInt("ID_USUARIO");
        //recuperarFavoritos(idUsuario);*/
        listaTiendas.clear();
        CargarTindas();


    }

    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void CargarTindas(){
        TiendaRepository.getInstance().listarTienda(miHandler);
        // TODO: 19/2/2020 ACA TIENE QUE CARGAR LA LISTA DE TIENDAS
        //esta asi ranciovich para mostrar 2
        //TiendaRepository.getInstance().buscarTienda(9,miHandler);
        //TiendaRepository.getInstance().buscarTienda(13,miHandler);

    }


    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    //listaTiendas.add(TiendaRepository.getInstance().getListaTiendas().get(0));
                    listaTiendas = TiendaRepository.getInstance().getListaTiendas();
                    System.out.println("size "+listaTiendas.size());
                    mRecyclerView = (RecyclerView) findViewById(R.id.CardRecycler);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(ListaTiendas.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new TiendaRecyclerAdapter(listaTiendas,idUsuario);
                    mRecyclerView.setAdapter(mAdapter);
                    break;
                case TiendaRepository._UPDATE_TIENDA:
                    showToast("Datos guardados");
                    break;
                case TiendaRepository._ERROR_TIENDA:
                    showToast("Se produjo en error");
                    break;
                default:
                    Log.d("SERVIAPP", "Default handler EditarPerfilTienda");
                    break;
            }
        }
    };


}
