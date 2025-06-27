package Model.Usuario;

import Model.UserType;
import Model.Rotina.Rotina;
import Data.DataManager;
import java.util.ArrayList;

public class Aluno extends Usuario {
    private float weight;
    private float height;
    private Rotina rotina; 

    public Aluno(String username, String name, int age, float weight, float height){
        super();
        setTipoUsuario(UserType.ALUNO);
        setUsername(username);
        setName(name);
        setAge(age);
        setWeight(weight);
        setHeight(height);
        this.rotina = new Rotina();
        this.rotina.setNome("Minha Rotina");
    }

    // Construtor padrão para carregamento de dados
    public Aluno() {
        super();
        setTipoUsuario(UserType.ALUNO);
        this.rotina = new Rotina();
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public static Aluno buscarAlunoPorUsername(String username) {
        ArrayList<Aluno> alunos = DataManager.carregarTodosAlunos();
        for (Aluno aluno : alunos) {
            if (aluno.getUsername().equals(username))
                return aluno;
        }
        return null; // Retorna nulo se não encontrar o aluno
    }
}