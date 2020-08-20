package interfaces;
import eventos.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class InterfazIPv4 extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelDatos;
    private JPanel panelBot;
    private JTextField ip1;
    private JTextField ip2;
    private JTextField ip3;
    private JTextField ip4;
    private JTextField mascDeRed;
    private JTextField nHost;
    private JTextArea datos;
    private JComboBox<String> modo;
    private static final String[] MODOS = { "IPv4", "IPv6", "Registros" };

    public InterfazIPv4(JFrame ventana) {

        // Instancias Calculos 1
        this.setLayout(new BorderLayout());
        this.panelBot = new JPanel();
        this.panelBot.setLayout(new BoxLayout(this.panelBot, BoxLayout.LINE_AXIS));
        this.panelDatos = new JPanel(new BorderLayout());
        this.datos = new JTextArea();
        this.ip1 = new JTextField(3);
        this.ip2 = new JTextField(3);
        this.ip3 = new JTextField(3);
        this.ip4 = new JTextField(3);
        this.mascDeRed = new JTextField(3);
        EventoIPv4 eventoIPv4 = new EventoIPv4(ip1, ip2, ip3, ip4, mascDeRed);
        eventoIPv4.setDatos(datos);

        // Expresion Lambda, para simplificar el llamado a clases anonimas
        this.ip1.addActionListener((ActionEvent e) -> ip2.requestFocusInWindow());

        this.ip2.addActionListener((ActionEvent e) -> ip3.requestFocusInWindow());

        this.ip3.addActionListener((ActionEvent e) -> ip4.requestFocusInWindow());

        this.ip4.addActionListener((ActionEvent e) -> mascDeRed.requestFocusInWindow());

        this.mascDeRed.addActionListener(eventoIPv4);

        this.datos.setEditable(false);

        this.panelBot.add(new JLabel("IPv4: "));
        this.panelBot.add(ip1);
        this.panelBot.add(ip2);
        this.panelBot.add(ip3);
        this.panelBot.add(ip4);
        this.panelBot.add(new JLabel(" / "));
        this.panelBot.add(mascDeRed);

        this.panelDatos.add(datos, BorderLayout.CENTER);

        // Instancias Calculos 2
        this.nHost = new JTextField(8);
        EventoHost eventoHost = new EventoHost(nHost);
        this.nHost.addActionListener(eventoHost);
        eventoHost.getTextArea(datos);

        this.modo = new JComboBox<>(MODOS);
        CambioModo cambioModo = new CambioModo(ventana);
        this.modo.addItemListener(cambioModo);

        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(new JSeparator(SwingConstants.VERTICAL));
        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(new JLabel("# de Host: "));
        this.panelBot.add(nHost);
        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(new JSeparator(SwingConstants.VERTICAL));
        this.panelBot.add(Box.createHorizontalStrut(5));
        this.panelBot.add(modo);

        this.panelBot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(panelDatos, BorderLayout.CENTER);
        this.add(panelBot, BorderLayout.SOUTH);
    }

    // ------------METODOS--------------

    public JPanel getPanelGod() {
        return this;
    }
}
