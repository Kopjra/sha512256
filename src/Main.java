import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide input files");
            System.exit(1);
        }
        String inputFile = args[0];
        Security.addProvider(new BouncyCastleProvider());

        try (
                InputStream inputStream = new FileInputStream(inputFile);
        ) {
            int byteRead;
            byte[] byteBuffer = new byte[8192];

            MessageDigest md = MessageDigest.getInstance("SHA-512/256");

            while ((byteRead = inputStream.read(byteBuffer)) > 0) {
                md.update(byteBuffer, 0, byteRead);
            }

            String hex = new String(Hex.encode(md.digest()));
            System.out.println(hex);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(2);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(3);
        }
    }
}