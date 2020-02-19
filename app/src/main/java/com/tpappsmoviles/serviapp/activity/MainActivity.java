package com.tpappsmoviles.serviapp.activity;


import android.content.Intent;
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

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;

public class MainActivity extends AppCompatActivity {
    private Button btnFavoritos;
    private Button btnBuscarServicios;
    private Button btnVerMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnFavoritos = (Button) findViewById(R.id.btnFavoritos);
        Button btnBuscarServicios = (Button) findViewById(R.id.btnBuscarServicios);
        Button btnVerMapa = (Button) findViewById(R.id.btnVerMapa);
        Button btnPrueba = (Button) findViewById(R.id.btn_prueba);

        Bundle extras = getIntent().getExtras();
        String nombreUsuario = extras.getString("NOMBRE_USUARIO");
        FavoritosDao pdao= FavoritosRepository.getInstance(MainActivity.this).getFavoritosBD().favoritosDao();
        final Favoritos fv=pdao.loadUsuarioAndTiendasByNombre(nombreUsuario);

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ACA HAY QUE BUSCAR EL USUARIO Y PASARLO PARA TENER LA LISTA DE FAVORITOS DE ESE USUARIO

                Intent intentFavoritos = new Intent(view.getContext() , ListaFavorios.class);
                intentFavoritos.putExtra("ID_USUARIO", fv.getIdUsuario());
                startActivity(intentFavoritos);
                }
        });

        btnBuscarServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 19/2/2020 Lista de tiendas
                // TODO: 19/2/2020 hacer reci
                Intent intentTiendas = new Intent(view.getContext() ,ListaTiendas.class);
                intentTiendas.putExtra("ID_USUARIO", fv.getIdUsuario());
                startActivity(intentTiendas);
            }
        });

        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(view.getContext() ,MapaTiendas.class);
                intentMapa.putExtra("ID_USUARIO", fv.getIdUsuario());
                startActivity(intentMapa);
            }
        });

        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(view.getContext(), TiendaPerfil.class);
                i1.putExtra("ID_USUARIO", fv.getIdUsuario());
                i1.putExtra("ID_TIENDA", 9);
                startActivity(i1);
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