import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;
import java.util.Properties;

public class TestConnection {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("database.properties")) {
            props.load(in);
            String URL  = props.getProperty("db.url");
            String USER = props.getProperty("db.user");
            String PASS = props.getProperty("db.password");
            
            System.out.println("URL: " + URL);
            System.out.println("USER: " + USER);
            System.out.println("PASS: " + PASS);
            
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
