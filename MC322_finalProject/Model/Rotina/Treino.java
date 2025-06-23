package Model.Rotina;

import java.util.ArrayList;

public class Treino {
    private int idTreino;
    private String nome;
    private  ArrayList<Exercicio> exercicios;
    
    public Treino(){
        this.exercicios = new ArrayList<>();
    }

    public int getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(int idTreino) {
        this.idTreino = idTreino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Exercicio> getExercicios() {
        return exercicios;
    }

}
