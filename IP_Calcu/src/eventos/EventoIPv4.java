package eventos;

import main.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EventoIPv4 implements ActionListener {
    private JTextField textIP1;
    private JTextField textIP2;
    private JTextField textIP3;
    private JTextField textIP4;
    private JTextField mascDeRed;
    private JTextArea datos;
    private IP ipv4 = new IP();

    public EventoIPv4(JTextField ip1, JTextField ip2, JTextField ip3, JTextField ip4, JTextField mascDeRed) {
        this.textIP1 = ip1;
        this.textIP2 = ip2;
        this.textIP3 = ip3;
        this.textIP4 = ip4;
        this.mascDeRed = mascDeRed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.datos.setText("");
        // Hacer el DecParaBin desde aqui
        this.oct1 = textIP1.getText();
        this.oct2 = textIP2.getText();
        this.oct3 = textIP3.getText();
        this.oct4 = textIP4.getText();
        this.ipv4.setIpnum(oct1 + "." + oct2 + "." + oct3 + "." + oct4);
        this.datos.append("IP: " + ipv4.getIpnum() + "\n");

        // Metodo que contiene las comparaciones booleanas mas grandes
        try {
            comparaciones();
            // IF PRINCIPAL
            if (esPublico) {
                this.ipv4.setSeguridad("Privada");
                privadas();
            } else {
                this.ipv4.setSeguridad("Publica");
                publicas();
            }
            operacionesConMascara();
        } catch (Exception exc) {
            this.datos.setText("Octeto o Mascara Invalida");
        }

        Object[] obj = { this.ipv4.getIpnum(), this.ipv4.getSeguridad(), this.ipv4.getClase(), this.ipv4.getApipa(),
                this.ipv4.getReservada(), this.ipv4.getEnvioInfo(), this.ipv4.getDirRed(), this.ipv4.getGateway(),
                this.ipv4.getBroadcast(), this.ipv4.getRango() };

        try {
            DB.getInstances().dbPrepareStatement("insert into ipv4 values (?,?,?,?,?,?,?,?,?,?)", obj);
        } catch (Exception exception) {
            this.datos.setText("Ya esta IP existe");
        }

    }

    // ---------------Metodos--------------

    // Privadas

    public void privadas() {
        this.datos.append("Seguridad" + ipv4.getSeguridad());
        if (decParaBin(oct1) == decParaBin("10")) {
            this.ipv4.setClase("A");
            this.datos.append("\nClase: " + this.ipv4.getClase());
        } else {
            if (decParaBin(oct1) == decParaBin("172")) {
                this.ipv4.setClase("B");
                this.datos.append("\nClase: " + this.ipv4.getClase());
            } else {
                this.ipv4.setClase("C");
                this.datos.append("\nClase: " + this.ipv4.getClase());
            }
        }
        this.ipv4.setApipa("NO");
        this.ipv4.setReservada("SI (Redes locales)");
        this.datos.append("\nAPIPA: " + this.ipv4.getApipa() + "\nReservada: " + this.ipv4.getReservada());
        this.ipv4.setEnvioInfo(INDETER);
        this.datos.append(ENVIOINDETER);
    }

    // Publicas

    public void publicas() {
        this.datos.append("Seguridad: " + ipv4.getSeguridad() + "\n");
        if (decParaBin(oct1) >= decParaBin("0") && decParaBin(oct1) <= decParaBin("127")) {
            this.ipv4.setClase("A");
            publicasA();
        }
        if (decParaBin(oct1) >= decParaBin("128") && decParaBin(oct1) <= decParaBin("191")) {
            this.ipv4.setClase("B");
            publicasB();
        }
        if (decParaBin(oct1) >= decParaBin("192") && decParaBin(oct1) <= decParaBin("223")) {
            this.ipv4.setClase("C");
            publicasC();
        }
        if (decParaBin(oct1) >= decParaBin("224") && decParaBin(oct1) <= decParaBin("239")) {
            this.ipv4.setClase("D");
            publicasD();
        }
        if (decParaBin(oct1) >= decParaBin("240") && decParaBin(oct1) <= decParaBin("255")) {
            this.ipv4.setClase("E");
            publicasE();
        }
    }

    public void publicasA() {
        this.ipv4.setApipa("NO");
        this.datos.append("Clase:" + this.ipv4.getClase() + "\nAPIPA: " + this.ipv4.getApipa());
        if (decParaBin(oct1) == decParaBin("0")) {
            this.ipv4.setReservada("SI (IP de Origen)");
            this.datos.append("\nReservado: " + this.ipv4.getReservada());
        } else {
            if (decParaBin(oct1) == decParaBin("100") && decParaBin(oct2) >= decParaBin("64")
                    && decParaBin(oct2) <= decParaBin("127")) {
                this.ipv4.setReservada("SI (Comunicaciones proveedor-cliente)");
                this.datos.append("\nReservado: " + this.ipv4.getReservada());
            } else {
                if (decParaBin(oct1) == decParaBin("127")) {
                    this.ipv4.setReservada("SI (Loopback");
                    this.datos.append("\nReservado: " + this.ipv4.getReservada());
                } else {
                    this.ipv4.setReservada("NO");
                    this.datos.append("\nReservado: " + this.ipv4.getReservada());
                }
            }
        }
        this.ipv4.setEnvioInfo(INDETER);
        this.datos.append(ENVIOINDETER);
    }

    public void publicasB() {
        this.datos.append("Clase: B\n");
        if (decParaBin(oct1) == decParaBin("169") && decParaBin(oct2) == decParaBin("254")) {
            this.ipv4.setApipa("SI");
            this.ipv4.setReservada("SI (IP de Enlace Local)");
            this.ipv4.setEnvioInfo("Unicast");
            this.datos.append("APIPA: SI\nReservado: SI (IP de Enlace Local)\nEnvio de Informacion: Unicast\n");
        } else {
            this.ipv4.setApipa("NO");
            this.ipv4.setReservada("NO");
            this.ipv4.setEnvioInfo(INDETER);
            this.datos.append("APIPA: NO\nReservado: NO");
            this.datos.append(ENVIOINDETER);
        }
    }

    public void publicasC() {
        this.ipv4.setApipa("NO");
        this.datos.append("Clase: C\nAPIPA: NO");
        if (cIETF) {
            this.ipv4.setReservada("SI (Protocolo IETF)");
            this.datos.append("\nReservado: SI (Protocolo IETF)");
        }
        if (cTest) {
            this.ipv4.setReservada("SI (TEST-NET-1)");
            this.datos.append("\nReservado: SI (TEST-NET-1)");
        }
        if (cRelay) {
            this.ipv4.setReservada("SI (Relay)");
            this.datos.append("\nReservado: SI (Relay)");
        }
        if (cReferencia) {
            this.ipv4.setReservada("SI (Pruebas de referencia)");
            this.datos.append("\nReservado: SI (Pruebas de referencia)");
        }
        if (cTest2) {
            this.ipv4.setReservada("SI (TEST-NET-2");
            this.datos.append("\nReservado: SI (TEST-NET-2)");
        }
        if (cTest3) {
            this.ipv4.setReservada("SI (TEST-NET-3)");
            this.datos.append("\nReservado: SI (TEST-NET-3)");
        }
        if (!cIETF && !cTest && !cRelay && !cReferencia && !cTest2 && !cTest3) {
            this.ipv4.setReservada("NO");
            this.datos.append("\nReservado: NO");
        }
        this.ipv4.setEnvioInfo(INDETER);
        this.datos.append(ENVIOINDETER);
    }

    public void publicasD() {
        this.datos.append("Clase: D\nAPIPA: NO");
        this.ipv4.setApipa("NO");
        this.ipv4.setReservada("SI (Multicast)");
        this.ipv4.setEnvioInfo("Multicast");
        this.datos.append("\nReservado: SI (Multicast)\nEnvio de Informacion: Multicast\n");
    }

    public void publicasE() {
        this.datos.append("Clase: E\nAPIPA: NO");
        this.ipv4.setApipa("NO");
        if (decParaBin(oct1) >= decParaBin("240") && (decParaBin(oct1) < decParaBin("255"))
                || decParaBin(oct4) <= decParaBin("254")) {
            this.datos.append("\nReservado: SI (Usos Futuros)");
            this.ipv4.setReservada("SI (Usos Futuros)");
        } else {
            this.datos.append("\nReservado: SI (Multidifusion)");
            this.ipv4.setReservada("SI (Multidifusion)");
        }
        this.ipv4.setEnvioInfo(INDETER);
        this.datos.append(ENVIOINDETER);
    }

    public void comparaciones() {
        esPublico = (decParaBin(oct1) == decParaBin("10")) || (decParaBin(oct1) == decParaBin("172")
                && (decParaBin(oct2) >= decParaBin("16") && decParaBin(oct2) <= decParaBin("31"))
                || (decParaBin(oct1) == decParaBin("192") && (decParaBin(oct2) == decParaBin("168"))));
        cIETF = decParaBin(oct1) == decParaBin("192") && decParaBin(oct2) == decParaBin("0")
                && decParaBin(oct3) == decParaBin("0");
        cTest = decParaBin(oct1) == decParaBin("192") && decParaBin(oct2) == decParaBin("0")
                && decParaBin(oct3) == decParaBin("2");
        cRelay = decParaBin(oct1) == decParaBin("192") && decParaBin(oct2) == decParaBin("88")
                && decParaBin(oct3) == decParaBin("99");
        cReferencia = decParaBin(oct1) == decParaBin("198") && decParaBin(oct2) >= decParaBin("18")
                && decParaBin(oct2) <= decParaBin("19");
        cTest2 = decParaBin(oct1) == decParaBin("198") && decParaBin(oct2) == decParaBin("51")
                && decParaBin(oct3) == decParaBin("100");
        cTest3 = decParaBin(oct1) == decParaBin("203") && decParaBin(oct2) == decParaBin("0")
                && decParaBin(oct3) == decParaBin("113");
    }

    public void operacionesConMascara() {
        String[] masc = mascara(mascDeRed.getText()).split("-");

        int and1 = Integer.parseInt(oct1) & binParaDec(masc[0]);
        int and2 = Integer.parseInt(oct2) & binParaDec(masc[1]);
        int and3 = Integer.parseInt(oct3) & binParaDec(masc[2]);
        int and4 = Integer.parseInt(oct4) & binParaDec(masc[3]);

        String dirRed = and1 + "." + and2 + "." + and3 + "." + and4;

        this.datos.append("Direccion de Red: " + dirRed + "\n");
        this.ipv4.setDirRed(dirRed);

        String dirGateway = and1 + "." + and2 + "." + and3 + "." + (and4 + 1);

        this.datos.append("Direccion de Gateway: " + dirGateway + "\n");
        this.ipv4.setGateway(dirGateway);

        int or1 = Integer.parseInt(oct1) | binParaDec(negar(masc[0]));
        int or2 = Integer.parseInt(oct2) | binParaDec(negar(masc[1]));
        int or3 = Integer.parseInt(oct3) | binParaDec(negar(masc[2]));
        int or4 = Integer.parseInt(oct4) | binParaDec(negar(masc[3]));

        this.datos.append("Direccion de Broadcast: " + or1 + "." + or2 + "." + or3 + "." + or4 + "\n");

        this.ipv4.setBroadcast(or1 + "." + or2 + "." + or3 + "." + or4);

        this.datos.append("Rango: " + dirGateway + " -> " + or1 + "." + or2 + "." + or3 + "." + (or4 - 1) + "\n");
        this.ipv4.setRango(dirGateway + " -> " + or1 + "." + or2 + "." + or3 + "." + (or4 - 1));
    }

    public int decParaBin(String decimalString) {
        int bin = Integer.parseInt(decimalString);
        return Integer.parseInt(Integer.toBinaryString(bin));
    }

    public int binParaDec(String binString) {
        String[] partes = binString.split("");
        int dec = 0;
        int n = partes.length - 1;
        for (int i = 0; i < partes.length; i++) {
            dec += (int) (Integer.parseInt(partes[i]) * (Math.pow(2, n)));
            n--;
        }
        return dec;
    }

    public String mascara(String notacionCIDR) {
        ArrayList<String> aList = new ArrayList<>();
        int notacInt = Integer.parseInt(notacionCIDR);
        StringBuilder mascara = new StringBuilder();
        if (notacInt >= 0 && notacInt <= 32) {
            do {
                if (notacInt > 0) {
                    aList.add("1");
                    notacInt--;
                } else {
                    aList.add("0");
                }
            } while (aList.size() < 32);
            aList.add(8, "-");
            aList.add(17, "-");
            aList.add(26, "-");
            for (int i = 0; i < aList.size(); i++) {
                mascara.append(aList.get(i));
            }
            return mascara.toString();
        } else {
            return null;
        }
    }

    public String negar(String bin) {
        StringBuilder negado = new StringBuilder();
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) == '0') {
                negado.append('1');
            } else {
                negado.append('0');
            }
        }
        return negado.toString();
    }

    public void setDatos(JTextArea datos) {
        this.datos = datos;
    }

    private boolean esPublico;
    private boolean cIETF;
    private boolean cTest;
    private boolean cRelay;
    private boolean cReferencia;
    private boolean cTest2;
    private boolean cTest3;
    private String oct1;
    private String oct2;
    private String oct3;
    private String oct4;
    private static final String ENVIOINDETER = "\nEnvio de Informacion: Indeterminado\n";
    private static final String INDETER = "Indeterminado";

}