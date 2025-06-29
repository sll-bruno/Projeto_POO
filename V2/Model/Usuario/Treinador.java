package Model.Usuario;

import Model.UserType;
import java.util.ArrayList;

/**
 * Representa um treinador do sistema.
 * Herda de Usuario e mantém uma lista de alunos associados.
 */
public class Treinador extends Usuario {
    private ArrayList<Aluno> alunos;

    /**
     * Construtor completo do treinador.
     */
    public Treinador(String username, String name, int age) {
        super();
        setTipoUsuario(UserType.TREINADOR);
        setUsername(username);
        setName(name);
        setAge(age);
        this.alunos = new ArrayList<>();
    }

    //Construtor padrão
    /**
     * Construtor padrão do treinador.
     */
    public Treinador() {
        super();
        setTipoUsuario(UserType.TREINADOR);
        this.alunos = new ArrayList<>();
    }

    /**
     * Retorna a lista de alunos do treinador.
     */
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    /**
     * Adiciona um aluno à lista do treinador.
     */
    public void addAluno(Aluno aluno) {
        if (aluno != null && !temAluno(aluno)) {
            alunos.add(aluno);
        }
    }

    /**
     * Remove um aluno da lista do treinador.
     */
    public void removeAluno(Aluno aluno) {
        if (aluno != null && temAluno(aluno)) {
            alunos.remove(aluno);
        }
    }

    /**
     * Verifica se o treinador possui determinado aluno.
     */
    public boolean temAluno(Aluno aluno) {
        return alunos.contains(aluno);
    }
}