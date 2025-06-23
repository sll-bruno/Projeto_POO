import View.TelaSelecaoUsuario;
import javax.swing.SwingUtilities;

public class Main {
    /**
     * Ponto de entrada da aplicação.
     * Utiliza SwingUtilities.invokeLater para garantir que a GUI seja criada
     * na thread de despacho de eventos (Event Dispatch Thread - EDT).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaSelecaoUsuario().setVisible(true);
        });
    }
}