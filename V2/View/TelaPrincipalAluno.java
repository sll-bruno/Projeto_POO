package View;

import Controller.AlunoController;
import Model.Rotina.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaPrincipalAluno extends JFrame {

    private String usernameAluno;
    private AlunoController controller;
    private JList<Treino> listaTreinos;
    private JTable tabelaExercicios;
    private DefaultListModel<Treino> listModel;
    private DefaultTableModel tableModel;
    private JButton btnAdicionarTreino, btnRemoverTreino, btnAdicionarExercicio, btnEditarExercicio,
            btnRemoverExercicio, btnSalvar, btnSair;

    public TelaPrincipalAluno(String usernameAluno) {
        this.controller = new AlunoController(this, usernameAluno);
        this.usernameAluno = usernameAluno;
        setTitle("Gerenciador de Treinos do Aluno " + this.usernameAluno);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.sair();
            }
        });
        setLocationRelativeTo(null);

        // -- Painel Esquerdo (Lista de Treinos) --
        listModel = new DefaultListModel<>();
        listaTreinos = new JList<>(listModel);
        listaTreinos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTreinos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.selecionarTreino(listaTreinos.getSelectedValue());
            }
        });

        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder("Meus Treinos"));
        painelEsquerdo.add(new JScrollPane(listaTreinos), BorderLayout.CENTER);

        JPanel botoesTreino = new JPanel(new FlowLayout());
        btnAdicionarTreino = new JButton("Adicionar");
        btnRemoverTreino = new JButton("Remover");
        botoesTreino.add(btnAdicionarTreino);
        botoesTreino.add(btnRemoverTreino);
        painelEsquerdo.add(botoesTreino, BorderLayout.SOUTH);

        // -- Painel Direito (Tabela de Exercícios) --
        String[] colunas = { "Nome", "Descrição", "Séries", "Repetições" };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável diretamente
            }
        };

        tabelaExercicios = new JTable(tableModel);

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setBorder(BorderFactory.createTitledBorder("Exercícios do Treino"));
        painelDireito.add(new JScrollPane(tabelaExercicios), BorderLayout.CENTER);

        JPanel botoesExercicio = new JPanel(new FlowLayout());
        btnAdicionarExercicio = new JButton("Adicionar");
        btnEditarExercicio = new JButton("Editar");
        btnRemoverExercicio = new JButton("Remover");
        botoesExercicio.add(btnAdicionarExercicio);
        botoesExercicio.add(btnEditarExercicio);
        botoesExercicio.add(btnRemoverExercicio);
        painelDireito.add(botoesExercicio, BorderLayout.SOUTH);

        // -- Painel Inferior (Salvar) --
        btnSalvar = new JButton("Salvar Alterações");
        btnSair = new JButton("Sair");
        JPanel painelInferior = new JPanel();
        painelInferior.add(btnSalvar);
        painelInferior.add(btnSair);

        // -- Split Pane --
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelEsquerdo, painelDireito);
        splitPane.setDividerLocation(250);

        // -- Adicionar componentes ao Frame --
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        // -- Adicionar Listeners --
        btnAdicionarTreino.addActionListener(e -> controller.adicionarTreino());
        btnRemoverTreino.addActionListener(e -> controller.removerTreino(listaTreinos.getSelectedValue()));
        btnAdicionarExercicio.addActionListener(e -> controller.adicionarExercicio(listaTreinos.getSelectedValue()));
        btnEditarExercicio.addActionListener(
                e -> controller.editarExercicio(listaTreinos.getSelectedValue(), tabelaExercicios.getSelectedRow()));
        btnRemoverExercicio.addActionListener(
                e -> controller.removerExercicio(listaTreinos.getSelectedValue(), tabelaExercicios.getSelectedRow()));
        btnSalvar.addActionListener(e -> controller.salvarDados());
        btnSair.addActionListener(e -> controller.sair());

        // Carrega dados iniciais
        controller.carregarDadosIniciais();
    }

    public void atualizarListaTreinos(ArrayList<Treino> treinos) {
        listModel.clear();
        for (Treino t : treinos) {
            listModel.addElement(t);
        }
    }

    public void atualizarTabelaExercicios(ArrayList<Exercicio> exercicios) {
        tableModel.setRowCount(0); // Limpa a tabela
        if (exercicios != null) {
            for (Exercicio ex : exercicios) {
                tableModel.addRow(
                        new Object[] { ex.getNome(), ex.getDescricao(), ex.getNumSeries(), ex.getRepeticoes() });
            }
        }
    }

    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}