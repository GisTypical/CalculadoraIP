package main;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class DB {
    // Attributes
    private static DB db = new DB();
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String driverDB = "org.postgresql.Driver";
    private String dbName = "calcuip";
    private String urlDB = "jdbc:postgresql://localhost:5432/" + this.dbName;
    private String userDB = "postgres";
    private String passDB = "123";
    private DefaultTableModel modelo;

    private DB() {
        try {
            Class.forName(driverDB);
            this.conn = DriverManager.getConnection(urlDB, userDB, passDB);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DB getInstances() {
        return db;
    }

    public ResultSet dbStatement(String query) {
        try {
            this.stmt = this.conn.createStatement();
            this.rs = this.stmt.executeQuery(query);
            while (rs.next()) {
                modelo.addRow(new Object[] { rs.getString("ip"), rs.getString("seguridad"), rs.getString("clase"),
                        rs.getString("apipa"), rs.getString("reservada"), rs.getString("envioinfo"),
                        rs.getString("dirred"), rs.getString("gateway"), rs.getString("broadcast"),
                        rs.getString("rango") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.stmt.close();
                this.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void dbPrepareStatement(String query, Object[] obj) {
        try {
            this.pstmt = this.conn.prepareStatement(query);
            this.pstmt.setString(1, (String) obj[0]);
            this.pstmt.setString(2, (String) obj[1]);
            this.pstmt.setString(3, (String) obj[2]);
            this.pstmt.setString(4, (String) obj[3]);
            this.pstmt.setString(5, (String) obj[4]);
            this.pstmt.setString(6, (String) obj[5]);
            this.pstmt.setString(7, (String) obj[6]);
            this.pstmt.setString(8, (String) obj[7]);
            this.pstmt.setString(9, (String) obj[8]);
            this.pstmt.setString(10, (String) obj[9]);
            this.pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dbClose() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setModel(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}