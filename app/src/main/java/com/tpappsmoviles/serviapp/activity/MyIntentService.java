package com.tpappsmoviles.serviapp.activity;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String _NOTIFICACION_FAVORITOS = "com.tpappsmoviles.serviapp._NOTIFICACION_FAVORITOS";
  //  public static final String ENVIAR_NOTIF2 = "com.example.ejemplobroadcast.action.NOT2";


    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.ejemplobroadcast.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.ejemplobroadcast.extra.PARAM2";
    private static final String EXTRA_PARAM3 = "com.example.ejemplobroadcast.extra.PARAM3";
    private static final String EXTRA_PARAM4 = "com.example.ejemplobroadcast.extra.PARAM4";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(_NOTIFICACION_FAVORITOS);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(_NOTIFICACION_FAVORITOS);
        intent.putExtra(EXTRA_PARAM3, param1);
        intent.putExtra(EXTRA_PARAM4, param2);
        Log.d(MyReceiver.TAG,"VA A INCIIAR EL SERCICIO");
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(MyReceiver.TAG,"ENVIA BROADCAST");
        Intent intent2 = new Intent(this, MyReceiver.class);
        intent2.setAction(_NOTIFICACION_FAVORITOS);
        intent2.putExtra(EXTRA_PARAM3, "ASD");
        intent2.putExtra(EXTRA_PARAM4, "ASDSA");

        sendBroadcast(intent2);
    }

}
