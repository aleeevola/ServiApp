package domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Favoritos {
    @PrimaryKey
    @NonNull
    private int idUsuario;
    @Ignore
    private List<TiendaFavorita> tiendas;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<TiendaFavorita> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<TiendaFavorita> tiendas) {
        this.tiendas = tiendas;
    }

    @Override
    public String toString() {
        return "Favoritos{" +
                "idUsuario=" + idUsuario +
                ", tiendas=" + tiendas +
                '}';
    }
}

