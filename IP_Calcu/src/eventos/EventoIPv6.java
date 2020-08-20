package eventos;

import java.awt.event.*;
import javax.swing.*;

public class EventoIPv6 implements ActionListener {

    private JTextField ip;
    private JTextArea datos;

    @Override
    public void actionPerformed(ActionEvent e) {
        String ipHex;
        try {
            ipHex = hexToDec(notacion(ip.getText()));
            datos.setText(ipHex);
            tipos(ipHex);
        } catch (Exception exception) {
            datos.setText("En la IP se encuentra un hexteto erroneo");
        }
    }

    private String notacion(String ip) {
        String ceros = "0000:";
        if (verificarPuntos(ip)) {
            return "Notacion Invalida";
        }
        if (ip.equals("::")) {
            return ceros.repeat(7) + "0000";
        }
        if (ip.contains("::")) {
            String[] partes = ip.split("::");
            int nCeros = 0;
            try {
                String[] parteIzq = partes[0].split(":");
                String[] parteDer = partes[1].split(":");
                // Numero de ceros que faltan
                if (!partes[0].equals("")) {
                    nCeros = 32 - ((parteIzq.length * 4) + (parteDer.length * 4));
                    nCeros /= 4;
                    return partes[0] + ":" + ceros.repeat(nCeros) + partes[1];
                }
                nCeros = 32 - (((parteIzq.length - 1) * 4) + (parteDer.length * 4));
                nCeros /= 4;
                return ceros.repeat(nCeros) + partes[1];
            } catch (Exception e) {
                String[] parteIzq = partes[0].split(":");
                nCeros = 32 - (parteIzq.length * 4);
                nCeros /= 4;
                return partes[0] + ":" + ceros.repeat(nCeros - 1) + "0000";
            }
        }
        return ip;
    }

    private boolean verificarPuntos(String ip) {
        int contDobles = ip.length() - ip.replace("::", "").length();
        int contTriples = ip.length() - ip.replace(":::", "").length();
        return contDobles > 2 || contTriples > 3;
    }

    private String hexToDec(String ipHex) {
        StringBuilder union = new StringBuilder();
        String[] partes = ipHex.split(":");
        for (String hexteto : partes) {
            int decimal = Integer.parseInt(hexteto, 16);
            if (decimal >= 0 && decimal <= 65535) {
                union.append(Integer.toHexString(decimal) + ":");
            }
        }
        return union.substring(0, union.length() - 1);
    }

    private void tipos(String ipHex) {
        String[] hexPartes = ipHex.split(":");
        int[] hexInt = new int[hexPartes.length];
        for (int i = 0; i < hexPartes.length; i++) {
            hexInt[i] = Integer.parseInt(hexToDec(hexPartes[i]), 16);
        }
        boolean unspecified = hexInt[0] == 0 && hexInt[1] == 0 && hexInt[2] == 0 && hexInt[3] == 0 && hexInt[4] == 0
                && hexInt[5] == 0 && hexInt[6] == 0 && hexInt[7] == 0;
        boolean loopback = hexInt[0] == 0 && hexInt[1] == 0 && hexInt[2] == 0 && hexInt[3] == 0 && hexInt[4] == 0
                && hexInt[5] == 0 && hexInt[6] == 0 && hexInt[7] == 1;
        if (hexInt[0] >= 8192 && hexInt[0] <= 16383) {
            datos.append("\nTipo: Unicast -> (Global Unicast) / Anycast\n");
        }
        if (hexInt[0] >= 65152 && hexInt[0] <= 65215) {
            datos.append("\nTipo: Unicast -> (Link-Local)");
        }
        if (hexInt[0] >= 64512 && hexInt[0] <= 65023) {
            datos.append("\nTipo: Unicast -> (Unique Local)");
        }
        if (hexInt[0] >= 65280 && hexInt[0] <= 65295) {
            datos.append("\nTipo: Multicast -> (Well-Known)");
        }
        if (hexInt[0] >= 65296 && hexInt[0] <= 65311) {
            datos.append("\nTipo: Multicast -> (Transient)");
        }
        if (unspecified) {
            datos.append("\nTipo: Unicast -> (Unspecified)");
        }
        if (loopback) {
            datos.append("\nTipo: Unicast -> (Loopback)");
        }
    }

    public void setIp1(JTextField ip1) {
        this.ip = ip1;
    }

    public void setDatos(JTextArea datos) {
        this.datos = datos;
    }

}