package Model.Rotina;

import java.util.ArrayList;

public class Rotina {
    private final int idRotina;
    private String nome;
    private  ArrayList<Treino> treinos;
    private static int counterID = 0;
    
    public Rotina(){
        this.treinos = new ArrayList<>();
        this.idRotina = counterID++;
    }

    public int getIdRotina() {
        return idRotina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Treino> getTreinos() {
        return treinos;
    }

}
