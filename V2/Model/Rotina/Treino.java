package Model.Rotina;

import java.util.ArrayList;

/**
 * Representa um treino dentro de uma rotina.
 * Possui um identificador, nome e uma lista de exercícios.
 */
public class Treino {
    private int idTreino;
    private String nome;
    private ArrayList<Exercicio> exercicios;
    
    public Treino(){
        this.exercicios = new ArrayList<>();
    }
     public Treino(String nome){
        this.nome = nome;
        this.exercicios = new ArrayList<>();
    }

    /**
     * Adiciona um exercício ao treino.
     */
    public void addExercicio(Exercicio exercicio) {
        this.exercicios.add(exercicio);
        exercicio.setTreino(this); //Define referencia para o treino que o exercicio pertence
    }
    /**
     * Remove um exercício do treino.
     */
    public void removeExercicio(Exercicio exercicio) {
        this.exercicios.remove(exercicio);
    }
    /**
     * Retorna o id do treino.
     */
    public int getIdTreino() {
        return idTreino;
    }
    /**
     * Define o id do treino.
     */
    public void setIdTreino(int idTreino) {
        this.idTreino = idTreino;
    }
    /**
     * Retorna o nome do treino.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome do treino.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Retorna a lista de exercícios do treino.
     */
    public ArrayList<Exercicio> getExercicios() {
        return exercicios;
    }

    @Override
    public String toString() {
        return this.nome; 
    }
}