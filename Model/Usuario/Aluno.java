package Model.Usuario;

import Model.UserType;
import Model.Rotina.Rotina;
import Data.DataManager;
import java.util.ArrayList;

/**
 * Representa um aluno do sistema.
 * Herda de Usuario e possui atributos de peso, altura e uma rotina de treinos.
 */
public class Aluno extends Usuario {
    private float weight;
    private float height;
    private Rotina rotina;

    /**
     * Construtor completo do aluno.
     */
    public Aluno(String username, String name, int age, float weight, float height) {
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

    //Construtor padrao
    /**
     * Construtor padrão do aluno.
     */
    public Aluno() {
        super();
        setTipoUsuario(UserType.ALUNO);
        this.rotina = new Rotina();
    }

    /**
     * Retorna o peso do aluno.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Define o peso do aluno.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Retorna a altura do aluno.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Define a altura do aluno.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Retorna a rotina do aluno.
     */
    public Rotina getRotina() {
        return rotina;
    }

    public static Aluno buscarAlunoPorUsername(String username) {
        ArrayList<Aluno> alunos = DataManager.carregarTodosAlunos();
        for (Aluno aluno : alunos) {
            if (aluno == null)
                continue;
            String alunoUsername = aluno.getUsername();
            if (alunoUsername != null && alunoUsername.equals(username))
                return aluno;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}