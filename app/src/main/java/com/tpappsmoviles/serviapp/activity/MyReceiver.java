package com.tpappsmoviles.serviapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public static final String EVENTO_01="frsf.isi.dam.app04.EVENTO_01_MSG";
    public static final String EVENTO_02="frsf.isi.dam.app04.EVENTO_01_MSG";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVIAPP","REcibido "+intent.getAction());
        Toast.makeText(context,"=>"+intent.getAction(),Toast.LENGTH_LONG).show();
    }
}

