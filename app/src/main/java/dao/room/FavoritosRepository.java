package dao.room;

import android.content.Context;

import androidx.room.Room;

import domain.Favoritos;

public class FavoritosRepository {
    private static FavoritosRepository PR = null;
    private FavoritosBD favoritosBD;
    private FavoritosDao pedidoDao;

    private FavoritosRepository(Context ctx){
        favoritosBD = Room.databaseBuilder(ctx,
                FavoritosBD.class, "favorito-db").allowMainThreadQueries().build();

        pedidoDao= favoritosBD.favoritosDao();
    }

    public synchronized static FavoritosRepository getInstance(Context ctx){
        if(PR ==null){
            PR = new FavoritosRepository(ctx);
        }
        return PR;
    }

    public FavoritosBD getFavoritosBD() {
        return favoritosBD;
    }

    public void insertFavoritos(Favoritos pedido){
        pedidoDao.insertUserAndTiendas(pedido);
    }
    public  void updateFavorio(Favoritos pedido){
        pedidoDao.updateFavorito(pedido);
    }

    public void deleteFavorito(Favoritos pedido){
        pedidoDao.deleteFavorito(pedido);
    }

    public Favoritos selectFavoritosByIdUsuario(int id){
        return pedidoDao.selectFavoritosByIdUsuario(id);
    }
}
