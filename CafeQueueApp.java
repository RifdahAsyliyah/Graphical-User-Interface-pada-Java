import javax.swing.SwingUtilities;

public class CafeQueueApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainFrame application = new MainFrame();

            application.setVisible(true);
        });
    }
}
