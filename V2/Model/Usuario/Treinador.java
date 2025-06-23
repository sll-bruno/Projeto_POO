package Model.Usuario;

import Model.UserType;

public class Treinador extends Usuario {
    public Treinador() {
        super();
        setTipoUsuario(UserType.PROFESSOR);
    }
}