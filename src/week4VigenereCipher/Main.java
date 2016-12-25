package week4VigenereCipher;

/**
 * Created by alex on 24.12.16.
 */
public class Main {
    public static void main(String[] args) {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();

        /*FileResource fr = new FileResource();
        char mostCommon = 'e';
        System.out.println(vb.tryKeyLength(fr.asString(), 4, mostCommon));*/


    }
}
