package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tpappsmoviles.serviapp.R;


public class MapaZonaTrabajo extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btn_guardarZona;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_zonatrabajo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.zt_map_zona);
        mapFragment.getMapAsync(this);

        btn_guardarZona = (Button) findViewById(R.id.zt_btn_guardar);

        btn_guardarZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarZona();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();

/*        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                Intent i = new Intent();

                double latitud=latLng.latitude;
                double longitud=latLng.longitude;

                i.putExtra("LATITUD",latitud);
                i.putExtra("LONGITUD",longitud);
                setResult(RESULT_OK,i);
                finish();
            }
        });*/
    }

    private void guardarZona(){
        //devuelve el centro del mapa
        LatLng latLng=mMap.getCameraPosition().target;
        //devuelve el zoom de la camara
        float zonaTrabajo = mMap.getCameraPosition().zoom;

        Intent i = new Intent();

        double latitud=latLng.latitude;
        double longitud=latLng.longitude;


        i.putExtra("LATITUD",latitud);
        i.putExtra("LONGITUD",longitud);
        i.putExtra("ZONATRABAJO",zonaTrabajo);
        setResult(RESULT_OK,i);
        finish();
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


}
