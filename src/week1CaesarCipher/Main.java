package week1CaesarCipher;

import static week1CaesarCipher.CaesarCipher.getTwoDecryptedMessage;

/**
 * Created by alex on 04.12.16.
 */
public class Main {
    public static void main(String[] args) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        /*int key = 15;

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
        System.out.println(decmsg);*/

       /* String str = "Laer. My necessaries are embark'd. Farewell. " +
                "And, sister, as the winds give benefit";
        System.out.println(str + "\n");

        String enc = getEncryptedMessage(abc, str, 22);
        System.out.println(enc);

        CaesarBreaker cb = new CaesarBreaker(abc);
        System.out.println(cb.decrypt(enc) + "\n");

        enc = getTwoEncryptedMessage(abc, str, 13, 25);
        System.out.println(enc);

        //CaesarBreaker cb = new CaesarBreaker(abc);
        System.out.println(cb.decryptTwoKeys(enc));*/

        String str = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        System.out.println(getTwoDecryptedMessage(abc, str, 14, 24));
        CaesarBreaker cb = new CaesarBreaker(abc);
        System.out.println(cb.decryptTwoKeys(str));


    }
}
