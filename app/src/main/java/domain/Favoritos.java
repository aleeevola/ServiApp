package domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Favoritos {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idUsuario;
    @Ignore
    private List<TiendaFavorita> tiendas = new ArrayList<>();
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



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

