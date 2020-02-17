package dao;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dao.rest.TiendaRest;
import domain.Tienda;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class TiendaRepository {

    public static String _SERVER = "http://10.0.2.2:5000/";
    private List<Tienda> listaTiendas;

    public static final int _ALTA_TIENDA =1;
    public static final int _UPDATE_TIENDA =2;
    public static final int _BORRADO_TIENDA =3;
    public static final int _CONSULTA_TIENDA =4;
    public static final int _ERROR_TIENDA =9;

    private static TiendaRepository _INSTANCE;

    private TiendaRepository(){}

    public static TiendaRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE = new TiendaRepository();
            _INSTANCE.configurarRetrofit();
            _INSTANCE.listaTiendas = new ArrayList<>();
        }
        return _INSTANCE;
    }

    private Retrofit rf;

    private TiendaRest tiendaRest;

    private void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");

        this.tiendaRest = this.rf.create(TiendaRest.class);
    }

    public void actualizarTienda(final Tienda tienda, final Handler h){
        Call<Tienda> llamada = this.tiendaRest.actualizar(tienda.getId(), tienda);
        llamada.enqueue(new Callback<Tienda>() {
            @Override
            public void onResponse(Call<Tienda> call, Response<Tienda> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    listaTiendas.remove(tienda);
                    listaTiendas.add(response.body());
                    Message m = new Message();
                    m.arg1 = _UPDATE_TIENDA;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Tienda> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }
        });
    }


    public void crearTienda(final Tienda tienda, final Handler h){
        Call<Tienda> llamada = this.tiendaRest.crear(tienda);
        llamada.enqueue(new Callback<Tienda>() {
            @Override
            public void onResponse(Call<Tienda> call, Response<Tienda> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    listaTiendas.add(response.body());
                    Message m = new Message();
                    m.arg1 = _ALTA_TIENDA;
                    m.arg2 = tienda.getId();
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Tienda> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }
        });
    }

    public void listarTienda(final Handler h){
        Call<List<Tienda>> llamada = this.tiendaRest.buscarTodas();
        llamada.enqueue(new Callback<List<Tienda>>() {
            @Override
            public void onResponse(Call<List<Tienda>> call, Response<List<Tienda>> response) {
                if(response.isSuccessful()){
                    listaTiendas.clear();
                    listaTiendas.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_TIENDA;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Tienda>> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }
        });
    }

    public void borrarObra(final Tienda tienda, final Handler h){
        Call<Void> llamada = this.tiendaRest.borrar(tienda.getId());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    for(Tienda tienda : listaTiendas){
                        Log.d("APP_2","Tienda "+tienda.getId());
                    }
                    Log.d("APP_2","BORRA Tienda "+tienda.getId());
                    listaTiendas.remove(tienda);
                    for(Tienda tienda : listaTiendas){
                        Log.d("APP_2","Tienda "+tienda.getId());
                    }
                    Message m = new Message();
                    m.arg1 = _BORRADO_TIENDA;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }
        });
    }



    private Tienda tienda = null;
    public Tienda buscarTienda(final String nombre, final Handler h){
        Call<Tienda> llamada = this.tiendaRest.buscarTienda(nombre);
        llamada.enqueue(new Callback<Tienda>() {
            @Override
            public void onResponse(Call<Tienda> call, Response<Tienda> response) {
                if(response.isSuccessful()){
                    Message m = new Message();
                    m.arg1 = _CONSULTA_TIENDA;
                    h.sendMessage(m);
                    tienda = response.body();
                 //   Log.d("TiendaRepository", tienda.getNombre());
                }
            }

            @Override
            public void onFailure(Call<Tienda> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }

        });
    return tienda;
    }

    public Tienda buscarTienda(final Integer id, final Handler h){
        Call<Tienda> llamada = this.tiendaRest.buscarTienda(id);
        llamada.enqueue(new Callback<Tienda>() {
            @Override
            public void onResponse(Call<Tienda> call, Response<Tienda> response) {
                if(response.isSuccessful()){
                    Message m = new Message();
                    m.arg1 = _CONSULTA_TIENDA;
                    h.sendMessage(m);
                    tienda = response.body();;
                }
            }

            @Override
            public void onFailure(Call<Tienda> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_TIENDA;
                h.sendMessage(m);
            }

        });
        return tienda;
    }

    public List<Tienda> getListaTiendas() {
        return listaTiendas;
    }
}