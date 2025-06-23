package Model.Usuario;

import Model.UserType;

public abstract class Usuario {
    private final int id;
    private String name;
    private String password;
    private UserType tipoUsuario;
    private static int counterID = 1;

    public Usuario() {
        this.id = counterID++;
    }

    //Define getters and setters for the attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId(){
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setTipoUsuario(UserType tipo){
        this.tipoUsuario = tipo;
    }

    public UserType getTipoUsuario() {
        return tipoUsuario;
    }
}
