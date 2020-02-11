package com.tpappsmoviles.serviapp.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.tpappsmoviles.serviapp.R;

public class MainActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText clave;
    private EditText clave2;
    private EditText correo;
    private EditText tarjeta;
    private EditText ccv;
    private EditText vtoTarjeta;
    private EditText aliasCBU;
    private EditText CBU;
    private RadioGroup groupTipoCuenta;
    private RadioButton tipoCuenta;
    private Switch vendedor;
    private SeekBar seekCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnFavoritos = (Button) findViewById(R.id.btnFavoritos);
        Button btnBuscarServicios = (Button) findViewById(R.id.btnBuscarServicios);
        Button btnVerMapa = (Button) findViewById(R.id.btnVerMapa);

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intentFavoritos = new Intent(this , MainActivity.class);
              //  startActivity(intentFavoritos);
                }
        });

        btnBuscarServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intentServicios = new Intent(this ,MainActivity.class);
              //  startActivity(intentServicios);
            }
        });

        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent intentMapa = new Intent(this ,MainActivity.class);
               // startActivity(intentMapa);
            }
        });

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



    }

    void verFavoritos(){

    };

    void buscarServicios(){

    };

    void verMapa(){

    };

}