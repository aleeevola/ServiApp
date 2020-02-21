package com.tpappsmoviles.serviapp.activity;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tpappsmoviles.serviapp.R;

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;
import domain.TiendaFavorita;

import static com.tpappsmoviles.serviapp.activity.MyReceiver.NOTIFICACION_ID;

public class MainActivity extends AppCompatActivity {
    private Button btnFavoritos;
    private Button btnBuscarServicios;
    private Button btnVerMapa;

    BroadcastReceiver br;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    public final static String default_notification_channel_id = "default" ;

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

        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"ACCION", Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
            }
        });


        br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        Intent i = new Intent();
        createNotificationChannel();
        filtro.addAction(MyIntentService._NOTIFICACION_FAVORITOS);
        getApplication().getApplicationContext().registerReceiver(br, filtro);
        registerReceiver(br,filtro);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("token", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
               // String token = task.getResult().getToken();

              //  Favoritos.token = token;
              //  SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
               // SharedPreferences.Editor editor = preferences.edit();
              //  editor.putString("token", token);
               //    editor.apply();
                // Log and toast
              //  String msg = "El token es: " + token;
             //   Log.d("token", msg);
            }
        });


        for(TiendaFavorita t: fv.getTiendas()) {
            FirebaseMessaging.getInstance().subscribeToTopic(t.getNombre())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                msg = getString(R.string.msg_subscribe_failed);
                            }
                            Log.d("MainActivity", msg);
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        //   Bundle extras2 = br.getResultExtras(true);
     //   br.onReceive(this,i);
//        String tipoNotificacion = extras2.getString("tipoNotificacion");
//        String textoNotificacion = extras2.getString("textoNotificacion");
//        Integer idTienda = Integer.parseInt(intent.getStringExtra("idTienda"));
 //       String nombreTienda = extras2.getString("nombreTienda");

/*        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplication().getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("EL DE MAIN")
                        .setContentText("aaaaaaaaaaa")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    //                    .setContentIntent(br.)
                        .setAutoCancel(true);

        Notification n = mBuilder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication().getApplicationContext());
        notificationManager.notify(NOTIFICACION_ID, n);

*/
        /*
       // getNotification("Lo q ta escrito aca");
        br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(EditarTiendaPerfil._NOTIFICACION_FAVORITOS);
       // getApplication().getApplicationContext().registerReceiver(br, filtro);
        this.registerReceiver(br,filtro);
      //  this.registerReceiver(br, filtro);
*/

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

        /*
        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(view.getContext(), TiendaPerfil.class);
                i1.putExtra("ID_USUARIO", fv.getIdUsuario());
                i1.putExtra("ID_TIENDA", 9);
                startActivity(i1);
            }
        });
*/
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



    public Notification getNotification (String content) {
        //String tipoNotificacion = intent.getStringExtra("tipoNotificacion");
        //String textoNotificacion = intent.getStringExtra("textoNotificacion");
       // Integer idTienda = Integer.parseInt(intent.getStringExtra("idTienda"));
        //String nombreTienda = intent.getStringExtra("nombreTienda");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , default_notification_channel_id ) ;
        builder.setContentTitle("nombreTienda + :  + tipoNotificacion") ;
        builder.setContentText("textoNotificacion") ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
 /*   private void recibirNotificacion (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyReceiver.class ) ;
        notificationIntent.putExtra(MyReceiver.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyReceiver.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CANAL1";
            String description = "MI CANAL 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("999", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}