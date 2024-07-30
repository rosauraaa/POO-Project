package juego;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Main {
    final static int ancho = 700;
    final static int alto = 500;

    public Main() {
        JFrame ventanita = new JFrame();
        ventanita.setSize(ancho,alto);
        ventanita.setTitle("Ping-Pong!");
        ventanita.setResizable(false);
        JPanel panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagenFondo = new ImageIcon("C:\\Users\\Dell\\Downloads\\kitty.jpeg").getImage();
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), (ImageObserver) this);
            }
        };


        ventanita.add(panelConFondo);
        ventanita.setVisible(true);
        ventanita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*para que cuando se cierre la
        ventanita el código no siga running ensegundo plano*/
        ventanita.setLocationRelativeTo(null); //para que siempre salga en el centro de la pantalla
        PingPong pingpong = new PingPong();
        ventanita.add(pingpong);

    }

    public static void main(String[] args) {
        Main launch = new Main();
    }
}
