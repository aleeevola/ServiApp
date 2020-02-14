package com.tpappsmoviles.serviapp.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;

import domain.Rubro;
import domain.Tienda;

public class ListaFavorios extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FavoritosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static List<Tienda> listaFavoritos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbarListFavoritos));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tiendas favoritas");

        //listaP = PlatoRepository.getInstance().getListaPlatos();
        //PlatoRepository.getInstance().listarPlatos(miHandler);

        Tienda t1= new Tienda();
        t1.setNombre("Hola");
        t1.setRubro(Rubro.BañoDeMascotas);
        t1.setHorarioDeAtencion("8:00 a 12:00");
        listaFavoritos.add(t1);

        Tienda t2= new Tienda();
        t2.setNombre("Ale");
        t2.setRubro(Rubro.Albañil);
        t2.setHorarioDeAtencion("12:00 a 13:00");
        listaFavoritos.add(t2);


        mRecyclerView = (RecyclerView) findViewById(R.id.CardRecycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoritosRecyclerAdapter(listaFavoritos);
        mRecyclerView.setAdapter(mAdapter);

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, @Nullable Intent data) {
        if( resultCode== Activity.RESULT_OK){
            if(requestCode==mAdapter.CODIGO_LISTA_PLATO){
                mAdapter.notifyDataSetChanged();
            }
        }
    }*/


    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    /*
    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            listaP = PlatoRepository.getInstance().getListaPlatos();
            switch (m.arg1){
                case PlatoRepository._CONSULTA_PLATO:
                    mAdapter = new PlatoRecyclerAdapter(listaP,false);
                    mRecyclerView.setAdapter(mAdapter);
                    break;
                case PlatoRepository._BORRADO_PLATO:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };*/


}
