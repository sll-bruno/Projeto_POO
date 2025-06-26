package View;

import Data.DataManager;
import Data.ValidacaoException;
import Model.Usuario.Treinador;
import Model.Usuario.Aluno;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipalTreinador extends JFrame {
    
    private Treinador treinador; // Objeto principal que contém todas as informações
    private JList<String> listaAlunos;
    private DefaultListModel<String> listModel;
    private JButton btnAdicionarAluno, btnRemoverAluno, btnAbrirTreino, btnSair;
    
    public TelaPrincipalTreinador(String nomeTreinador) {
        // Carregar ou criar treinador
        try {
            this.treinador = DataManager.carregarTreinador(nomeTreinador);
            if (this.treinador == null) {
                this.treinador = criarNovoTreinador(nomeTreinador);
                if (this.treinador != null) {
                    DataManager.salvarTreinador(this.treinador);
                } else {
                    // Se cancelou a criação, volta para tela anterior
                    new TelaSelecaoUsuario().setVisible(true);
                    this.dispose();
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados do treinador: " + e.getMessage());
            this.treinador = new Treinador(nomeTreinador, nomeTreinador, 0);
        }
        
        setTitle("Painel do Treinador " + this.treinador.getName() + " (Idade: " + this.treinador.getAge() + ")");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        setupListeners();
        carregarAlunosNaLista(); // Carrega do objeto treinador para a interface
    }
    
    private Treinador criarNovoTreinador(String username) {
        try {
            // Solicitar nome completo
            String nome = JOptionPane.showInputDialog(this,
                "Bem-vindo!\nDigite seu nome completo:",
                "Cadastro Treinador - Nome",
                JOptionPane.QUESTION_MESSAGE);
            
            if (nome == null || nome.trim().isEmpty()) return null; // Cancelado
            
            // Solicitar idade
            String idadeStr = JOptionPane.showInputDialog(this,
                "Digite sua idade:",
                "Cadastro Treinador - Idade",
                JOptionPane.QUESTION_MESSAGE);
            
            if (idadeStr == null) return null; // Cancelado
            
            int idade = Integer.parseInt(idadeStr.trim());
            if (idade <= 0 || idade > 120) {
                throw new ValidacaoException("Idade deve estar entre 1 e 120 anos.");
            }
            
            return new Treinador(username, nome.trim(), idade);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, digite apenas números válidos.");
            return criarNovoTreinador(username); // Tentar novamente
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return criarNovoTreinador(username); // Tentar novamente
        }
    }
    
    private void initComponents() {
        listModel = new DefaultListModel<>();
        listaAlunos = new JList<>(listModel);
        listaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaAlunos.setFont(listaAlunos.getFont().deriveFont(14f));
        
        btnAdicionarAluno = new JButton("Adicionar Aluno");
        btnRemoverAluno = new JButton("Remover Aluno");
        btnAbrirTreino = new JButton("Abrir Treinos");
        btnSair = new JButton("Sair");
        
        // Configurar estilo dos botões
        Font fonteBotao = btnAdicionarAluno.getFont().deriveFont(12f);
        btnAdicionarAluno.setFont(fonteBotao);
        btnRemoverAluno.setFont(fonteBotao);
        btnAbrirTreino.setFont(fonteBotao.deriveFont(Font.BOLD));
        btnSair.setFont(fonteBotao);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior com título
        JLabel lblTitulo = new JLabel("Meus Alunos", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 16f));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Painel central com lista de alunos
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createTitledBorder("Lista de Alunos"));
        
        JScrollPane scrollPane = new JScrollPane(listaAlunos);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        painelCentral.add(scrollPane, BorderLayout.CENTER);
        
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnAdicionarAluno);
        painelBotoes.add(btnRemoverAluno);
        painelBotoes.add(btnAbrirTreino);
        painelBotoes.add(btnSair);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnAdicionarAluno.addActionListener(this::adicionarAluno);
        btnRemoverAluno.addActionListener(this::removerAluno);
        btnAbrirTreino.addActionListener(this::abrirTreinoAluno);
        btnSair.addActionListener(this::sair);
        
        // Duplo clique para abrir treinos
        listaAlunos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirTreinoAluno(null);
                }
            }
        });
    }
    
    // Carrega os alunos do objeto treinador para a interface
    private void carregarAlunosNaLista() {
        listModel.clear();
        for (Aluno aluno : treinador.getAlunos()) {
            listModel.addElement(aluno.getName()); // Mostra o nome do aluno na lista
        }
    }
    
    private void adicionarAluno(ActionEvent e) {
        String nomeAluno = JOptionPane.showInputDialog(this,
            "Digite o nome do aluno que deseja adicionar:",
            "Adicionar Aluno",
            JOptionPane.QUESTION_MESSAGE);
        
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return; // Cancelado ou vazio
        }
        
        nomeAluno = nomeAluno.trim();
        
        // Verificar se o aluno já está na lista
        if (treinadorTemAluno(nomeAluno)) {
            JOptionPane.showMessageDialog(this,
                "Este aluno já está na sua lista.",
                "Aluno Duplicado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar se o aluno existe no sistema
        if (!DataManager.existeArquivoAluno(nomeAluno)) {
            JOptionPane.showMessageDialog(this,
                "Digite um aluno já existente.",
                "Aluno Não Encontrado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Aluno existe e não está na lista, adicionar
        adicionarAlunoNaLista(nomeAluno);
    }
    
    // Método auxiliar para verificar se o treinador já tem o aluno pelo nome
    private boolean treinadorTemAluno(String nomeAluno) {
        for (Aluno aluno : treinador.getAlunos()) {
            if (aluno.getName().equals(nomeAluno)) {
                return true;
            }
        }
        return false;
    }
    
    private void adicionarAlunoNaLista(String nomeAluno) {
        try {
            // Carregar o aluno do sistema
            Aluno aluno = DataManager.carregarAluno(nomeAluno);
            if (aluno != null) {
                // Adiciona no objeto treinador
                treinador.addAluno(aluno);
                
                // Adiciona na interface
                listModel.addElement(aluno.getName());
                
                // Salva no arquivo
                salvarTreinador();
                
                JOptionPane.showMessageDialog(this,
                    "Aluno '" + nomeAluno + "' adicionado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar dados do aluno: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerAluno(ActionEvent e) {
        String nomeAlunoSelecionado = listaAlunos.getSelectedValue();
        
        if (nomeAlunoSelecionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecione um aluno para remover.",
                "Nenhum Aluno Selecionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja remover o aluno '" + nomeAlunoSelecionado + "' da sua lista?\n" +
            "(Isso não apagará os dados do aluno, apenas o removerá da sua lista)",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcao == JOptionPane.YES_OPTION) {
            // Encontrar o objeto Aluno correspondente
            Aluno alunoParaRemover = null;
            for (Aluno aluno : treinador.getAlunos()) {
                if (aluno.getName().equals(nomeAlunoSelecionado)) {
                    alunoParaRemover = aluno;
                    break;
                }
            }
            
            if (alunoParaRemover != null) {
                // Remove do objeto treinador
                treinador.removeAluno(alunoParaRemover);
                
                // Remove da interface
                listModel.removeElement(nomeAlunoSelecionado);
                
                // Salva no arquivo
                salvarTreinador();
                
                JOptionPane.showMessageDialog(this,
                    "Aluno removido da sua lista.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void abrirTreinoAluno(ActionEvent e) {
        String nomeAlunoSelecionado = listaAlunos.getSelectedValue();
        
        if (nomeAlunoSelecionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecione um aluno para ver seus treinos.",
                "Nenhum Aluno Selecionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Abrir a tela principal do aluno selecionado
        TelaPrincipalAluno telaAluno = new TelaPrincipalAluno(nomeAlunoSelecionado);
        telaAluno.setTitle("Treinos do Aluno " + nomeAlunoSelecionado + " (Editando como Treinador)");
        telaAluno.setVisible(true);
    }
    
    private void sair(ActionEvent e) {
        new TelaSelecaoUsuario().setVisible(true);
        this.dispose();
    }
    
    // Método simplificado que salva o objeto treinador completo
    private void salvarTreinador() {
        try {
            DataManager.salvarTreinador(treinador);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar dados: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
