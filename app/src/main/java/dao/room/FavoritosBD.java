package dao.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import domain.Favoritos;
import domain.TiendaFavorita;

@Database(entities = {Favoritos.class, TiendaFavorita.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FavoritosBD extends RoomDatabase{
    public abstract FavoritosDao favoritosDao();
}
