package dao.rest;


import java.util.List;

import domain.Tienda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TiendaRest {


        @GET("tiendas/")
        Call<List<Tienda>> buscarTodas();

        @GET("tiendas/{nombre}")
        Call<Tienda> buscarTienda(@Path("nombre") String nombre);

        @DELETE("tiendas/{id}")
        Call<Void> borrar(@Path("id") Integer id);

        @PUT("tiendas/{id}")
        Call<Tienda> actualizar(@Path("id") Integer id, @Body Tienda tienda);

        @POST("tiendas/")
        Call<Tienda> crear(@Body Tienda tienda);


}
