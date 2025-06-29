package View;

import Model.UserType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaSelecaoUsuario extends JFrame {
    
    public TelaSelecaoUsuario() {
        setTitle("Seleção de Perfil");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(245, 245, 245));
            }
        };
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();

        // Placeholder para logo
        JLabel lblLogo = new JLabel("Bem Vindo!");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 32));
        lblLogo.setForeground(new Color(46, 134, 222));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200,200,200)));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(lblLogo, gbc);

        JButton btnAluno = new JButton("Sou Aluno");
        btnAluno.setFont(new Font("Arial", Font.BOLD, 18));
        btnAluno.setBackground(new Color(46, 204, 113));
        btnAluno.setForeground(Color.WHITE);
        btnAluno.setFocusPainted(false);
        btnAluno.setPreferredSize(new Dimension(180, 50));
        btnAluno.addActionListener((ActionEvent e) -> {
            new TelaLogin(UserType.ALUNO).setVisible(true);
            this.dispose();
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(btnAluno, gbc);

        JButton btnTreinador = new JButton("Sou Treinador");
        btnTreinador.setFont(new Font("Arial", Font.BOLD, 18));
        btnTreinador.setBackground(new Color(52, 152, 219));
        btnTreinador.setForeground(Color.WHITE);
        btnTreinador.setFocusPainted(false);
        btnTreinador.setPreferredSize(new Dimension(180, 50));
        btnTreinador.addActionListener((ActionEvent e) -> {
            new TelaLogin(UserType.TREINADOR).setVisible(true);
            this.dispose();
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        painelPrincipal.add(btnTreinador, gbc);

        add(painelPrincipal);
    }
}