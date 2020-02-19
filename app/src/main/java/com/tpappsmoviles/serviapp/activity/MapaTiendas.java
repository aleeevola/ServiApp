package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tpappsmoviles.serviapp.R;

import java.util.ArrayList;

import dao.TiendaRepository;
import domain.Rubro;
import domain.Tienda;

public class MapaTiendas extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Tienda> tiendas=new ArrayList<>();
    Integer idUsuario;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listarTiendas();
        setContentView(R.layout.activity_maps_tiendas);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTiendas);
        mapFragment.getMapAsync(this);

        spinner = (Spinner) findViewById(R.id.spinnerRubros);
        spinner.setAdapter(new ArrayAdapter<Rubro>(this, android.R.layout.simple_spinner_item, Rubro.values()));

        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getInt("ID_USUARIO");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mapa de tiendas");

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();


        System.out.println("TAMAÑO "+tiendas.size());
        cargarMarcadores("TODOS");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  mMap.clear();

                                                  cargarMarcadores(adapterView.getItemAtPosition(i).toString());
System.out.println("TAMAÑO AL CLIQUEAR "+tiendas.size());
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {
                                              }
                                          }
        );
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int id = (int)(marker.getTag());
                Intent i1 = new Intent(MapaTiendas.this, TiendaPerfil.class);
                i1.putExtra("ID_TIENDA", id);
                i1.putExtra("ID_USUARIO", idUsuario);
                startActivity(i1);
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.info_tienda_mapas, null);
                TextView textView = (TextView) v.findViewById(R.id.itm_nombre);
                textView.setText(marker.getTitle());

                TextView descripcion = (TextView) v.findViewById(R.id.itm_telefono);
                descripcion.setText(marker.getSnippet());
                return v;
            }
        });

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


    public void cargarMarcadores(String rubro){


        BitmapDescriptor bitmapDescriptor;
        String titulo;
        String informacion;
        Boolean todos=false;

        if(rubro.equals("TODOS")) todos=true;



        for (int i=0;i<tiendas.size();i++){
            if(rubro.equals(tiendas.get(i).getRubro().toString()) || todos){
                switch (tiendas.get(i).getRubro()){
                    case Mascotas:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                        break;
                    case LavadoDeAutos:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
                        break;
                    case LimpiezaDePiletas:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                        break;
                    case Jardinería:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                        break;
                    case LimpiezaDeCasas:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                        break;
                    case Plomería:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                        break;
                    case Gasista:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
                        break;
                    case Electricista:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                        break;
                    case Albañil:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                        break;
                    case Otros:
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tiendas.get(i).getRubro().toString());
                }

                titulo=tiendas.get(i).getNombre();
                informacion= String.valueOf(tiendas.get(i).getTelefono());
                LatLng coordenadas= new LatLng(tiendas.get(i).getLat(),tiendas.get(i).getLng());
                //LatLng coordenadas= new LatLng(-31,-60);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(titulo)
                        .snippet(informacion)
                        .icon(bitmapDescriptor));
                marker.setTag(tiendas.get(i).getId());

            }
        }

    }


    public void listarTiendas(){

        // TODO: 19/2/2020 Listar tiendas
       // tiendas = (ArrayList) TiendaRepository.getInstance().getListaTiendas();
        //System.out.println(tiendas.toString());
        TiendaRepository.getInstance().buscarTienda(9,miHandler);

    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    tiendas.add(TiendaRepository.getInstance().getListaTiendas().get(0));
                    System.out.println("TERMINO BUSQUEDA. OBJETOS: "+tiendas.size());
                    //setParametros();
                    break;
                case TiendaRepository._UPDATE_TIENDA:
                    //showToast("Datos guardados");
                    break;
                case TiendaRepository._ERROR_TIENDA:
                    //showToast("Se produjo en error");
                    break;
                default:
                    Log.d("SERVIAPP", "Default handler EditarPerfilTienda");
                    break;
            }
        }
    };
}