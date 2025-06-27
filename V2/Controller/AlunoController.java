package Controller;

import Data.DataManager;
import Data.ValidacaoException;
import Model.Rotina.*;
import Model.Usuario.*;
import View.TelaPrincipalAluno;
import java.io.IOException;
import javax.swing.*;

public class AlunoController {
    
    private TelaPrincipalAluno view;
    private Aluno aluno;

    public AlunoController(TelaPrincipalAluno view, String usernameAluno) {
        this.view = view;
        this.aluno = new Aluno(usernameAluno, null, 0, 0.0f, 0.0f);
    }

    public void carregarDadosIniciais() {
        try {
            Aluno alunoCarregado = DataManager.carregarAluno(this.aluno.getUsername());
            if (alunoCarregado == null) { // Se não houver arquivo salvo, cria um aluno novo
                this.aluno = criarNovoAluno(aluno.getUsername());
                if (this.aluno != null) {
                    DataManager.salvarAluno(this.aluno); // Salva o novo aluno
                    view.mostrarMensagem("Novo aluno criado com sucesso!", "Cadastro de novo usuário", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Se o usuário cancelar o cadastro, volta para a tela inicial
                    view.dispose();
                    new View.TelaSelecaoUsuario().setVisible(true);
                    return;
                }
            } else {
                // Se o aluno já existe, carrega os dados
                this.aluno = alunoCarregado;
            }

            view.atualizarListaTreinos(aluno.getRotina().getTreinos());
            view.atualizarTabelaExercicios(null); // Limpa a tabela de exercícios
        } catch (IOException | ValidacaoException e) {
            view.mostrarMensagem("Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Aluno criarNovoAluno(String username) {
        try {
            // Solicitar dados do aluno
            // Solicitar nome
            String nome = JOptionPane.showInputDialog(view,
                "Bem-vindo!\nDigite seu nome completo:",
                "Cadastro - Nome",
                JOptionPane.QUESTION_MESSAGE);
            
            if (nome == null){ // Usuário cancelou
                view.dispose();
                new View.TelaSelecaoUsuario().setVisible(true);
                return null;
            }
            if (nome.trim().isEmpty())
                throw new ValidacaoException("Digite um nome.");
            if (!nome.matches("[a-zA-Z ]+")) 
                throw new ValidacaoException("O nome deve conter apenas letras e espaços.");

            // Solicitar idade
            String idadeStr = JOptionPane.showInputDialog(view,
                "Digite sua idade:",
                "Cadastro - Idade",
                JOptionPane.QUESTION_MESSAGE);

            if (idadeStr == null){ // Usuário cancelou
                view.dispose();
                new View.TelaSelecaoUsuario().setVisible(true);
                return null;
            }
            if (idadeStr.trim().isEmpty())
                throw new ValidacaoException("Digite uma idade.");
            int idade = Integer.parseInt(idadeStr.trim());
            if (idade <= 0 || idade > 100)
                throw new ValidacaoException("Idade deve ser um número entre 1 e 100 anos.");

            // Solicitar peso
            String pesoStr = JOptionPane.showInputDialog(view,
                "Digite seu peso (kg, ex: 82.5):",
                "Cadastro - Peso",
                JOptionPane.QUESTION_MESSAGE);
            
            if (pesoStr == null){ // Usuário cancelou
                view.dispose();
                new View.TelaSelecaoUsuario().setVisible(true);
                return null;
            }
            if (pesoStr.trim().isEmpty())
                throw new ValidacaoException("Digite um peso.");
            float peso = Float.parseFloat(pesoStr.trim().replace(",", "."));
            if (peso <= 0 || peso > 300)
                throw new ValidacaoException("Peso deve ser um número entre 1 e 300 kg.");

            // Solicitar altura
            String alturaStr = JOptionPane.showInputDialog(view,
                "Digite sua altura (m, ex: 1.75):",
                "Cadastro - Altura",
                JOptionPane.QUESTION_MESSAGE);

            if (alturaStr == null){ // Usuário cancelou
                view.dispose();
                new View.TelaSelecaoUsuario().setVisible(true);
                return null;
            }
            if (alturaStr.trim().isEmpty())
                throw new ValidacaoException("Digite uma altura.");
            float altura = Float.parseFloat(alturaStr.trim().replace(",", "."));
            if (altura <= 1 || altura > 2.5)
                throw new ValidacaoException("Altura deve ser um número entre 1 e 2.5 metros.");

            return new Aluno(username, nome, idade, peso, altura);

        } catch (NumberFormatException e) {
            view.mostrarMensagem("Por favor, digite apenas números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return criarNovoAluno(username); // Tentar novamente
        } catch (ValidacaoException e) {
            view.mostrarMensagem(e.getMessage(), "Dados Inválidos", JOptionPane.ERROR_MESSAGE);
            return criarNovoAluno(username); // Tentar novamente
        }
    }

    public void selecionarTreino(Treino treino) {
        if (treino != null) {
            view.atualizarTabelaExercicios(treino.getExercicios());
        } else {
            view.atualizarTabelaExercicios(null);
        }
    }

    public void adicionarTreino() {
        String nomeTreino = JOptionPane.showInputDialog(view, "Digite o nome do novo treino:", "Adicionar Treino", JOptionPane.PLAIN_MESSAGE);
        if (nomeTreino != null && !nomeTreino.trim().isEmpty()) {
            Treino novoTreino = new Treino(nomeTreino.trim());
            aluno.getRotina().addTreino(novoTreino);
            view.atualizarListaTreinos(aluno.getRotina().getTreinos());
        }
    }

    public void removerTreino(Treino treino) {
        if (treino == null) {
            view.mostrarMensagem("Por favor, selecione um treino para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja remover o treino '" + treino.getNome() + "'?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            aluno.getRotina().removeTreino(treino);
            view.atualizarListaTreinos(aluno.getRotina().getTreinos());
            view.atualizarTabelaExercicios(null);
        }
    }

    public void adicionarExercicio(Treino treino) {
        if (treino == null) {
            view.mostrarMensagem("Por favor, selecione um treino para adicionar o exercício.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JTextField nomeField = new JTextField();
        JTextField descField = new JTextField();
        JTextField repsField = new JTextField();
        JTextField seriesField = new JTextField();

        Object[] message = {
            "Nome:", nomeField,
            "Descrição:", descField,
            "Séries:", seriesField,
            "Repetições:", repsField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Adicionar Exercício", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                String desc = descField.getText();
                if (nome.trim().isEmpty()) throw new ValidacaoException("O nome não pode ser vazio.");
                
                int reps = Integer.parseInt(repsField.getText());
                int series = Integer.parseInt(seriesField.getText());

                Exercicio novoExercicio = new Exercicio(nome, desc, reps, series);
                treino.addExercicio(novoExercicio);
                view.atualizarTabelaExercicios(treino.getExercicios());

            } catch (NumberFormatException e) {
                view.mostrarMensagem("Repetições e séries devem ser números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (ValidacaoException e) {
                 view.mostrarMensagem(e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editarExercicio(Treino treino, int linhaSelecionada) {
         if (treino == null || linhaSelecionada == -1) {
            view.mostrarMensagem("Selecione um exercício para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Exercicio ex = treino.getExercicios().get(linhaSelecionada);
        
        JTextField nomeField = new JTextField(ex.getNome());
        JTextField descField = new JTextField(ex.getDescricao());
        JTextField repsField = new JTextField(String.valueOf(ex.getRepeticoes()));
        JTextField seriesField = new JTextField(String.valueOf(ex.getNumSeries()));

        Object[] message = {
            "Nome:", nomeField,
            "Descrição:", descField,
            "Séries:", seriesField,
            "Repetições:", repsField,        };
        
        int option = JOptionPane.showConfirmDialog(view, message, "Editar Exercício", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                ex.setNome(nomeField.getText());
                ex.setDescricao(descField.getText());
                ex.setRepeticoes(Integer.parseInt(repsField.getText()));
                ex.setNumSeries(Integer.parseInt(seriesField.getText()));
                view.atualizarTabelaExercicios(treino.getExercicios());
            } catch (NumberFormatException e) {
                 view.mostrarMensagem("Repetições e séries devem ser números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void removerExercicio(Treino treino, int linhaSelecionada) {
        if (treino == null || linhaSelecionada == -1) {
            view.mostrarMensagem("Selecione um exercício para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja remover este exercício?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Exercicio ex = treino.getExercicios().get(linhaSelecionada);
            treino.removeExercicio(ex);
            view.atualizarTabelaExercicios(treino.getExercicios());
        }
    }
    
    public void salvarDados() {
        try {
            DataManager.salvarAluno(aluno);
            view.mostrarMensagem("Dados salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            view.mostrarMensagem("Erro ao salvar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sair() {
        String[] opcoes = {"Salvar e Sair", "Sair sem Salvar", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(view, "Deseja salvar as alterações antes de sair?",
        "Sair",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0] // Padrão para "Cancelar"
        );
        if (escolha == 0) // Salvar e sair
            salvarDados();
        else if (escolha == 2 || escolha == JOptionPane.CLOSED_OPTION) // Cancelar
            return; // Não faz nada, volta para a tela
        view.dispose();
        new View.TelaSelecaoUsuario().setVisible(true); // Volta para a tela de seleção de usuário
    }
}