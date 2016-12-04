package week1CaesarCipher;

import static week1CaesarCipher.CaesarCipher.*;

/**
 * Created by alex on 04.12.16.
 */
public class Main {
    public static void main(String[] args) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int key = 15;

        String message = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        System.out.println(message);

        String encmsg = getEncryptedMessage(abc, message, key);
        System.out.println(encmsg);
        String decmsg = getDecryptedMessage(abc, encmsg, key);
        System.out.println(decmsg);

        //message = "First Legion";
        System.out.println("\n" + message);
        encmsg = getTwoEncryptedMessage(abc, message, 8, 21);
        System.out.println(encmsg);
        decmsg = getTwoDecryptedMessage(abc, encmsg, 8, 21);
        System.out.println(decmsg);


    }
}
