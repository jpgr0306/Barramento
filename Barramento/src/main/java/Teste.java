import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Teste {
    
    public static BigInteger generatePrimeFromString(String input, int numBits) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger seed = new BigInteger(1, hashBytes);

        while (true) {
            seed = seed.add(BigInteger.ONE); // Incrementa a semente

            if (seed.bitLength() < numBits) {
                // Define o tamanho do número primo
                seed = seed.setBit(numBits);
            }

            if (!seed.testBit(0)) {
                // Garante que a semente seja ímpar
                seed = seed.add(BigInteger.ONE);
            }

            if (seed.isProbablePrime(64)) {
                break;
            }
        }

        return seed;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "Hello, World!";
        int numBits = 1024;

        BigInteger prime = generatePrimeFromString(input, numBits);

        System.out.println("Número primo gerado: " + prime);
    }
}