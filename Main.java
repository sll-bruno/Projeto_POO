import View.TelaSelecaoUsuario;
import javax.swing.SwingUtilities;

//Classe Main é o entrypoint da aplicação. Ao rodar o item abaixo, inicia-se a execução do aplicativo
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaSelecaoUsuario().setVisible(true);
        });
    }
}