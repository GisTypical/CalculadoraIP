package eventos;
import interfaces.*;
import javax.swing.*;
import java.awt.event.*;

public class CambioModo implements ItemListener{
    
    private JFrame ventana;

    public CambioModo(JFrame ventana){
        this.ventana = ventana;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object modo = e.getItem();
            if (modo.equals("IPv4")){
                cambioIPv4();
            }
            if (modo.equals("IPv6")) {
                cambioIPv6();
            }
            if (modo.equals("Registros")) {
                cambioRegistros();
            }
        }
    }

    public void cambioIPv4() {
        InterfazIPv4 interfazIPv4 = new InterfazIPv4(ventana);
        this.ventana.setContentPane(interfazIPv4.getPanelGod());
        this.ventana.setSize(450, 300);
        this.ventana.validate();
        this.ventana.invalidate();
    }

    public void cambioIPv6(){
        InterfazIPv6 interfazIPv6 = new InterfazIPv6(ventana);
        this.ventana.setSize(450, 300);
        this.ventana.setContentPane(interfazIPv6.getPanel());
        this.ventana.validate();
        this.ventana.invalidate();
    }

    public void cambioRegistros(){
        InterfazRegistro registro = new InterfazRegistro(ventana);
        this.ventana.setSize(700, 300);
        this.ventana.setContentPane(registro.getPanel());
        this.ventana.validate();
        this.ventana.invalidate();
    }
}