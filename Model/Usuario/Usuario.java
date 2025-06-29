package Model.Usuario;

import Model.UserType;

/**
 * Classe abstrata base para usuários do sistema.
 * Contém informações comuns como id, nome, username, senha, idade e tipo de usuário.
 */
public abstract class Usuario {
    private final int id;
    private String name;
    private String username;
    private String password;
    private int age;
    private UserType tipoUsuario;
    private static int counterID = 1;

    /**
     * Construtor que gera um id único para cada usuário.
     */
    public Usuario() {
        this.id = counterID++;
    }

    /**
     * Retorna o nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Retorna o nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do usuário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o id do usuário.
     */
    public int getId(){
        return id;
    }

    /**
     * Define a senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna a senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a idade do usuário.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retorna a idade do usuário.
     */
    public int getAge() {
        return age;
    }

    /**
     * Define o tipo do usuário (ALUNO ou TREINADOR).
     */
    public void setTipoUsuario(UserType tipo){
        this.tipoUsuario = tipo;
    }

    /**
     * Retorna o tipo do usuário.
     */
    public UserType getTipoUsuario() {
        return tipoUsuario;
    }
}