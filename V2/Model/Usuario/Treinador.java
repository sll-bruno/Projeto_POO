package Model.Usuario;

import Model.UserType;
import java.util.ArrayList;

public class Treinador extends Usuario {
    private ArrayList<Aluno> alunos;

    public Treinador(String username, String name, int age) {
        super();
        setTipoUsuario(UserType.TREINADOR);
        setUsername(username);
        setName(name);
        setAge(age);
        this.alunos = new ArrayList<>();
    }

    // Construtor padrão para carregamento de dados
    public Treinador() {
        super();
        setTipoUsuario(UserType.TREINADOR);
        this.alunos = new ArrayList<>();
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void addAluno(Aluno aluno) {
        if (aluno != null && !temAluno(aluno)) {
            alunos.add(aluno);
        }
    }

    public void removeAluno(Aluno aluno) {
        if (aluno != null && temAluno(aluno)) {
            alunos.remove(aluno);
        }
    }

    public boolean temAluno(Aluno aluno) {
        return alunos.contains(aluno);
    }
}