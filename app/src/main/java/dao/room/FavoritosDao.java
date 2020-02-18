package dao.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import domain.Favoritos;
import domain.TiendaFavorita;

@Dao
public abstract class FavoritosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long _insertFavorito(Favoritos favorito);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertTiendaFavorita(TiendaFavorita tienda);

    @Update
    public  abstract void updateFavorito(Favoritos favorito);

    @Delete
    public abstract void deleteFavorito(Favoritos favorito);

    @Delete
    public abstract void deleteTiendaFavoria(TiendaFavorita tienda);

    @Query("SELECT * FROM Favoritos WHERE idUsuario = :id")
    public abstract Favoritos selectFavoritosByIdUsuario(int id);


    @Transaction
    @Query("SELECT * FROM Favoritos WHERE idUsuario =:id")
    public abstract UserAndTiendas loadUsuarioAndTiendasByIdUsuario(int id);

    @Transaction
    public void insertUserAndTiendas(Favoritos favorito) {
        long idpedido=_insertFavorito(favorito);
        List<TiendaFavorita> tiendas=favorito.getTiendas();
        if (!tiendas.isEmpty()){
            for (int i=0; i<tiendas.size();i++){
                TiendaFavorita tienda=tiendas.get(i);
                tienda.setIdUsuarioFavorito(favorito.getIdUsuario());
                this._insertTiendaFavorita(tienda);
            }
        }
    }

    @Transaction
    public Favoritos loadUsuarioAndTiendasById(int idUsuario) {
        UserAndTiendas uat = loadUsuarioAndTiendasByIdUsuario(idUsuario);
        Favoritos fv=new Favoritos();
        fv=uat.getFavoritos();
        fv.setTiendas(uat.getTiendas());
        return fv;
    }
}
