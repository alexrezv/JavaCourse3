package week1CaesarCipher;

/**
 * Created by alex on 03.12.16.
 */
public class CaesarCipher {
    public static void main(String[] args) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println(abc);
        System.out.println(getRearrangedABC(abc, 19) + "\n");

        String message = "HELLO MY DEAR FRIEND! HOW ARE YOU?" +
                "\nHeloo me dea frend! Kak Dela?";
        System.out.println(message);
        System.out.println(getEncryptedMessage(abc, message, 19));
        System.out.println(getDecryptedMessage(abc, getEncryptedMessage(abc, message, 19), 19));

    }

    private static String getRearrangedABC(String abc, int key) {
        key = key % abc.length();
        return abc.substring(key) + abc.substring(0, key);

    }

    private static String getEncryptedMessage(String abc, String message, int key) {
        return doCaesarCipher(abc, message, key);
    }

    private static String getDecryptedMessage(String abc, String message, int key) {
        return doCaesarCipher(abc, message, Math.abs(26 - key));
    }

    private static String doCaesarCipher(String abc, String message, int key) {
        String rearrangedABC = getRearrangedABC(abc, key);
        String encmesg = "";
        for (int i = 0; i < message.length(); i++) {

            if (abc.contains(Character.toUpperCase(message.charAt(i)) + "")) {
                if (Character.isLowerCase(message.charAt(i))) {
                    encmesg += Character.toLowerCase(
                            rearrangedABC.charAt(abc.indexOf(Character.toUpperCase(message.charAt(i))))
                    );
                } else {
                    encmesg += rearrangedABC.charAt(abc.indexOf(Character.toUpperCase(message.charAt(i))));
                }
            } else {
                encmesg += message.charAt(i);
            }
        }
        return encmesg;
    }
}
