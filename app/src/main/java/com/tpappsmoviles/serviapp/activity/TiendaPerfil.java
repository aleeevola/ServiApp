package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;
import java.util.List;

import dao.TiendaRepository;
import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;
import domain.Rubro;
import domain.Servicio;
import domain.Tienda;
import domain.TiendaFavorita;

public class TiendaPerfil extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView mRecyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static List<Servicio> listaServicios =new ArrayList<>();

    private Tienda tienda= new Tienda();
    private TextView rubro;
    private Button telefono; // TODO: 19/2/2020 IMPLEMENTAR TELEFONO DIALER
    private Button favorito;
    private TextView direccion;
    private TextView horario;
    private ImageView imagen;
    private ActionBar actionBar;

    private GoogleMap mMap;
    private float zonaTrabajo;
    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        rubro =(TextView) findViewById(R.id.at_RubroTienda);
        telefono = (Button) findViewById(R.id.at_btnLlamar);
        direccion =(TextView) findViewById(R.id.at_Direccion);
        horario =(TextView) findViewById(R.id.at_Horario);
        imagen= (ImageView) findViewById(R.id.at_imagenTienda);
        favorito= (Button) findViewById(R.id.btnAgregarFavorito);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.at_mapa);
        mapFragment.getMapAsync(this);


        Bundle extras = getIntent().getExtras();
        final Integer idTienda = extras.getInt("ID_TIENDA");
        final Integer idUsuario = extras.getInt("ID_USUARIO");
        Log.d("IdTIENDA recuperado en TIENDA PERFIL", idTienda.toString());
        Log.d("IdUSUARIO recuperado en TIENDA PERFIL", idUsuario.toString());
        TiendaRepository.getInstance().buscarTienda(idTienda,miHandler);

        //FavoritosDao pdao= FavoritosRepository.getInstance(ListaFavorios.this).getFavoritosBD().favoritosDao();

        //pdao.insertUserAndTiendas(fv);

        final FavoritosDao pdao= FavoritosRepository.getInstance(TiendaPerfil.this).getFavoritosBD().favoritosDao();
        final Favoritos fv=pdao.loadUsuarioAndTiendasById(idUsuario);

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pdao.contieneTienda(idUsuario,idTienda)==null){
                    System.out.println("NO CONTIENE TIENDA");
                    TiendaFavorita t = new TiendaFavorita();
                    t.setIdtienda(tienda.getId());
                    t.setNombre(tienda.getNombre());
                    t.setRubro(tienda.getRubro());
                    t.setHorarioDeAtencion(tienda.getHorarioDeAtencion());
                    fv.getTiendas().add(t);
                    pdao.insertUserAndTiendas(fv);
                    showToast("Agregada a favoritos");
                }
                else { showToast("Ya se encuentra en favoritos");}
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();

    }
    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String
            permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9999: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    actualizarMapa();
                } else {
                    return;
                }
            }
        }
    }

    public void setParametros(){
        actionBar.setTitle(tienda.getNombre());
        rubro.setText(tienda.getRubro().toString());
        telefono.setText(String.valueOf(tienda.getTelefono()));
        direccion.setText(tienda.getDireccion());
        horario.setText(tienda.getHorarioDeAtencion());
        imagen.setImageBitmap(tienda.getImagen());
        zonaTrabajo=tienda.getZonaTrabajo();
        lat=tienda.getLat();
        lng=tienda.getLng();
        listaServicios=tienda.getServicios();

        mRecyclerView = (RecyclerView) findViewById(R.id.at_CardRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServiciosRecyclerAdapter(listaServicios);
        mRecyclerView.setAdapter(mAdapter);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), zonaTrabajo));
    }



    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    tienda = TiendaRepository.getInstance().getListaTiendas().get(0);
                    setParametros();
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
