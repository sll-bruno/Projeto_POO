package View;

import Model.UserType;
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
            new TelaLogin(UserType.ALUNO).setVisible(true);
            this.dispose();
        });
        
        JButton btnTreinador = new JButton("Sou Treinador");
        btnTreinador.addActionListener((ActionEvent e) -> {
            new TelaLogin(UserType.TREINADOR).setVisible(true);
            this.dispose();
        });
        
        add(btnAluno);
        add(btnTreinador);
    }
}