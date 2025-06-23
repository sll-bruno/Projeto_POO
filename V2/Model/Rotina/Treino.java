package Model.Rotina;

import java.util.ArrayList;

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

    public void addExercicio(Exercicio exercicio) {
        this.exercicios.add(exercicio);
        exercicio.setTreino(this); // Define a referência de volta para o treino
    }

    public void removeExercicio(Exercicio exercicio) {
        this.exercicios.remove(exercicio);
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

    @Override
    public String toString() {
        return this.nome; // Usado para exibir na JList
    }
}