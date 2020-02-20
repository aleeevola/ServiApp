package com.tpappsmoviles.serviapp.activity;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.tpappsmoviles.serviapp.R;

import dao.room.FavoritosDao;
import dao.room.FavoritosRepository;
import domain.Favoritos;

public class MainActivity extends AppCompatActivity {
    private Button btnFavoritos;
    private Button btnBuscarServicios;
    private Button btnVerMapa;

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

        getNotification("Lo q ta escrito aca");
      /*  BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(getPackageName() + MyReceiver._NOTIFICACION_FAVORITOS);
        getApplication().getApplicationContext().registerReceiver(br, filtro);
*/
      //  this.registerReceiver(br, filtro);


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

}