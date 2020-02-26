package com.tpappsmoviles.serviapp.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tpappsmoviles.serviapp.R;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "1001";

    public MyFirebaseMessagingService() {
    }

    private static FirebaseFunctions mFunctions;


    public static Task<String> sendPushNotification(String nombreTienda, String tipoNotificacion, String textoNotificacion) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("nombreTienda", nombreTienda);
        data.put("tipoNotificacion", tipoNotificacion);
        data.put("textoNotificacion", textoNotificacion);
    Log.d("SEND PUSH NOTIFICATION", "tienda: "+nombreTienda);
        if(mFunctions == null){
            mFunctions = FirebaseFunctions.getInstance();
        }

        return mFunctions
                .getHttpsCallable("sendNotification")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("token", "Se crea el service");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("token","Nuevo token: "+s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() >0){
            String nombreTienda = remoteMessage.getData().get("nombreTienda");
            String tipoNotificacion = remoteMessage.getData().get("tipoNotificacion");
            String textoNotificacion = remoteMessage.getData().get("textoNotificacion");
            if(nombreTienda != null && tipoNotificacion != null && textoNotificacion != null){
                sendNotification(nombreTienda, tipoNotificacion, textoNotificacion);
            }
        }
    }

    private void sendNotification(String nombreTienda, String tipoNotificacion, String textoNotificacion) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("nombreTienda", nombreTienda);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(nombreTienda+": "+tipoNotificacion)
                        .setContentText(textoNotificacion)
                        .setAutoCancel(true);
                //        .setContentIntent(pendingIntent);
        createNotificationChannel();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion favoritos";
            String description = "descripcion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
