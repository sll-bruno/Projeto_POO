package Controller;

import Data.DataManager;
import Data.ValidacaoException;
import Model.Rotina.*;
import Model.Usuario.Aluno;
import View.TelaPrincipalAluno;
import java.io.IOException;
import javax.swing.*;

public class AppController {
    
    private TelaPrincipalAluno view;
    private Aluno aluno;

    public AppController(TelaPrincipalAluno view) {
        this.view = view;
    }

    public void carregarDadosIniciais() {
        try {
            this.aluno = DataManager.carregarAluno();
            if (this.aluno == null) { // Se não houver arquivo salvo, cria um aluno novo
                this.aluno = new Aluno("Novo Aluno", 25, 70.0f, 1.75f);
            }
            view.atualizarListaTreinos(aluno.getRotina().getTreinos());
            view.atualizarTabelaExercicios(null); // Limpa a tabela de exercícios
        } catch (IOException | ValidacaoException e) {
            view.mostrarMensagem("Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
            "Repetições:", repsField,
            "Séries:", seriesField
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
            "Repetições:", repsField,
            "Séries:", seriesField
        };
        
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
}