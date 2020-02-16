package com.tpappsmoviles.serviapp.activity;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
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

public class TiendaPerfil extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static List<Servicio> listaServicios =new ArrayList<>();

    //ESTA TIENDA ES PARA MOSTAR LOS DATOS
    private Tienda tienda= new Tienda();
    private TextView rubro;
    private Button telefono;
    private TextView direccion;
    private TextView horario;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tienda);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbarListFavoritos));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nombre tienda");

        //listaP = PlatoRepository.getInstance().getListaPlatos();
        //PlatoRepository.getInstance().listarPlatos(miHandler);

        rubro =(TextView) findViewById(R.id.at_RubroTienda);
        telefono = (Button) findViewById(R.id.at_btnLlamar);
        direccion =(TextView) findViewById(R.id.at_Direccion);
        horario =(TextView) findViewById(R.id.at_Horario);
        imagen= (ImageView) findViewById(R.id.at_imagenTienda);


        /////////////
        //CREO UNA TIENDA PARA MOSTRAR LOS DATOS,
        //DEBE CARGARLOS DE LA BASE DE DATOS SUPONGO
        //FALTA MOSTRAR LA FOTO
        Tienda t1= new Tienda();
        t1.setNombre("Hola");
        t1.setRubro(Rubro.Mascotas);
        t1.setTelefono(123456789);
        t1.setDireccion("hernan cataneo");
        t1.setHorarioDeAtencion("8:00 a 12:00");
        //t1.setImagen();

        Servicio s1 = new Servicio();
        s1.setDescripcion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        s1.setNombre("Cortar pelo");
        s1.setPrecio((float) 222);

        Servicio s2 = new Servicio();
        s2.setDescripcion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        s2.setNombre("Bañar");
        s2.setPrecio((float) 33);


        listaServicios.add(s1);
        listaServicios.add(s2);

        tienda=t1;
        /////////////

        setParametros();

        mRecyclerView = (RecyclerView) findViewById(R.id.at_CardRecycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServiciosRecyclerAdapter(listaServicios);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void setParametros(){
        rubro.setText(tienda.getRubro().toString());
        telefono.setText(String.valueOf(tienda.getTelefono()));
        direccion.setText(tienda.getDireccion());
        horario.setText(tienda.getHorarioDeAtencion());
        //imagen.setImageBitmap(tienda.getImagen());
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
