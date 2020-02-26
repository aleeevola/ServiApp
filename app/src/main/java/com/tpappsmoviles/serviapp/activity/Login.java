package com.tpappsmoviles.serviapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.tpappsmoviles.serviapp.R;

import dao.TiendaRepository;
import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;
import domain.Tienda;


public class Login extends AppCompatActivity {
        Boolean existeTienda = false;
        String usuario;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
          // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

            final EditText usuarioEditText = findViewById(R.id.username);
            final EditText contraseñaEditText = findViewById(R.id.password);
            final Button loginButton = findViewById(R.id.login);
            final Switch esTienda = findViewById(R.id.switchTienda);
            final ProgressBar loadingProgressBar = findViewById(R.id.loading);

            usuario = usuarioEditText.getText().toString();


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    usuario = usuarioEditText.getText().toString();
                    if(!usuario.isEmpty()) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        if (esTienda.isChecked()) {
                            try {
                                iniciarSesionTienda(usuario, contraseñaEditText.getText().toString());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {

                            final FavoritosDao pdao= FavoritosRepository.getInstance(Login.this).getFavoritosBD().favoritosDao();
                            Favoritos fv=pdao.selectFavoritosByNombreUsuario(usuario);
                            if(fv==null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Usuario inexistente. ¿Desea registrar el usuario "+ usuario + "?")
                                        .setTitle("Registrar usuario")
                                        .setPositiveButton("Registrar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dlgInt, int i) {
                                                        Favoritos favorito = new Favoritos();
                                                        favorito.setNombre(usuario);
                                                        showToast("Usuario " + usuario + " creado.");
                                                        pdao.insertUserAndTiendas(favorito);
                                                        Intent i1 = new Intent(v.getContext(), MainActivity.class);
                                                        i1.putExtra("NOMBRE_USUARIO", usuario);
                                                        startActivity(i1);
                                                    }
                                                })
                                        .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dlgInt, int i) {
                                                        Toast.makeText(Login.this,
                                                                "Registro cancelado",Toast.LENGTH_LONG)
                                                                .show();
                                                    }
                                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Intent i1 = new Intent(v.getContext(), MainActivity.class);
                                i1.putExtra("NOMBRE_USUARIO", usuario);
                                startActivity(i1);
                            }
                        }
                    } else {
                        showToast("Ingrese un usuario");
                    }
                }
            });
        }


        private void iniciarSesionTienda(final String usuario, String contraseña) throws InterruptedException {
            TiendaRepository.getInstance().existeTienda(usuario, miHandler);
        }

        private void existeTienda(String nombre){
            Log.d("EXISTE TIENDA","usuario: " + nombre);
            TiendaRepository.getInstance().buscarTienda(nombre,miHandler);
        }

    private void noExisteTienda(final String nombre){
        Log.d("NO EXISTE TIENDA","usuario: " + nombre);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tienda inexistente. ¿Desea registrar la tienda "+ nombre + "?")
                .setTitle("Registrar tienda")
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                                Tienda tienda = new Tienda();
                                tienda.setNombre(nombre);
                                showToast("Tienda " + nombre + " creada.");
                                TiendaRepository.getInstance().crearTienda(tienda, miHandler);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                                Toast.makeText(Login.this,
                                        "Registro cancelado",Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



        public void showToast(String txtToast){
            Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
            toast1.show();
        }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            Log.d("APP SERVIAPP","Vuelve al handler " + m.arg1);
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    Log.d("APP Serviapp","consulta "+m.arg1);
                    Intent i1 = new Intent(Login.this, EditarTiendaPerfil.class);
                    i1.putExtra("ID_TIENDA", m.arg2);
                    startActivity(i1);
                    break;
                case TiendaRepository._ALTA_TIENDA:
                    Intent i = new Intent(Login.this,EditarTiendaPerfil.class);
                    i.putExtra("ID_TIENDA", m.arg2);
                    startActivity(i);
                    break;
                case TiendaRepository._EXISTE_TIENDA:
                    existeTienda(usuario);
                    break;
                case TiendaRepository._NOEXISTE_TIENDA:
                    noExisteTienda(usuario);
                    break;
                default:
                    Log.d("APP SERVIAPP", "Default LOGIN");

            }
        }
    };


    }