package Model.Rotina;

/**
 * Representa um exercício dentro de um treino.
 * Possui identificador, nome, descrição, número de repetições, número de séries e referência ao treino.
 */
public class Exercicio {
    private int id;
    private String nome;
    private String descricao;
    private int repeticoes;
    private int numSeries;
    private Treino treino;

    /**
     * Construtor padrão do exercício.
     */
    public Exercicio() {}

    /**
     * Construtor completo do exercício.
     */
    public Exercicio(String nome, String descricao, int repeticoes, int numSeries) {
        this.nome = nome;
        this.descricao = descricao;
        this.repeticoes = repeticoes;
        this.numSeries = numSeries;
    }
    
    /**
     * Retorna o id do exercício.
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Define o id do exercício.
     */
    public void setId(int id) {
         this.id = id; 
    }

    /**
     * Retorna o nome do exercício.
     */
    public String getNome() {
         return nome; 
    }

    /**
     * Define o nome do exercício.
     */
    public void setNome(String nome) {
         this.nome = nome; 
    }

    /**
     * Retorna a descrição do exercício.
     */
    public String getDescricao() {
         return descricao; 
    }

    /**
     * Define a descrição do exercício.
     */
    public void setDescricao(String descricao) {
         this.descricao = descricao; 
    }

    /**
     * Retorna o número de repetições do exercício.
     */
    public int getRepeticoes() {
         return repeticoes; 
    }

    /**
     * Define o número de repetições do exercício.
     */
    public void setRepeticoes(int repeticoes) {
         this.repeticoes = repeticoes; 
    }

    public int getNumSeries() {
         return numSeries; 
    }

    public void setNumSeries(int numSeries) {
         this.numSeries = numSeries; 
    }

    public Treino getTreino() {
         return treino; 
    }

    public void setTreino(Treino treino) {
         this.treino = treino; 
    }

}