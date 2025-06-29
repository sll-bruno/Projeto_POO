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

        //Lista de alunos
        listModel = new DefaultListModel<>();
        listaAlunos = new JList<>(listModel);
        listaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaAlunos.setFont(new Font("Arial", Font.PLAIN, 16));
        listaAlunos.setFixedCellHeight(32);
        listaAlunos.setBackground(new Color(235, 245, 251));
        listaAlunos.setSelectionBackground(new Color(46, 134, 222, 80));
        listaAlunos.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(listaAlunos);

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setBackground(new Color(245, 245, 245));
        btnAdicionarAluno = new JButton("Adicionar Aluno");
        btnAdicionarAluno.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdicionarAluno.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnAdicionarAluno.setForeground(Color.WHITE);
        btnAdicionarAluno.setFocusPainted(false);
        btnAdicionarAluno.setPreferredSize(new Dimension(150, 38));
        btnRemoverAluno = new JButton("Remover Aluno");
        btnRemoverAluno.setFont(new Font("Arial", Font.BOLD, 15));
        btnRemoverAluno.setBackground(new Color(200, 80, 80)); // vermelho menos saturado
        btnRemoverAluno.setForeground(Color.WHITE);
        btnRemoverAluno.setFocusPainted(false);
        btnRemoverAluno.setPreferredSize(new Dimension(150, 38));
        btnEditarTreinos = new JButton("Editar Treinos do Aluno");
        btnEditarTreinos.setFont(new Font("Arial", Font.BOLD, 15));
        btnEditarTreinos.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnEditarTreinos.setForeground(Color.WHITE);
        btnEditarTreinos.setFocusPainted(false);
        btnEditarTreinos.setPreferredSize(new Dimension(210, 38));
        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 15));
        btnSair.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(100, 38));
        painelBotoes.removeAll();
        painelBotoes.add(btnAdicionarAluno);
        painelBotoes.add(btnRemoverAluno);
        painelBotoes.add(btnEditarTreinos);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnSair);

        setLayout(new BorderLayout());
        JPanel painelCentro = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(245, 245, 245));
            }
        };
        painelCentro.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(46,134,222),2), "Alunos", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(46,134,222)));
        painelCentro.add(scrollPane, BorderLayout.CENTER);
        add(painelCentro, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        //adiciona listeners aos elemtnos
        btnAdicionarAluno.addActionListener(e -> controller.adicionarAluno());
        btnRemoverAluno.addActionListener(e -> controller.removerAluno(listaAlunos.getSelectedValue()));
        btnEditarTreinos.addActionListener(e -> controller.editarTreinosAluno(listaAlunos.getSelectedValue()));
        btnSalvar.addActionListener(e -> controller.salvarDados());
        btnSair.addActionListener(e -> controller.sair());

        controller.carregarDadosIniciais();
    }

    //Atualiza a lista de alunos exibida
    public void atualizarListaAlunos(ArrayList<Aluno> alunos) {
        listModel.clear();
        for (Aluno a : alunos) {
            listModel.addElement(a);
        }
    }

    //Mostra mensagens para o usuário
    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}
