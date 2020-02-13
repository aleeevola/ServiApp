package domain;

import java.util.ArrayList;

public class Usuario {

    private int id;
    private String usuario;
    private String contraseña;
    private ArrayList<Tienda> favoritos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public ArrayList<Tienda> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<Tienda> favoritos) {
        this.favoritos = favoritos;
    }


}
