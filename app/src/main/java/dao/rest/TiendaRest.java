package dao.rest;


import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.util.List;

import domain.Tienda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static java.lang.annotation.ElementType.PARAMETER;




public interface TiendaRest {


        @GET("/tiendas")
        Call<List<Tienda>> buscarTodas();

        @GET("/tiendas")
        Call<List<Tienda>> buscarTienda(@Query("nombre") String nombre);

        @GET("/tiendas/{id}")
        Call<Tienda> buscarTienda(@Path("id") Integer id);

        @DELETE("tiendas/{id}")
        Call<Void> borrar(@Path("id") Integer id);

        @PUT("tiendas/{id}")
        Call<Tienda> actualizar(@Path("id") Integer id, @Body Tienda tienda);

        @POST("tiendas/")
        Call<Tienda> crear(@Body Tienda tienda);


}
