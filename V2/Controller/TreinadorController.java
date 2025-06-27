package Controller;

import Model.Usuario.Aluno;
import Model.Usuario.Treinador;
import View.TelaPrincipalTreinador;
import View.TelaPrincipalAluno;

import javax.swing.*;
import java.util.ArrayList;

public class TreinadorController {

    private TelaPrincipalTreinador view;
    private Treinador treinador;

    public TreinadorController(TelaPrincipalTreinador view, String usernameTreinador) {
        this.view = view;
        this.treinador = new Treinador(usernameTreinador, null, 0);
    }

    // Carrega a lista de alunos do treinador
    public void carregarAlunos() {
        ArrayList<Aluno> alunos = treinador.getAlunos();
        view.atualizarListaAlunos(alunos);
    }

    // Adiciona um aluno existente à lista do treinador
    public void adicionarAluno() {
        String username = JOptionPane.showInputDialog(view, "Digite o nome de usuário do aluno já cadastrado:",
                "Adicionar Aluno", JOptionPane.PLAIN_MESSAGE);
        if (username != null && !username.trim().isEmpty()) {
            Aluno aluno = Aluno.buscarAlunoPorUsername(username.trim()); // Implemente esse método estático em Aluno
            if (aluno != null) {
                if (!treinador.getAlunos().contains(aluno)) {
                    treinador.addAluno(aluno);
                    carregarAlunos();
                    view.mostrarMensagem("Aluno adicionado à sua lista!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    view.mostrarMensagem("Esse aluno já está na sua lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                view.mostrarMensagem("Aluno não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Remove um aluno da lista do treinador (não deleta do sistema)
    public void removerAluno(Aluno aluno) {
        if (aluno == null) {
            view.mostrarMensagem("Selecione um aluno para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view,
                "Remover aluno da sua lista? (O aluno não será excluído do sistema)", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            treinador.removeAluno(aluno);
            carregarAlunos();
            view.mostrarMensagem("Aluno removido da sua lista.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Permite editar os treinos do aluno selecionado
    public void editarTreinosAluno(Aluno aluno) {
        if (aluno == null) {
            view.mostrarMensagem("Selecione um aluno para editar os treinos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Abre a tela do aluno, permitindo ao treinador editar os treinos
        TelaPrincipalAluno telaAluno = new TelaPrincipalAluno(aluno.getUsername());
        telaAluno.setVisible(true);
    }

    public void sair() {
        view.dispose();
        new View.TelaSelecaoUsuario().setVisible(true); // Volta para a tela de seleção de usuário
    }
}
