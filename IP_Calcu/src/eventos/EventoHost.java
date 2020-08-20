package eventos;
import java.awt.event.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EventoHost implements ActionListener {

    private JTextField nHost;
    private long nCIRD;
    private JTextArea datos;

    public EventoHost(JTextField nHost) {
        this.nHost = nHost;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int numeroHost = Integer.parseInt(nHost.getText());
        nCIRD = (long) (32 - (Math.log(numeroHost + (double) 2) / Math.log(2)));
        comparaciones();
    }
    public void comparaciones(){
        boolean claseC = nCIRD >= 24 && nCIRD < 31;
        boolean claseB = nCIRD >= 16 && nCIRD < 24;
        boolean claseA = nCIRD >= 8 && nCIRD < 16;
        if (claseC) {
            datos.setText("Se requiere la IP: 192.168.19.0/" + nCIRD);
        }
        if (claseB) {
            datos.setText("Se requiere la IP: 172.29.0.0/" + nCIRD);
        }
        if (claseA) {
            datos.setText("Se requiere la IP: 10.0.0.0/" + nCIRD);
        }
        if (!claseC && !claseB && !claseA){
            datos.setText("Indeterminado");   
        }
    }

    public void getTextArea(JTextArea datos){
        this.datos = datos;
    }
}
