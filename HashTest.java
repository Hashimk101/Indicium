public class HashTest {
    public static void main(String[] args) {
        String hash = com.indicium.services.SessionManager.hashSHA256("ADMIN123");
        System.out.println("Hash for ADMIN123: " + hash);
    }
}
