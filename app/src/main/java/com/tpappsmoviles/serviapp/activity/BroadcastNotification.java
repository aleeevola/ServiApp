package com.tpappsmoviles.serviapp.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tpappsmoviles.serviapp.R;

public class BroadcastNotification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);
        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver._NOTIFICACION_FAVORITOS);
        getApplication().getApplicationContext()
                .registerReceiver(br, filtro);

    }
}
