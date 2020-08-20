package interfaces;

import eventos.*;
import java.awt.*;
import javax.swing.*;

public class InterfazIPv6 extends JPanel{

    private JPanel panelDatos;
    private JPanel panelBot;
    private JTextField ip1;
    private JTextArea datos;
    private JButton go;
    private JComboBox<String> modo;
    private static final String[] MODOS = {"IPv6", "IPv4", "Registros"};

    private static final long serialVersionUID = 5360811976939753087L;

    public InterfazIPv6(JFrame ventana){
        //Instancias
        this.setLayout(new BorderLayout());
        this.panelBot = new JPanel();
        this.panelBot.setLayout(new BoxLayout(this.panelBot, BoxLayout.LINE_AXIS));
        this.panelDatos = new JPanel(new BorderLayout());
        this.datos = new JTextArea();
        this.ip1 = new JTextField(40);
        this.go = new JButton("GO");
        this.modo = new JComboBox<>(MODOS);
        CambioModo cambioModo = new CambioModo(ventana);
        this.modo.addItemListener(cambioModo);        
        
        EventoIPv6 eventoIPv6 = new EventoIPv6();
        this.go.addActionListener(eventoIPv6);
        eventoIPv6.setDatos(datos);
        eventoIPv6.setIp1(ip1);

        this.panelDatos.add(datos, BorderLayout.CENTER);
        
        this.panelBot.add(new JLabel("IPv6: "));
        this.panelBot.add(ip1);
        this.panelBot.add(go);
        
        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(new JSeparator(SwingConstants.VERTICAL));
        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(modo);

        this.panelBot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 12));

        this.add(panelDatos, BorderLayout.CENTER);
        this.add(panelBot, BorderLayout.SOUTH);
    }

    public JPanel getPanel(){
        return this;
    }
}