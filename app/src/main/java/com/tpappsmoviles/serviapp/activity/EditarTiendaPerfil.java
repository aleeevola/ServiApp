package com.tpappsmoviles.serviapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;

import domain.Rubro;
import domain.Servicio;
import domain.Tienda;

public class EditarTiendaPerfil extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static List<Servicio> listaServicios =new ArrayList<>();

    private Tienda tienda= new Tienda();
    private EditText nombre;
    private Spinner rubro;
    private EditText telefono;
    private EditText horario;
    private EditText direccion;
    private ImageView imagen;

    private Button btn_foto;
    private Button btn_mapa;
    private Button btn_guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbarListFavoritos));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editar Perfil");

        //listaP = PlatoRepository.getInstance().getListaPlatos();
        //PlatoRepository.getInstance().listarPlatos(miHandler);

        nombre =(EditText) findViewById(R.id.ep_nombreTienda);
        rubro =(Spinner) findViewById(R.id.ep_rubro);
        telefono = (EditText) findViewById(R.id.ep_telefono);
        direccion =(EditText) findViewById(R.id.ep_direccion);
        //horario =(EditText) findViewById(R.id.at_Horario);

        btn_foto= (Button) findViewById(R.id.ep_btn_cargarfoto);
        btn_mapa= (Button) findViewById(R.id.ep_btn_zonatrabajo);
        btn_guardar= (Button) findViewById(R.id.ep_btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveParametros();
            }
        });

        rubro = (Spinner) findViewById(R.id.ep_rubro);
        rubro.setAdapter(new ArrayAdapter<Rubro>(this, android.R.layout.simple_spinner_item, Rubro.values()));

        mRecyclerView = (RecyclerView) findViewById(R.id.ep_CardServicios);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServiciosRecyclerAdapter(listaServicios);
        mRecyclerView.setAdapter(mAdapter);

        //if (perfilExistente){
        //Si el perfil existe tiene que cargar los campos
        //Asignar el perfil a tienda que esta definido arriba de todo
        //    setParametros();
        //}
    }

    public void setParametros(){
        nombre.setText(tienda.getNombre());
        telefono.setText(String.valueOf(tienda.getTelefono()));
        direccion.setText(tienda.getDireccion());
        imagen.setImageBitmap(tienda.getImagen());

        //poner que muestre el rubro que ya habia seleccionado
        //este falta tmb
        //horario.setText(tienda.getHorarioDeAtencion());

        listaServicios=tienda.getServicios();
        mAdapter.notifyDataSetChanged();

    }

    public void saveParametros(){
        tienda.setNombre(nombre.getText().toString());
        tienda.setRubro((Rubro) rubro.getSelectedItem());
        tienda.setTelefono(Integer.parseInt(telefono.getText().toString()));
        //tienda.setHorarioDeAtencion();
        tienda.setDireccion(direccion.getText().toString());
        //tienda.setImagen(imagen.);
        //tienda.setServicios();

        //guardar los cambios en tienda y despues actalizarlo en el servidor
        showToast("Datos guardados");
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
