package dao.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

import domain.Favoritos;
import domain.TiendaFavorita;

public class UserAndTiendas {

    @Embedded
    private Favoritos favoritos;
    @Relation(parentColumn = "idUsuario",entityColumn = "idUsuarioFavorito",entity = TiendaFavorita.class)
    private List<TiendaFavorita> tiendas;

    public Favoritos getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Favoritos favoritos) {
        this.favoritos = favoritos;
    }

    public List<TiendaFavorita> getTiendas() {
        return tiendas;
    }

    public void setTiendas(ArrayList<TiendaFavorita> tiendas) {
        this.tiendas = tiendas;
    }
}
