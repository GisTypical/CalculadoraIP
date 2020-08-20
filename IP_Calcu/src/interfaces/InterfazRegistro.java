package interfaces;

import eventos.*;
import main.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InterfazRegistro extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable registro;
    private JPanel datos; 
    private String[] columnas = { "IP", "Seguridad", "Clase", "APIPA", "Reservada", "EnvioInfo", "DirRed", "Gateway",
            "Broadcast", "Rango" };
    private DefaultTableModel modelo = new DefaultTableModel();
    private JComboBox<String> modo;
    private static final String[] MODOS = { "Registros", "IPv4", "IPv6" };

    public InterfazRegistro(JFrame ventana) {
        DB.getInstances();
        this.datos = new JPanel(new BorderLayout());
        this.setLayout(new BorderLayout());

        for (int i = 0; i < columnas.length; i++) {
            this.modelo.addColumn(columnas[i]);
        }
        this.registro = new JTable(modelo);
        this.registro.setFillsViewportHeight(true);

        DB.getInstances().setModel(modelo);
        DB.getInstances().dbStatement("select * from ipv4");

        this.modo = new JComboBox<>(MODOS);
        CambioModo cambioModo = new CambioModo(ventana);
        this.modo.addItemListener(cambioModo);

        this.datos.add(new JScrollPane(registro), BorderLayout.CENTER);
        this.add(modo, BorderLayout.SOUTH);
        this.add(this.datos, BorderLayout.CENTER);

    }

    public JPanel getPanel() {
        return this;
    }
}