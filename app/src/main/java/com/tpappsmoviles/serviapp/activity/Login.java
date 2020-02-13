package com.tpappsmoviles.serviapp.activity;

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
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    if(esTienda.isChecked()){
                        iniciarSesionTienda(usuarioEditText.getText().toString(), contraseñaEditText.getText().toString());
                    }
                    else {
                        Intent i1 = new Intent(v.getContext(), MainActivity.class);
                        startActivity(i1);
                    }
                }
            });
        }


        private void iniciarSesionTienda(String usuario, String contraseña){
            Tienda tienda = TiendaRepository.getInstance().buscarTienda(usuario,miHandler);
            if(usuario.isEmpty() || !existeTienda(tienda)){
                showToast("Tienda no válida");
            }
            else{
                //PASAR TIENDA CON PUTEXTRA
                Intent i1 = new Intent(this, MainActivityTienda.class);
                startActivity(i1);
            }

        }

        private Boolean existeTienda(Tienda t){
            if(t != null){
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
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:
                    Log.d("case consultaplato BUSCRPLATO", "size lista");
                    break;

                default:
                    Log.d("DEFAULT BUSCRPLATO", "handler");
                    break;
            }
        }
    };

    }
