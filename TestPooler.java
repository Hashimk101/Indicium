import java.sql.Connection;
import java.sql.DriverManager;

public class TestPooler {
    public static void main(String[] args) {
        String URL  = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require";
        String USER = "postgres.girsmimhwhetusdxbtbp";
        String PASS = "enW7MosIWlPWYzT7";
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection to port 6543 successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        URL  = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection to port 5432 successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
