package src;
import java.security.MessageDigest;

/**
 * Utility class containing static methods for cryptographic functions.
 */
public class Util {

    /**
     * Applies the SHA-256 hash function to the input string and returns the resulting hash as a hexadecimal string.
     * SHA-256 (Secure Hash Algorithm 256-bit) is a cryptographic hash function that produces a 256-bit hash value.
     *
     * @param input The input string to be hashed.
     * @return The resulting hash as a hexadecimal string.
     */
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Encodes this String into a sequence of bytes using UTF-8 charset
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            // This will contain hash as hexidecimal
            StringBuffer hexString = new StringBuffer();

            // Process each byte in the hash array
            for (byte b : hash) {
                // convert byte to a hexadecimal string
                String hex = String.format("%02x", b); // Ensures two-character hexadecimal string
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
