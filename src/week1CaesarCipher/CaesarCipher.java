package week1CaesarCipher;

/**
 * Created by alex on 03.12.16.
 */
public class CaesarCipher {

    private static String getRearrangedABC(String abc, int key) {
        key = key % abc.length();
        return abc.substring(key) + abc.substring(0, key);

    }

    public static String getEncryptedMessage(String abc, String message, int key) {
        return doCaesarCipher(abc, message, key);
    }

    public static String getDecryptedMessage(String abc, String message, int key) {
        return doCaesarCipher(abc, message, Math.abs(26 - key));
    }

    private static String doCaesarCipher(String abc, String message, int key) {
        String rearrangedABC = getRearrangedABC(abc, key);
        String encmesg = "";
        for (int i = 0; i < message.length(); i++) {
            encmesg = swapSymbol(abc, rearrangedABC, message, encmesg, i);
        }
        return encmesg;
    }

    public static String getTwoEncryptedMessage(String abc, String message, int key1, int key2) {
        return doTwoCaesarCipher(abc, message, key1, key2);
    }

    public static String getTwoDecryptedMessage(String abc, String message, int key1, int key2) {
        return doTwoCaesarCipher(abc, message, Math.abs(26 - key1), Math.abs(26 - key2));
    }

    private static String doTwoCaesarCipher(String abc, String message, int key1, int key2) {
        String rearrangedABC1 = getRearrangedABC(abc, key1);
        String rearrangedABC2 = getRearrangedABC(abc, key2);
        String encmesg = "";
        for (int i = 0; i < message.length(); i++) {
            if (i % 2 == 0) {
                encmesg = swapSymbol(abc, rearrangedABC1, message, encmesg, i);
            } else {
                encmesg = swapSymbol(abc, rearrangedABC2, message, encmesg, i);
            }
        }
        return encmesg;
    }

    private static String swapSymbol(String abc, String rearrABC, String message, String encmesg, int i) {
        if (abc.contains(Character.toUpperCase(message.charAt(i)) + "")) {
            if (Character.isLowerCase(message.charAt(i))) {
                encmesg += Character.toLowerCase(
                        rearrABC.charAt(abc.indexOf(Character.toUpperCase(message.charAt(i))))
                );
            } else {
                encmesg += rearrABC.charAt(abc.indexOf(Character.toUpperCase(message.charAt(i))));
            }
        } else {
            encmesg += message.charAt(i);
        }
        return encmesg;
    }
}
