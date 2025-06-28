package Data;

import java.io.*;
import Model.Rotina.*;
import Model.Usuario.*;
import java.util.ArrayList;

public class DataManager {

    private static final String DIRETORIO_DADOS_ALUNOS = "dados_alunos/";
    private static final String DIRETORIO_DADOS_TREINADORES = "dados_treinadores/";

    private static String gerarNomeArquivoAluno(String usernameAluno) {
        return DIRETORIO_DADOS_ALUNOS + File.separator + "dados_aluno_" + usernameAluno + ".txt";
    }

    private static String gerarNomeArquivoTreinador(String usernameTreinador) {
        return DIRETORIO_DADOS_TREINADORES + File.separator + "dados_treinador_" + usernameTreinador + ".txt";
    }

    public static void salvarAluno(Aluno aluno) throws IOException {
        String fileName = gerarNomeArquivoAluno(aluno.getUsername());

        // Cria o diretório se não existir
        File arquivo = new File(fileName);
        if (!arquivo.getParentFile().exists()) {
            arquivo.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Username:" + aluno.getUsername() + "\n");
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
                    writer.write(String.format("Exercicio:%s|%s|%d|%d\n",
                        ex.getNome(), ex.getDescricao(), ex.getRepeticoes(), ex.getNumSeries()));
                }
                writer.write("END_EXERCICIOS\n");
            }
            writer.write("END_TREINOS\n");
        }
    }

    public static Aluno carregarAluno(String usernameAluno) throws IOException, ValidacaoException {
        String fileName = gerarNomeArquivoAluno(usernameAluno);
        File file = new File(fileName);
        if (!file.exists()) {
            return null; // Retorna nulo se o arquivo não existe
        }

        Aluno aluno = new Aluno();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            aluno.setUsername(reader.readLine().split(":")[1]);
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
                            if (line.startsWith("Exercicio:")) {
                                String[] lineParts = line.split(":", 2);
                                String[] parts = lineParts[1].split("\\|");
                                String nome = parts[0];
                                String desc = parts[1];
                                int reps = Integer.parseInt(parts[2]);
                                int series = Integer.parseInt(parts[3]);
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

    public static boolean existeArquivoAluno(String usernameAluno) {
        String fileName = gerarNomeArquivoAluno(usernameAluno);
        return new File(fileName).exists();
    }

    public static ArrayList<Aluno> carregarTodosAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<>();
        File dir = new File(DIRETORIO_DADOS_ALUNOS);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().startsWith("dados_aluno_") && file.getName().endsWith(".txt")) {
                    try {
                        Aluno aluno = carregarAluno(file.getName().replace("dados_aluno_", "").replace(".txt", ""));
                        if (aluno != null) {
                            alunos.add(aluno);
                        }
                    } catch (IOException | ValidacaoException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return alunos;
    }
    
    public static void salvarTreinador(Treinador treinador) throws IOException {
        String fileName = gerarNomeArquivoTreinador(treinador.getUsername());
        
        // Criar diretório se não existir
        File arquivo = new File(fileName);
        if (!arquivo.getParentFile().exists()) {
            arquivo.getParentFile().mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Username:" + treinador.getUsername() + "\n");
            writer.write("Nome:" + treinador.getName() + "\n");
            writer.write("Idade:" + treinador.getAge() + "\n");
            writer.write("START_ALUNOS\n");
            for (Aluno aluno : treinador.getAlunos()) {
                writer.write("Aluno:" + aluno.getUsername() + "\n");
            }
            writer.write("END_ALUNOS\n");
        }
    }

    public static Treinador carregarTreinador(String usernameTreinador) throws IOException, ValidacaoException {
        String fileName = gerarNomeArquivoTreinador(usernameTreinador);
        File file = new File(fileName);
        if (!file.exists()) {
            return null; // Retorna nulo se o arquivo não existe
        }

        Treinador treinador = new Treinador();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            treinador.setUsername(reader.readLine().split(":")[1]);
            treinador.setName(reader.readLine().split(":")[1]);
            treinador.setAge(Integer.parseInt(reader.readLine().split(":")[1]));

            String line;
            if ((line = reader.readLine()).equals("START_ALUNOS")) {
                while (!(line = reader.readLine()).equals("END_ALUNOS")) {
                    if (line.startsWith("Aluno:")) {
                        String usernameAluno = line.split(":")[1];
                        Aluno aluno = carregarAluno(usernameAluno);
                        if (aluno != null) {
                            treinador.addAluno(aluno);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ValidacaoException("Erro ao ler o arquivo de dados do treinador. O arquivo pode estar corrompido.");
        }
        return treinador;
    }

    public static boolean existeArquivoTreinador(String usernameTreinador) {
        String fileName = gerarNomeArquivoTreinador(usernameTreinador);
        return new File(fileName).exists();
    }
}