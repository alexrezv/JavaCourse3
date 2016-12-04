package week1CaesarCipher;

import java.util.Arrays;

import static week1CaesarCipher.CaesarCipher.getDecryptedMessage;
import static week1CaesarCipher.CaesarCipher.getTwoDecryptedMessage;

/**
 * Created by alex on 03.12.16.
 * <p>
 * Complete the decryption method shown in the lesson by creating a CaesarBreaker class with the methods countLetters,
 * maxIndex, and decrypt. Recall that the decrypt method creates a CaesarCipher object in order to use the encrypt
 * method you wrote for the last lesson. Make sure that your CaesarCipher class is in the same folder as CaesarBreaker!
 * You may want to use the following code as part of your decrypt method.
 * CaesarCipher cc = new CaesarCipher();
 * String message = cc.encrypt(encrypted, 26 - key);
 */

public class CaesarBreaker {
    private String abc = "";

    public CaesarBreaker(String abc) {
        this.abc = abc;
    }

    /*
    * Write a testDecrypt method in the CaesarBreaker class that prints the decrypted message, and make sure it works
    * for encrypted messages that were encrypted with one key.
    */
    private static void testDecrypt() {
    }

    private static String getHalfOfString(String message, int start) {
        String hs = "";
        for (int i = 0; i < message.length(); i++) {
            if (i % 2 == start) {
                hs += message.charAt(i);
            }
        }
        return hs;
    }

    private int[] countLetters(String s, int[] letterCounts) {
        for (int i = 0; i < s.length(); i++) {
            int k = this.abc.indexOf(Character.toUpperCase(s.charAt(i)));
            if (k >= 0) {
                ++letterCounts[k];
            }
        }
        return letterCounts;
    }

    private int getMaxIndex(int[] letterCounts) {
        int a = Arrays.stream(letterCounts).max().orElse(-1);
        //System.out.print(a+"\n");
        for (int i = 0; i < letterCounts.length; i++) {
            if (a == letterCounts[i]) {
                return i;
            }
        }

        return -1;
    }

    /*
    * Write the method halfOfString in the CaesarBreaker class that has two parameters, a String parameter named message
    * and an int parameter named start. This method should return a new String that is every other character from
    * message starting with the start position. For example, the call halfOfString(“Qbkm Zgis”, 0) returns the String
    * “Qk gs” and the call halfOfString(“Qbkm Zgis”, 1) returns the String “bmZi”. Be sure to test this method with a
    * small example.
    */

    public String decrypt(String encoded) {
        //System.out.println("key is " + this.getKey(encoded));
        return getDecryptedMessage(this.abc, encoded, this.getKey(encoded));
    }

    /*
    * Write the method getKey in the CaesarBreaker class that has one parameter, a String s. This method should call
    * countLetters to get an array of the letter frequencies in String s and then use maxIndex to calculate the index of
    * the largest letter frequency, which is the location of the encrypted letter ‘e’, which leads to the key, which is
    * returned.
    */

    public int getKey(String s) {
        //call countLetters to get an array of the letter frequencies in String s
        int[] letterCounts = new int[26];
        letterCounts = this.countLetters(s, letterCounts);
        /*for (int i = 0; i < letterCounts.length; i++) {
            System.out.println(i + "\t" + this.abc.charAt(i) + "\t" + letterCounts[i]);
        }*/
        //then use maxIndex to calculate the index of the largest letter frequency
        //System.out.println(getMaxIndex(letterCounts) + " - " + this.abc.indexOf("E"));
        int key = Math.abs(this.getMaxIndex(letterCounts) - this.abc.indexOf("E"));
        if (this.getMaxIndex(letterCounts) < 4) {
            key = 26 + this.getMaxIndex(letterCounts) - this.abc.indexOf("E");
        }
        return key;
    }

    /*
    * Write the method decryptTwoKeys in the CaesarBreaker class that has one parameter, a String parameter named
    * encrypted that represents a String that was encrypted with the two key algorithm discussed in the previous lesson.
    * This method attempts to determine the two keys used to encrypt the message, prints the two keys, and then returns
    * the decrypted String with those two keys. More specifically, this method should:
    *
    * - Calculate a String of every other character starting with the first character of the encrypted String by calling
    * halfOfString.
    *
    * - Calculate a String of every other character starting with the second character of the encrypted String.
    *
    * - Then calculate the key used to encrypt each half String.
    *
    * - You should print the two keys found.
    *
    * - Calculate and return the decrypted String using the encryptTwoKeys method from your CaesarCipher class, again
    * making sure it is in the same folder as your CaesarBreaker class.
    */

    public String decryptTwoKeys(String encrypted) {
        String hlf1 = getHalfOfString(encrypted, 0);
        String hlf2 = getHalfOfString(encrypted, 1);
        int key1 = this.getKey(hlf1);
        int key2 = this.getKey(hlf2);
        System.out.println("key1 is " + key1 + "\tkey2 is " + key2);
        return getTwoDecryptedMessage(this.abc, encrypted, key1, key2);
    }


}
