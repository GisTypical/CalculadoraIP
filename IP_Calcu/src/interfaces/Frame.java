package interfaces;

import javax.swing.*;
import java.awt.*;
public class Frame {
    
    private JFrame ventana;

    public Frame(){
        this.ventana = new JFrame("Calculadora IP v.1.0");

        InterfazIPv4 interfazIPv4 = new InterfazIPv4(ventana);
        this.ventana.add(interfazIPv4);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.ventana.setLocation((int) screen.getWidth() / 3, (int) screen.getHeight() / 4);
        this.ventana.setSize(450, 300);
        this.ventana.setVisible(true);
        this.ventana.setResizable(false);
        this.ventana.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}