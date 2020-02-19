package com.tpappsmoviles.serviapp.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import dao.room.UserAndTiendas;
import domain.Favoritos;
import domain.Rubro;
import domain.TiendaFavorita;

public class ListaFavorios extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FavoritosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static List<TiendaFavorita> listaFavoritos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbarListFavoritos));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tiendas favoritas");

        Bundle extras = getIntent().getExtras();
        Integer idUsuario = extras.getInt("ID_USUARIO");
        recuperarFavoritos(idUsuario);

        mRecyclerView = (RecyclerView) findViewById(R.id.CardRecycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoritosRecyclerAdapter(listaFavoritos);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void CargarFavoritosEjemplo(){

        TiendaFavorita t1= new TiendaFavorita();
        t1.setNombre("Hola");
        t1.setRubro(Rubro.Mascotas);
        t1.setHorarioDeAtencion("8:00 a 12:00");
        t1.setIdtienda(11);
        listaFavoritos.add(t1);

        TiendaFavorita t2= new TiendaFavorita();
        t2.setNombre("Ale");
        t2.setRubro(Rubro.Albañil);
        t2.setHorarioDeAtencion("12:00 a 13:00");
        t2.setIdtienda(22);
        listaFavoritos.add(t2);

        //Favoritos fv=new Favoritos();
        //fv.setIdUsuario(1);
        //fv.setTiendas(listaFavoritos);
        //FavoritosDao pdao= FavoritosRepository.getInstance(ListaFavorios.this).getFavoritosBD().favoritosDao();

        //pdao.insertUserAndTiendas(fv);
    }

    public void recuperarFavoritos(int idUsuario){
        FavoritosDao pdao= FavoritosRepository.getInstance(ListaFavorios.this).getFavoritosBD().favoritosDao();

        Favoritos fv=pdao.loadUsuarioAndTiendasById(idUsuario);
        listaFavoritos=fv.getTiendas();
        System.out.println(fv.toString());
    }




}
