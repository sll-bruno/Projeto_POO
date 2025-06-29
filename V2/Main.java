import View.TelaSelecaoUsuario;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaSelecaoUsuario().setVisible(true);
        });
    }
}