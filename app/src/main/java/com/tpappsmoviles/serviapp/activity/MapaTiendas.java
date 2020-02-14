package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import domain.Tienda;

public class MapaTiendas extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private PolylineOptions lineaOpciones= new PolylineOptions();
    private Polyline linea;

    private ArrayList<Tienda> tiendas=new ArrayList<>();

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tiendas);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTiendas);
        mapFragment.getMapAsync(this);


        spinner = (Spinner) findViewById(R.id.spinnerRubros);

        //adapter = ArrayAdapter.createFromResource(this, R.array.estados_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();

        listarPedidos();
        cargarMarcadores("TODOS");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  mMap.clear();

                                                  cargarMarcadores(adapterView.getItemAtPosition(i).toString());
                                                  linea = mMap.addPolyline(lineaOpciones);
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

        lineaOpciones=new PolylineOptions();
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
                informacion="Tienda:"+titulo + " Rubro: "+tiendas.get(i).getRubro().toString() + "Teléfono: " + tiendas.get(i).getTelefono();
             /*   LatLng coordenadas= new LatLng(tiendas.get(i).getLat(),tiendas.get(i).getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(titulo)
                        .snippet(informacion)
                        .icon(bitmapDescriptor));
                if(tiendas.get(i).getRubro().toString().equals("En envio")){
                    lineaOpciones.add(coordenadas);
                }

             */
            }
        }

    }

    public void listarPedidos(){


        tiendas = (ArrayList) TiendaRepository.getInstance().getListaTiendas();
        System.out.println(tiendas.toString());
       /*
        Pedido p1 =new Pedido();
        p1.setEstado(1);
        p1.setId(1);
        p1.setLat(-31.620816);
        p1.setLng(-60.747582);
        pedidos.add(p1);
        Pedido p2 =new Pedido();
        p2.setEstado(2);
        p2.setId(1);
        p2.setLat(-31.00);
        p2.setLng(-60.747582);
        pedidos.add(p2);
        Pedido p3 =new Pedido();
        p3.setEstado(3);
        p3.setId(1);
        p3.setLat(-31.10);
        p3.setLng(-60.66);
        pedidos.add(p3);
        Pedido p4 =new Pedido();
        p4.setEstado(4);
        p4.setId(4);
        p4.setLat(-30.00);
        p4.setLng(-61.747582);
        pedidos.add(p4);
        Pedido p5 =new Pedido();
        p5.setEstado(5);
        p5.setId(5);
        p5.setLat(-29.00);
        p5.setLng(-60.899);
        pedidos.add(p5);
        Pedido p6 =new Pedido();
        p6.setEstado(6);
        p6.setId(6);
        p6.setLat(-31.3);
        p6.setLng(-62.747582);
        pedidos.add(p6);
        Pedido p7 =new Pedido();
        p7.setEstado(7);
        p7.setId(7);
        p7.setLat(-33.00);
        p7.setLng(-61.582);
        pedidos.add(p7);
        Pedido p8 =new Pedido();
        p8.setEstado(8);
        p8.setId(8);
        p8.setLat(-32.11);
        p8.setLng(-60.74782);
        pedidos.add(p8);
        Pedido p9 =new Pedido();
        p9.setEstado(6);
        p9.setId(9);
        p9.setLat(-30.1);
        p9.setLng(-62.582);
        pedidos.add(p9);*/
    }
}