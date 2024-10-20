package za.ac.cput.Config;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class SecretKeyGenerator {


    public static String generateSecretKey() {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();


            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Error generating secret key for JWT", e);
        }
    }

    public static void main(String[] args) {

        String secretKey = generateSecretKey();
        System.out.println("Generated JWT Secret Key: " + secretKey);
    }
}


