package Data;

import java.io.*;
import java.util.ArrayList;
import Model.Rotina.*;
import Model.Usuario.Aluno;

public class DataManager {

    private static final String FILE_NAME = "dados_aluno.txt";

    public static void salvarAluno(Aluno aluno) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("Nome:" + aluno.getName() + "\n");
            writer.write("Idade:" + aluno.getAge() + "\n");
            writer.write("Peso:" + aluno.getWeight() + "\n");
            writer.write("Altura:" + aluno.getHeight() + "\n");
            writer.write("Rotina:" + aluno.getRotina().getNome() + "\n");
            
            writer.write("START_TREINOS\n");
            for (Treino treino : aluno.getRotina().getTreinos()) {
                writer.write("TreinoNome:" + treino.getNome() + "\n");
                writer.write("START_EXERCICIOS\n");
                for (Exercicio ex : treino.getExercicios()) {
                    writer.write(String.format("Exercicio|%s|%s|%d|%d\n",
                        ex.getNome(), ex.getDescricao(), ex.getRepeticoes(), ex.getNumSeries()));
                }
                writer.write("END_EXERCICIOS\n");
            }
            writer.write("END_TREINOS\n");
        }
    }

    public static Aluno carregarAluno() throws IOException, ValidacaoException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return null; // Retorna nulo se o arquivo não existe
        }

        Aluno aluno = new Aluno();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            aluno.setName(reader.readLine().split(":")[1]);
            aluno.setAge(Integer.parseInt(reader.readLine().split(":")[1]));
            aluno.setWeight(Float.parseFloat(reader.readLine().split(":")[1]));
            aluno.setHeight(Float.parseFloat(reader.readLine().split(":")[1]));
            aluno.getRotina().setNome(reader.readLine().split(":")[1]);

            String line;
            if ((line = reader.readLine()).equals("START_TREINOS")) {
                Treino treinoAtual = null;
                while (!(line = reader.readLine()).equals("END_TREINOS")) {
                    if (line.startsWith("TreinoNome:")) {
                        treinoAtual = new Treino(line.split(":")[1]);
                        aluno.getRotina().addTreino(treinoAtual);
                    } else if (line.equals("START_EXERCICIOS") && treinoAtual != null) {
                        while (!(line = reader.readLine()).equals("END_EXERCICIOS")) {
                            if (line.startsWith("Exercicio|")) {
                                String[] parts = line.split("\\|");
                                String nome = parts[1];
                                String desc = parts[2];
                                int reps = Integer.parseInt(parts[3]);
                                int series = Integer.parseInt(parts[4]);
                                treinoAtual.addExercicio(new Exercicio(nome, desc, reps, series));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ValidacaoException("Erro ao ler o arquivo de dados. O arquivo pode estar corrompido.");
        }
        return aluno;
    }
}