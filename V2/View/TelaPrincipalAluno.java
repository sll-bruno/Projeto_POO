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
        listaTreinos.setFont(new Font("Arial", Font.PLAIN, 16));
        listaTreinos.setFixedCellHeight(32);
        listaTreinos.setBackground(new Color(235, 245, 251));
        listaTreinos.setSelectionBackground(new Color(46, 134, 222, 80));
        listaTreinos.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        listaTreinos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.selecionarTreino(listaTreinos.getSelectedValue());
            }
        });

        JPanel painelEsquerdo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(245, 245, 245));
            }
        };
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(46,134,222),2), "Meus Treinos", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(46,134,222)));
        painelEsquerdo.add(new JScrollPane(listaTreinos), BorderLayout.CENTER);

        JPanel botoesTreino = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesTreino.setOpaque(false);
        btnAdicionarTreino = new JButton("Adicionar");
        btnAdicionarTreino.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdicionarTreino.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnAdicionarTreino.setForeground(Color.WHITE);
        btnAdicionarTreino.setFocusPainted(false);
        btnAdicionarTreino.setPreferredSize(new Dimension(110, 36));
        btnRemoverTreino = new JButton("Remover");
        btnRemoverTreino.setFont(new Font("Arial", Font.BOLD, 15));
        btnRemoverTreino.setBackground(new Color(200, 80, 80)); // vermelho menos saturado
        btnRemoverTreino.setForeground(Color.WHITE);
        btnRemoverTreino.setFocusPainted(false);
        btnRemoverTreino.setPreferredSize(new Dimension(110, 36));
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
        tabelaExercicios.setFont(new Font("Arial", Font.PLAIN, 15));
        tabelaExercicios.setRowHeight(28);
        tabelaExercicios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tabelaExercicios.setBackground(new Color(235, 245, 251));
        tabelaExercicios.setSelectionBackground(new Color(46, 134, 222, 80));

        JPanel painelDireito = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(245, 245, 245));
            }
        };
        painelDireito.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(46,134,222),2), "Exercícios do Treino", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(46,134,222)));
        painelDireito.add(new JScrollPane(tabelaExercicios), BorderLayout.CENTER);

        JPanel botoesExercicio = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesExercicio.setOpaque(false);
        btnAdicionarExercicio = new JButton("Adicionar");
        btnAdicionarExercicio.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdicionarExercicio.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnAdicionarExercicio.setForeground(Color.WHITE);
        btnAdicionarExercicio.setFocusPainted(false);
        btnAdicionarExercicio.setPreferredSize(new Dimension(110, 36));
        btnEditarExercicio = new JButton("Editar");
        btnEditarExercicio.setFont(new Font("Arial", Font.BOLD, 15));
        btnEditarExercicio.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnEditarExercicio.setForeground(Color.WHITE);
        btnEditarExercicio.setFocusPainted(false);
        btnEditarExercicio.setPreferredSize(new Dimension(110, 36));
        btnRemoverExercicio = new JButton("Remover");
        btnRemoverExercicio.setFont(new Font("Arial", Font.BOLD, 15));
        btnRemoverExercicio.setBackground(new Color(200, 80, 80)); // vermelho menos saturado
        btnRemoverExercicio.setForeground(Color.WHITE);
        btnRemoverExercicio.setFocusPainted(false);
        btnRemoverExercicio.setPreferredSize(new Dimension(110, 36));
        botoesExercicio.add(btnAdicionarExercicio);
        botoesExercicio.add(btnEditarExercicio);
        botoesExercicio.add(btnRemoverExercicio);
        painelDireito.add(botoesExercicio, BorderLayout.SOUTH);

        // -- Painel Inferior (Salvar) --
        btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 15));
        btnSalvar.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new Dimension(170, 38));
        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 15));
        btnSair.setBackground(new Color(70, 130, 180)); // azul menos saturado
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(110, 38));
        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(new Color(245, 245, 245));
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