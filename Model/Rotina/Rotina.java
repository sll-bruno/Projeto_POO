package Model.Rotina;

import java.util.ArrayList;

/**
 * Representa uma rotina de treinos de um aluno.
 * Possui um identificador, nome e uma lista de treinos.
 */
public class Rotina {
    private final int idRotina;
    private String nome;
    private ArrayList<Treino> treinos;
    private static int counterID = 0;
    
    public Rotina(){
        this.treinos = new ArrayList<>();
        this.idRotina = counterID++;
    }

    /**
     * Adiciona um treino à rotina.
     */
    public void addTreino(Treino treino) {
        this.treinos.add(treino);
    }

    /**
     * Remove um treino da rotina.
     */
    public void removeTreino(Treino treino) {
        this.treinos.remove(treino);
    }

    /**
     * Retorna o id da rotina.
     */
    public int getIdRotina() {
        return idRotina;
    }

    /**
     * Retorna o nome da rotina.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da rotina.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a lista de treinos da rotina.
     */
    public ArrayList<Treino> getTreinos() {
        return treinos;
    }
}