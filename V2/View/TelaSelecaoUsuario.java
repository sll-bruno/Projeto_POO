package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaSelecaoUsuario extends JFrame {
    
    public TelaSelecaoUsuario() {
        setTitle("Seleção de Perfil");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(2, 1, 10, 10));
        
        JButton btnAluno = new JButton("Sou Aluno");
        btnAluno.addActionListener((ActionEvent e) -> {
            new TelaPrincipalAluno().setVisible(true);
            this.dispose();
        });
        
        JButton btnTreinador = new JButton("Sou Treinador");
        btnTreinador.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidade para Treinador ainda não implementada.", "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
        });
        
        add(btnAluno);
        add(btnTreinador);
    }
}