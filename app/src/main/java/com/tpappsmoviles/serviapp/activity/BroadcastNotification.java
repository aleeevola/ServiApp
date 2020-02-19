package com.tpappsmoviles.serviapp.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BroadcastNotification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_broadcast_notificacion);
        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_01);
        getApplication().getApplicationContext()
                .registerReceiver(br, filtro);

    }
}
