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
import domain.Tienda;


public class Login extends AppCompatActivity {

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


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String usuario = usuarioEditText.getText().toString();
                    if(!usuario.isEmpty()) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        if (esTienda.isChecked()) {
                            iniciarSesionTienda(usuario, contraseñaEditText.getText().toString());
                        } else {
                            Intent i1 = new Intent(v.getContext(), MainActivity.class);
                            i1.putExtra("usuario", usuario);
                            startActivity(i1);
                        }
                    } else {
                        showToast("Ingrese un usuario");
                    }
                }
            });
        }


        private void iniciarSesionTienda(final String usuario, String contraseña){
            Tienda tienda = TiendaRepository.getInstance().buscarTienda(usuario,miHandler);
 //           System.out.println("System out print tienda; " + tienda);
            if(!existeTienda(tienda)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Tienda inexistente. ¿Desea registrar la tienda "+ usuario + "?")
                        .setTitle("Registrar tienda")
                        .setPositiveButton("Registrar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        Tienda tienda = new Tienda();
                                        tienda.setNombre(usuario);
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
            else{
                //PASAR TIENDA CON PUTEXTRA
                Intent i1 = new Intent(this, EditarTiendaPerfil.class);
                i1.putExtra("ID_TIENDA", tienda.getId());
                startActivity(i1);
            }

        }

        private Boolean existeTienda(Tienda t){
    //        Log.d("Existe tienda ",t.toString());
            if(t!=null){
                return true;
            }else{
                return false;
            }
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
                    break;
                case TiendaRepository._ALTA_TIENDA:
                    Intent i = new Intent(Login.this,EditarTiendaPerfil.class);
                    i.putExtra("ID_TIENDA", m.arg2);
                    startActivity(i);
                    break;
                default:
                    Log.d("DEFAULT BUSCRTIENDA", "handler");
                    break;
            }
        }
    };


    }