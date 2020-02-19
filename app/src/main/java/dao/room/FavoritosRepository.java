package dao.room;

import android.content.Context;

import androidx.room.Room;

import domain.Favoritos;

public class FavoritosRepository {
    private static FavoritosRepository FR = null;
    private FavoritosBD favoritosBD;
    private FavoritosDao favoritosDao;

    private FavoritosRepository(Context ctx){
        favoritosBD = Room.databaseBuilder(ctx,
                FavoritosBD.class, "favorito3-db").allowMainThreadQueries().build();

        favoritosDao = favoritosBD.favoritosDao();
    }

    public synchronized static FavoritosRepository getInstance(Context ctx){
        if(FR ==null){
            FR = new FavoritosRepository(ctx);
        }
        return FR;
    }

    public FavoritosBD getFavoritosBD() {
        return favoritosBD;
    }

    public void insertFavoritos(Favoritos pedido){
        favoritosDao.insertUserAndTiendas(pedido);
    }
    public  void updateFavorio(Favoritos pedido){
        favoritosDao.updateFavorito(pedido);
    }

    public void deleteFavorito(Favoritos pedido){
        favoritosDao.deleteFavorito(pedido);
    }

    public Favoritos selectFavoritosByIdUsuario(int id){
        return favoritosDao.selectFavoritosByIdUsuario(id);
    }
}
