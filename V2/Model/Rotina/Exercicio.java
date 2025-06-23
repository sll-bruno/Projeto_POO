package Model.Rotina;

public class Exercicio {
    private int id;
    private String nome;
    private String descricao;
    private int repeticoes;
    private int numSeries;
    private Treino treino; // Referência ao Treino

    public Exercicio() {}

    public Exercicio(String nome, String descricao, int repeticoes, int numSeries) {
        this.nome = nome;
        this.descricao = descricao;
        this.repeticoes = repeticoes;
        this.numSeries = numSeries;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getRepeticoes() { return repeticoes; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }
    public int getNumSeries() { return numSeries; }
    public void setNumSeries(int numSeries) { this.numSeries = numSeries; }
    public Treino getTreino() { return treino; }
    public void setTreino(Treino treino) { this.treino = treino; }
}