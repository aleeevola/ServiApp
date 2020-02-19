package domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"idtienda","idUsuarioFavorito"})
public class TiendaFavorita {

    private int idtienda;
    private String nombre;
    private String horarioDeAtencion;
    private Rubro rubro;
    private int idUsuarioFavorito;

    public int getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorarioDeAtencion() {
        return horarioDeAtencion;
    }

    public void setHorarioDeAtencion(String horarioDeAtencion) {
        this.horarioDeAtencion = horarioDeAtencion;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public int getIdUsuarioFavorito() {
        return idUsuarioFavorito;
    }

    public void setIdUsuarioFavorito(int idUsuarioFavorito) {
        this.idUsuarioFavorito = idUsuarioFavorito;
    }

    @Override
    public String toString() {
        return "TiendaFavorita{" +
                "idtienda=" + idtienda +
                ", nombre='" + nombre + '\'' +
                ", horarioDeAtencion='" + horarioDeAtencion + '\'' +
                ", rubro=" + rubro +
                ", idUsuarioFavorito=" + idUsuarioFavorito +
                '}';
    }
}
