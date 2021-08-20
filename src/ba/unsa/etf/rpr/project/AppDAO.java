package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class AppDAO {
    private Connection conn;
    private PreparedStatement ps;
    private static AppDAO instance=null;



    private AppDAO()  {
        //baza ce se napraviti u home folderu korisnika
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "\\.todoApp\\todoDatabase.db";

        //String url="jdbc:sqlite:todoDatabase.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        /*try {
            ps = conn.prepareStatement("SELECT *FROM users");
        } catch (SQLException e) {
            generateDatabase();
            try {
                ps = conn.prepareStatement("SELECT *FROM users");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }*/


    }

   /* private void generateDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("todoDatabase.db.sql"));
            String sqlStatement = "";
            while (input.hasNext()) {
                sqlStatement += input.nextLine();
                if ( sqlStatement.charAt( sqlStatement.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlStatement);
                        sqlStatement = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static AppDAO getInstance()  {
        if (instance == null) instance = new AppDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
