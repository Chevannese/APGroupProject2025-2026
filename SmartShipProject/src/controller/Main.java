package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static final String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String username;
    private String password;
    private Connection conn;

    /**
     * Prepares the things for the connection.
     * @param hostname
     * @param database
     * @param port
     * @param username
     * @param password
     */
    public Main(String hostname, String database, String port, String username, String password) {
        this.username = username;
        this.password = password;

        this.url = String.format("jdbc:mysql://%s:%s/%s", hostname, port, database);
    }

    public Connection connect() throws ClassNotFoundException, SQLException {

        Class.forName(Main.driver);
        conn = DriverManager.getConnection(url, username, password);

        return conn;
    }

    public static void main(String[] args) {
        String hostname = "zabwas.h.filess.io";
        String database = "SmartShip_tubeablein";
        String port = "61002";
        String username = "SmartShip_tubeablein";
        String password = "6b5e0a8b874847dbb9f33d20b1789a7b0b0e6c7c";

        Main mysql = new Main(hostname, database, port, username, password);
        try {
            Connection conn = mysql.connect();

            System.out.println(String.format("Conected: %b", !conn.isClosed()));
            if (conn.isClosed())
                System.out.println("Terminating as the connection is closed.");
                System.exit(1);

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT 1+1");
            if (resultSet.next()) System.out.println(String.format("Result of query: %s", resultSet.getInt("1+1")));

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error de MySQL");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Ha ocurrido un error ajeno a MySQL");
            e.printStackTrace();
        }
    }
}