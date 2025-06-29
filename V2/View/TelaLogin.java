package View;

import Model.UserType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
    
    private UserType tipoUsuario;
    private JTextField campoNome;
    private JButton btnEntrar;
    private JButton btnVoltar;
    
    public TelaLogin(UserType tipoUsuario) {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.tipoUsuario = tipoUsuario;
        
        initComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initComponents() {
        campoNome = new JTextField(20);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 16));
        campoNome.setPreferredSize(new Dimension(200, 35));
        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEntrar.setBackground(new Color(46, 134, 222));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setPreferredSize(new Dimension(120, 40));
        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBackground(new Color(220, 53, 69));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setPreferredSize(new Dimension(120, 40));
        // Tornar o botão "Entrar" como padrão
        getRootPane().setDefaultButton(btnEntrar);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(245, 245, 245)); // cor de fundo clara
            }
        };
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        // Label de instrução
        JLabel lblInstrucao = new JLabel("Nome de usuário (letras e números): ");
        lblInstrucao.setFont(lblInstrucao.getFont().deriveFont(Font.BOLD, 16f));
        lblInstrucao.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(lblInstrucao, gbc);
        // Campo de nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        painelPrincipal.add(campoNome, gbc);
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnEntrar);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        painelPrincipal.add(painelBotoes, gbc);
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        btnEntrar.addActionListener(this::entrar);
        btnVoltar.addActionListener(this::voltar);
        
        // Permitir login com Enter
        campoNome.addActionListener(this::entrar);
    }
    
    private void entrar(ActionEvent e) {
        String nome = campoNome.getText().trim();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, digite seu nome de usuário para continuar.", 
                "Nome Obrigatório", 
                JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }
        
        if (nome.length() < 2) {
            JOptionPane.showMessageDialog(this, 
                "O nome deve ter pelo menos 2 caracteres.", 
                "Nome Inválido", 
                JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }
        
        if (!nome.matches("[a-zA-Z0-9]+")) {
            JOptionPane.showMessageDialog(this,
                "O nome de usuário deve conter apenas letras e números.",
                "Nome Inválido",
                JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }
        
        if (this.tipoUsuario == UserType.ALUNO) {
            // Abre a tela principal do aluno
            TelaPrincipalAluno telaPrincipal = new TelaPrincipalAluno(nome);
            telaPrincipal.setVisible(true);
            this.dispose();
        }

        if (this.tipoUsuario == UserType.TREINADOR) {
            // Abre a tela principal do treinador
            TelaPrincipalTreinador telaPrincipal = new TelaPrincipalTreinador(nome);
            telaPrincipal.setVisible(true);
            this.dispose();
        }
    }
    
    private void voltar(ActionEvent e) {
        new TelaSelecaoUsuario().setVisible(true);
        this.dispose();
    }
}