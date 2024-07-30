package juego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PingPong extends JPanel implements KeyListener, ActionListener {
    private boolean jugando = false;
    private boolean gameover = false;
private Timer timer;
private int tiempo = 100;
private int posxj1 = Main.ancho - 28;
private int posyj1 = Main.alto/2-75;
private int posxj2 = 1;
private int posyj2 = Main.alto/2-75;
private int posxpelotita = 200;
private int posypelotita = 300;
private int direccionx = 2; /*entre más grande sea este numero que se le va asumar
a la posición de la pelotita, más rápido va a ir, x eso debe ser pequeño*/
private int direcciony = 3;
private boolean arribaj1 = false;
private boolean abajoj1 = false;
private boolean arribaj2 = false;
private boolean abajoj2 = false;
private int scorej1 = 0;
private int scorej2 = 0;


    public PingPong(){
        setLayout(null);
        timer = new Timer(tiempo,this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
        Thread jugador1Thread = new Thread(new jugador1Runnable());
        Thread jugador2Thread = new Thread(new jugador2Runnable());
        jugador1Thread.start();
        jugador2Thread.start();

    }
    public void paint(Graphics g){
        Image fondo = new ImageIcon("C:\\Users\\Dell\\Downloads\\kitty.jpeg").getImage();
        g.drawImage(fondo, 0, 0, Main.ancho, Main.alto, this);
        //dibujando Jugador1
        g.setColor(Color.white);
        g.fillRect(posxj1,posyj1,15,100);
        //dibujando Jugador2
        g.setColor(Color.white);
        g.fillRect(posxj2,posyj2,15,100);
        //dibujando la pelotita
        g.setColor(Color.yellow);
        g.fillOval(posxpelotita,posypelotita,25,25);

        moverpelotita();
        chocar();

        //SCORE
        g.setColor(Color.WHITE);
        g.setFont(new Font("KidDos Font",Font.PLAIN,25));
        g.drawString("☆ Score: " + scorej1,450,30);
        g.drawString("☆ Score: " + scorej2,150,30);

        //bienvenida
        if(!jugando && !gameover){
            g.setColor(Color.BLACK);
            g.setFont(new Font("KidDos Font",Font.PLAIN,25));
            g.drawString("₊˚⊹ ^. .^☆⋆｡ presiona la espaciadora para jugar!!",70, 380 );
        }
        if(gameover){
            if(scorej1==scorej2){
                g.setColor(Color.PINK);
                g.setFont(new Font("KidDos Font",Font.PLAIN,25));
                g.drawString("★EMPATE!★",70, 380 );
            }
            else if(scorej1>scorej2){
                g.setFont(new Font("KidDos Font",Font.PLAIN,25));
                g.drawString("★GANADOR: JUGADOR 1★",70, 380 );
            }
            else if(scorej1<scorej2){
                g.setFont(new Font("KidDos Font",Font.PLAIN,25));
                g.drawString("★GANADOR: JUGADOR 2★",70, 380 );
            }

        }


    }

    public void moverpelotita(){
    if(jugando){
        //que la pelotita se mueva
        posxpelotita = posxpelotita + direccionx; //aquí sumando a la posición inicial el movimiento
        posypelotita = posypelotita + direcciony;

    }
    if(posxpelotita>700){
        //cuando la pelotita sale de los límites de la pantalla, el juego se para!!
        gameover();
    }
    if(posxpelotita<0){  //lo mismo, si se sale de los límites
        gameover();
    }
    if(posypelotita<0){
    direcciony = -direcciony; /*si rebota arriba, cambia la direccion hacia abajo*/
    }
    if(posypelotita>500-50){ /*-50 para que la pelotita no rebote más abajo del margen de la pantalla en y*/
        direcciony = -direcciony;
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

public void reinicio(){
   jugando = true;
   gameover = false;
   posxj1 = Main.ancho - 28;
   posyj1 = Main.alto/2-75;
   posxj2 = 1;
   posyj2 = Main.alto/2-75;
   posxpelotita = 200;
   posypelotita = 300;
   arribaj1 = false;
   abajoj1 = false;
   arribaj2 = false;
   abajoj2 = false;
   scorej1 = 0;
   scorej2 = 0;
   timer.start();
}
public void gameover(){
        gameover = true;
        jugando = false;
        timer.stop(); //que finalice el juego
}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //este para ver si se está presionando la tecla
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //para jugador 1
        if (key == KeyEvent.VK_UP) {
            arribaj1 = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            abajoj1 = true;
        }
        //para jugador 2
        if(key == KeyEvent.VK_Q){
            arribaj2 = true;
        }
        if(key == KeyEvent.VK_A){
            abajoj2 = true;
        }
        //iniciar el juego
        if(key == KeyEvent.VK_SPACE){
            jugando = true;
        }
         if(gameover){
            reinicio();
        }
    }
//viendo si se dejó de presionar la tecla
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            arribaj1 = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            abajoj1 = false;
        }
        if(key == KeyEvent.VK_Q){
            arribaj2 = false;
        }
        if(key == KeyEvent.VK_A){
            abajoj2 = false;
        }

    }


    //HILOS
    //hilo para el jugador 1
    private class jugador1Runnable implements Runnable{
        public void run(){
                while (true) { /*ciclo que se repite infinitamente para mantener el movimiento, que no se dtenga*/
                    if (arribaj1) {
                        posyj1 -= 5;
                    } else if (abajoj1) {
                        posyj1 += 5;
                    }

                    try {
                        Thread.sleep(10); /*lo mando a dormir para que no se ejecute demasiado rápido*/
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    repaint(); /*simplemente para que repinte el elemento cuando haya
                    cambiado su posicion y se vea gráficamente*/
                }
            }
    }
    //hilo para el jugador 2
    private class jugador2Runnable implements Runnable{
        public void run(){
            while (true) {
                if (arribaj2) {
                    posyj2 -= 5;
                } else if (abajoj2) {
                    posyj2 += 5;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repaint();
            }
        }
    }

    public void chocar(){
        if(new Rectangle(posxpelotita,posypelotita,25,25).intersects(new Rectangle(posxj1,posyj1,15,100))){
/*dibujando un rectángulo justo en la posición de la pelotita y otro rectángulo
* justo en la posición del jugador, y cuando choquen (colisionen) la dirección
* de la pelotita cambie*/
            direccionx = -direccionx;
            //y cuando choca, el puntaje del jugador aumenta!!
            scorej1++;
        }
        if(new Rectangle(posxpelotita,posypelotita,25,25).intersects(new Rectangle(posxj2,posyj2,15,100))){
            direccionx = -direccionx;
            scorej2++;
        }
    }
}
