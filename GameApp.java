import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class GameApp {
    public static void main(String[] args) throws IOException {

        JFrame display = new JFrame();
        Game game = new Game();

        ImageIcon icon = new ImageIcon("./files/skeleton.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(105, 115, java.awt.Image.SCALE_SMOOTH);
        ImageIcon myIcon = new ImageIcon(newimg);

        display.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        display.setSize(Game.WIDTH, Game.HEIGHT);
        display.add(game, BorderLayout.CENTER);
        display.pack();

        display.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int option = JOptionPane.showConfirmDialog(null, game.message(), "Thanks for playing!",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, myIcon);
                if (option == JOptionPane.YES_OPTION) {
                    display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        display.setVisible(true);
        display.setResizable(false);
        display.setLocationRelativeTo(null);
    }
}
