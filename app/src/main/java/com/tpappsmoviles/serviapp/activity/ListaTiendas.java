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

    public static List<Tienda> listaTiendas =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Buscar Tiendas");

        /*
        Bundle extras = getIntent().getExtras();
        Integer idUsuario = extras.getInt("ID_USUARIO");
        recuperarFavoritos(idUsuario);*/
        CargarTindaEjemplo();


    }

    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void CargarTindaEjemplo(){
        TiendaRepository.getInstance().buscarTienda(13,miHandler);

        //Favoritos fv=new Favoritos();
        //fv.setIdUsuario(1);
        //fv.setTiendas(listaTiendas);
        //FavoritosDao pdao= FavoritosRepository.getInstance(ListaFavorios.this).getFavoritosBD().favoritosDao();

        //pdao.insertUserAndTiendas(fv);
    }

    public void recuperarFavoritos(int idUsuario){
       /* FavoritosDao pdao= FavoritosRepository.getInstance(ListaTiendas.this).getFavoritosBD().favoritosDao();

        Favoritos fv=pdao.loadUsuarioAndTiendasById(idUsuario);
        listaTiendas =fv.getTiendas();
        System.out.println(fv.toString());*/
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    listaTiendas.add(TiendaRepository.getInstance().getListaTiendas().get(0));
                    System.out.println("hola");
                    mRecyclerView = (RecyclerView) findViewById(R.id.CardRecycler);
                    mRecyclerView.setHasFixedSize(true);

                    mLayoutManager = new LinearLayoutManager(ListaTiendas.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new TiendaRecyclerAdapter(listaTiendas);
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
