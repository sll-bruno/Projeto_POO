package View;

import Controller.TreinadorController;
import Model.Usuario.Aluno;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaPrincipalTreinador extends JFrame {

    private String usernameTreinador;
    private TreinadorController controller;
    private JList<Aluno> listaAlunos;
    private DefaultListModel<Aluno> listModel;
    private JButton btnAdicionarAluno, btnRemoverAluno, btnEditarTreinos, btnSalvar, btnSair;

    public TelaPrincipalTreinador(String usernameTreinador) {
        this.usernameTreinador = usernameTreinador;
        this.controller = new TreinadorController(this, usernameTreinador);

        setTitle("Painel do Treinador: " + this.usernameTreinador);
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Lista de alunos
        listModel = new DefaultListModel<>();
        listaAlunos = new JList<>(listModel);
        listaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listaAlunos);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnAdicionarAluno = new JButton("Adicionar Aluno");
        btnRemoverAluno = new JButton("Remover Aluno");
        btnEditarTreinos = new JButton("Editar Treinos do Aluno");
        btnSalvar = new JButton("Salvar Alterações");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnAdicionarAluno);
        painelBotoes.add(btnRemoverAluno);
        painelBotoes.add(btnEditarTreinos);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnSair);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Listeners
        btnAdicionarAluno.addActionListener(e -> controller.adicionarAluno());
        btnRemoverAluno.addActionListener(e -> controller.removerAluno(listaAlunos.getSelectedValue()));
        btnEditarTreinos.addActionListener(e -> controller.editarTreinosAluno(listaAlunos.getSelectedValue()));
        btnSalvar.addActionListener(e -> controller.salvarDados());
        btnSair.addActionListener(e -> controller.sair());

        // Carrega lista inicial de alunos do treinador
        controller.carregarDadosIniciais();
    }

    // Atualiza a lista de alunos exibida
    public void atualizarListaAlunos(ArrayList<Aluno> alunos) {
        listModel.clear();
        for (Aluno a : alunos) {
            listModel.addElement(a);
        }
    }

    // Mostra mensagens para o usuário
    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}
