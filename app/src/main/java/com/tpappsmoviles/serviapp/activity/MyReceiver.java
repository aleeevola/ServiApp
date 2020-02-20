package com.tpappsmoviles.serviapp.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.tpappsmoviles.serviapp.R;

public class MyReceiver extends BroadcastReceiver {
    public static final String _NOTIFICACION_FAVORITOS  = "NOTIFICACION_FAVORITOS";

    public static final int NOTIFICACION_ID = 123;
    public static final String CHANNEL_ID="notificacion_oferta";
    Context context1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVIAPP","REcibido "+intent.getAction());
        Toast.makeText(context,"=>"+intent.getAction(),Toast.LENGTH_LONG).show();

        String tipoNotificacion = intent.getStringExtra("tipoNotificacion");
        String textoNotificacion = intent.getStringExtra("textoNotificacion");
        Integer idTienda = Integer.parseInt(intent.getStringExtra("idTienda"));
        String nombreTienda = intent.getStringExtra("nombreTienda");

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        //CREO EL INSTENT DE EDITAR PARA PASARLO A LA NOTIFICACION
        Intent destino = new Intent(context, MainActivity.class);
        destino.putExtra("tipoNotificacion",tipoNotificacion);
        destino.putExtra("textoNotificacion",textoNotificacion);
        destino.putExtra("idTienda", idTienda);
        destino.putExtra("nombreTienda", nombreTienda);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, uniqueInt, destino, 0);
        Log.d("RECEIVER", "EN EL MEDIO");
        createNotificationChannel();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(nombreTienda + ": " + tipoNotificacion)
                        .setContentText(textoNotificacion)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICACION_ID, mBuilder.build());
        Log.d("RECEIVER ", "AL FINAL");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion favoritos";
            String description = "descripcion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context1.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

}