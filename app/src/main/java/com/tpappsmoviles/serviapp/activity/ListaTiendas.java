package com.tpappsmoviles.serviapp.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.TiendaRepository;
import domain.Rubro;
import domain.Tienda;

public class ListaTiendas extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TiendaRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Integer idUsuario;
    private EditText nombre;
    private Spinner rubro;
    private Button btnFiltrar;
    private ArrayAdapter<Rubro> adapterRubro;

    public static List<Tienda> listaTiendas =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Buscar Tiendas");

        nombre = (EditText) findViewById(R.id.editNombre);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
        rubro = (Spinner) findViewById(R.id.r_rubro);

        rubro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                adapterRubro = new ArrayAdapter<Rubro>(ListaTiendas.this, android.R.layout.simple_spinner_item, Rubro.values());
                rubro.setAdapter(adapterRubro);
                return false;
            }
        });



        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getInt("ID_USUARIO");
        //recuperarFavoritos(idUsuario);*/
        listaTiendas.clear();
        CargarTiendas();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString()!= null){
                    if(rubro.getSelectedItem()!=null){
                        filtrarLista(nombre.getText().toString(),(Rubro) rubro.getSelectedItem());
                    }else{
                        filtrarLista(nombre.getText().toString());
                    }
                }else{
                    if(rubro.getSelectedItem()!=null){
                        filtrarLista((Rubro) rubro.getSelectedItem());
                    }
                }

            }
        });


    }




    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void CargarTiendas(){
        TiendaRepository.getInstance().listarTienda(miHandler);
    }

    private void filtrarLista(String nombreTienda, Rubro rubro) {
            ArrayList<Tienda> listaNombres = new ArrayList<>();
            ArrayList<Tienda> listaResultado = new ArrayList<>();
            for(Tienda t: listaTiendas){
                if(t.getNombre().equals(nombreTienda) || t.getNombre().contains(nombreTienda)){
                    listaNombres.add(t);
                }
            }
            for(Tienda t: listaTiendas){
                if(t.getRubro().equals(rubro) && listaNombres.contains(t)){
                    listaResultado.add(t);
                }
            }
        listaTiendas.clear();
            listaTiendas = listaResultado;
            mAdapter.notifyDataSetChanged();
    }

    private void filtrarLista(String nombreTienda) {
        ArrayList<Tienda> listaResultado = new ArrayList<>();
        for(Tienda t: listaTiendas){
            if(t.getNombre().equals(nombreTienda) || t.getNombre().contains(nombreTienda)){
                listaResultado.add(t);
            }
        }
        listaTiendas.clear();
        listaTiendas = listaResultado;
        mAdapter.notifyDataSetChanged();
    }

    private void filtrarLista(Rubro rubro) {
        ArrayList<Tienda> listaResultado = new ArrayList<>();
        for(Tienda t: listaTiendas){
            if(t.getRubro().equals(rubro)){
                listaResultado.add(t);
            }
        }
        listaTiendas.clear();
        listaTiendas = listaResultado;
        mAdapter.notifyDataSetChanged();
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
