package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tiendas);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTiendas);
        mapFragment.getMapAsync(this);

        spinner = (Spinner) findViewById(R.id.spinnerRubros);
        spinner.setAdapter(new ArrayAdapter<Rubro>(this, android.R.layout.simple_spinner_item, Rubro.values()));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mapa de tiendas");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();

        listarTiendas();
        cargarMarcadores("TODOS");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  mMap.clear();

                                                  cargarMarcadores(adapterView.getItemAtPosition(i).toString());

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {
                                              }
                                          }
        );
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
                informacion=" Rubro: "+tiendas.get(i).getRubro().toString() + " Teléfono: " + tiendas.get(i).getTelefono();
             //   LatLng coordenadas= new LatLng(tiendas.get(i).getLat(),tiendas.get(i).getLng());
                LatLng coordenadas= new LatLng(-31,-60);
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(titulo)
                        .snippet(informacion)
                        .icon(bitmapDescriptor));

            }
        }

    }

    public void listarTiendas(){


       // tiendas = (ArrayList) TiendaRepository.getInstance().getListaTiendas();
        //System.out.println(tiendas.toString());

        Tienda t1= new Tienda();
        t1.setNombre("Hola");
        t1.setRubro(Rubro.Mascotas);
        t1.setTelefono(123456789);
        t1.setDireccion("hernan cataneo");
        t1.setHorarioDeAtencion("8:00 a 12:00");
        tiendas.add(t1);
    }
}