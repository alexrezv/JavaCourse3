package week4VigenereCipher;

import edu.duke.FileResource;
import week4VigenereCipher.sourcesByDuke.CaesarCracker;
import week4VigenereCipher.sourcesByDuke.VigenereCipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by alex on 24.12.16.
 * <p>
 * Your first step in this mini-project is to write the three methods in the VigenereBreaker class. Specifically you
 * should do the following:
 */
public class VigenereBreaker {
    /*
    * Write the public method sliceString, which has three parameters—a String message, representing the encrypted
    * message, an integer whichSlice, indicating the index the slice should start from, and an integer totalSlices,
    * indicating the length of the key. This method returns a String consisting of every totalSlices-th character from
    * message, starting at the whichSlice-th character.
    */
    public String sliceString(String message, int whichSlice, int totalSlices) {
        String slice = "";
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice += message.charAt(i);
        }
        return slice;
    }

    /*
    * Write the public method tryKeyLength, which takes three parameters—a String encrypted that represents the
    * encrypted message, an integer klength that represents the key length, and a character mostCommon that indicates
    * the most common character in the language of the message. This method should make use of the CaesarCracker class,
    * as well as the sliceString method, to find the shift for each index in the key. You should fill in the key (which
    * is an array of integers) and return it. Test this method on the text file athens_keyflute.txt, which is a scene
    * from A Midsummer Night’s Dream encrypted with the key “flute”, and make sure you get the key {5, 11, 20, 19, 4}.
    */
    public ArrayList<Integer> tryKeyLength(String encrypted, int klength, char mostCommon) {
        ArrayList<Integer> key = new ArrayList<>();
        //int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String slice = this.sliceString(encrypted, i, klength);
            CaesarCracker caesarCracker = new CaesarCracker();
            key.add(caesarCracker.getKey(slice));
        }
        return key;
    }

    /*
    * Write the public method breakVigenere with no parameters. This void method should put everything together, so that
    * you can create a new VigenereBreaker in BlueJ, call this method on it, and crack the cipher used on a message.
    * This method should perform 6 tasks (in this order):
    */

    /**
     * Does some thing in old style.
     *
     * @Deprecated
     * @deprecated use {@link #breakVigenere()} instead.
     */
    @Deprecated
    public void breakVigenereOld() {
        //Create a new FileResource using its default constructor.
        FileResource fr = new FileResource();
        //Use the asString method to read the entire contents of the file into a String.
        String message = fr.asString();
        //Use the tryKeyLength method, which you just wrote, to find the key for the message you read in. For now, you
        // should just pass ‘e’ for mostCommon.
        ArrayList<Integer> key = this.tryKeyLength(message, 4, 'e');
        //You should create a new VigenereCipher, passing in the key that tryKeyLength found for you.
        VigenereCipher vc = new VigenereCipher(key);
        //You should use the VigenereCipher’s decrypt method to decrypt the encrypted message.
        //Finally, you should print out the decrypted message!
        System.out.println(vc.decrypt(message));
    }

    /**
     * Assignment: English Language, Unknown Key Length
     * Now that you have broken Vigenère ciphers with a known key length, it is time to expand your program’s
     * functionality to break Vigenère ciphers of unknown key length. You will do this by adding three methods to your
     * VigenereBreaker class, and then modifying your breakVigenere method.
     */

    /*
    * In the VigenereBreaker class, write the public method readDictionary, which has one parameter—a FileResource fr.
    * This method should first make a new HashSet of Strings, then read each line in fr (which should contain exactly
    * one word per line), convert that line to lowercase, and put that line into the HashSet that you created.
    * The method should then return the HashSet representing the words in a dictionary. All the dictionary files,
    * including the English dictionary file, are included in the starter program you download. They are inside the
    * folder called ‘dictionaries’.
    */
    public HashSet readDictionary(FileResource dictionaryFile) {
        HashSet<String> dictionary = new HashSet<>();
        for (String word : dictionaryFile.words()) {
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }

    /*
    * In the VigenereBreaker class, write the public method countWords, which has two parameters—a String message,
    * and a HashSet of Strings dictionary. This method should split the message into words (use .split(“\\W+”), which
    * returns a String array), iterate over those words, and see how many of them are “real words”—that is, how many
    * appear in the dictionary. Recall that the words in dictionary are lowercase. This method should return the integer
    * count of how many valid words it found.
    */
    public int countWords(String message, HashSet<String> dictionary) {
        int counter = 0;
        for (String word : message.split("\\W+")) {
            if (dictionary.contains(word.toLowerCase())) {
                counter++;
            }
        }
        return counter;
    }

    /*
    * In the VigenereBreaker class, write the public method breakForLanguage, which has two parameters—a String
    * encrypted, and a HashSet of Strings dictionary. This method should try all key lengths from 1 to 100 (use your
    * tryKeyLength method to try one particular key length) to obtain the best decryption for each key length in that
    * range. For each key length, your method should decrypt the message (using VigenereCipher’s decrypt method as
    * before), and count how many of the “words” in it are real words in English, based on the dictionary passed in (use
    * the countWords method you just wrote). This method should figure out which decryption gives the largest count of
    * real words, and return that String decryption. Note that there is nothing special about 100; we will just give you
    * messages with key lengths in the range 1–100. If you did not have this information, you could iterate all the way
    * to encrypted.length(). Your program would just take a bit longer to run.
    */
    public String breakForLanguage(String encrypted, HashSet<String> dictionary, char mostCommon) {
        ArrayList<Integer> keyToWordsCount = new ArrayList<>();
        keyToWordsCount.add(0);
        for (int i = 1; i < 150; i++) {
            //System.out.println("Breaking for key " + i);
            ArrayList<Integer> key = this.tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            keyToWordsCount.add(this.countWords(decrypted, dictionary));
        }
        int maxRecognizedWordsKey = keyToWordsCount.indexOf(keyToWordsCount.stream().max(Integer::compareTo).orElse(0));
        System.out.println("Found key " + maxRecognizedWordsKey + " for valid words "
                + keyToWordsCount.stream().max(Integer::compareTo).orElse(0));
        ArrayList<Integer> key = this.tryKeyLength(encrypted, maxRecognizedWordsKey, 'e');
        System.out.println("The key is: " + key);
        VigenereCipher vc = new VigenereCipher(key);
        return vc.decrypt(encrypted);
    }

    /*
    * Finally, you need to modify your breakVigenere method to make use of your new code. The steps below describe what
    * your breakVigenere method should do. They are similar to the steps from the previous portion of this project but
    * have been updated: new items are in italics, [and old items that you should no longer do are in square brackets.]
    */
    /*public void breakVigenere () {
        //Create a new FileResource using its default constructor.
        FileResource fr = new FileResource();
        //Use that FileResource’s asString method to read the entire contents of the file into a String.
        String message = fr.asString();
        //You should make a new FileResource to read from the English dictionary file that we have provided.
        FileResource dictionaryFile = new FileResource("./dictionaries/English");
        //You should use your readDictionary method to read the contents of that file into a HashSet of Strings.
        HashSet<String> dictionary = readDictionary(dictionaryFile);
        //You should use the breakForLanguage method that you just wrote to decrypt the encrypted message.
        //Finally, you should print out the decrypted message!
        System.out.println(this.breakForLanguage(message, dictionary, 'e'));
    }*/

    /**
     * Assignment: Multiple Languages
     * Finally, it is time to expand your program so that it can crack messages in other languages. To accomplish this,
     * you need to write two new methods and modify two methods you have already written.
     * Specifically, you should do the following:
     */
    /*
    * In the VigenereBreaker class, write the public method mostCommonCharIn, which has one parameter—a HashSet of
    * Strings dictionary. This method should find out which character, of the letters in the English alphabet,
    * appears most often in the words in dictionary. It should return this most commonly occurring character. Remember
    * that you can iterate over a HashSet of Strings with a for-each style for loop.
    */
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<String, Integer> charToCount = new HashMap<>();
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                if (!charToCount.containsKey(word.substring(i, i + 1))) {
                    charToCount.put(word.substring(i, i + 1), 1);
                } else {
                    charToCount.put(word.substring(i, i + 1), charToCount.get(word.substring(i, i + 1)) + 1);
                }
            }
        }
        int maxCharOccur = charToCount.entrySet().stream().mapToInt(a -> a.getValue()).max().orElse(0);
        char mostCommonChar = ' ';
        for (String chr : charToCount.keySet()) {
            if (charToCount.get(chr).equals(maxCharOccur)) {
                mostCommonChar = chr.charAt(chr.indexOf(chr));
                break;
            }
        }
        return mostCommonChar;
    }

    /*
    * In the VigenereBreaker class, write the public method breakForAllLangs, which has two parameters—a String
    * encrypted, and a HashMap, called languages, mapping a String representing the name of a language to a HashSet of
    * Strings containing the words in that language. Try breaking the encryption for each language, and see which gives
    * the best results! Remember that you can iterate over the languages.keySet() to get the name of each language, and
    * then you can use .get() to look up the corresponding dictionary for that language. You will want to use the
    * breakForLanguage and countWords methods that you already wrote to do most of the work (it is slightly inefficient
    * to re-count the words here, but it is simpler, and the inefficiency is not significant). You will want to print
    * out the decrypted message as well as the language that you identified for the message.
    */
    public String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        ArrayList<Integer> keyToWordsCount = new ArrayList<>();
        ArrayList<String> decrypted = new ArrayList<>();
        for (String language : languages.keySet()) {
            char mostCommonChar = this.mostCommonCharIn(languages.get(language));
            System.out.println("Breaking for " + language);
            String dcmsg = this.breakForLanguage(encrypted, languages.get(language), mostCommonChar);
            decrypted.add(dcmsg);
            keyToWordsCount.add(this.countWords(dcmsg, languages.get(language)));
        }
        int maxRecognizedWords = keyToWordsCount.indexOf(keyToWordsCount.stream().max(Integer::compareTo).orElse(0));
        return decrypted.get(maxRecognizedWords);
    }

    /* !!NOT IMPLEMENTED!!
    * Modify the method breakForLanguage to make use of your mostCommonCharIn method to find the most common character
    * in the language, and pass that to tryKeyLength instead of ‘e’.
    */

    /*
    * Modify your breakVigenere method to read many dictionaries instead of just one. In particular, you should make a
    * HashMap mapping Strings to a HashSet of Strings that will map each language name to the set of words in its
    * dictionary. Then, you will want to read (using your readDictionary method) each of the dictionaries that we have
    * provided (Danish, Dutch, English, French, German, Italian, Portuguese, and Spanish) and store the words in the
    * HashMap you made. Reading all the dictionaries may take a little while, so you might add some print statements to
    * reassure you that your program is making progress. Once you have made that change, you will want to call
    * breakForAllLangs, passing in the message (the code to read in the message is unchanged from before), and the
    * HashMap you just created.
    */
    public void breakVigenere() {
        String langs[] = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};
        HashMap<String, HashSet<String>> langToWords = new HashMap<>();
        for (String lang : langs) {
            FileResource dictionaryFile = new FileResource("./dictionaries/" + lang);
            langToWords.put(lang, this.readDictionary(dictionaryFile));
        }

        //Create a new FileResource using its default constructor.
        FileResource fr = new FileResource();
        //Use that FileResource’s asString method to read the entire contents of the file into a String.
        String message = fr.asString();

        System.out.println(this.breakForAllLangs(message, langToWords));
    }

    /**
     * Here are some optional ideas to extend your program even further:
     *
     * Test edge cases. 'Edge cases' refer to special situations where a program might break down. For example, consider
     * text files or languages where the most common letter is unreliable or nearly tied with another letter. For
     * example, the original message encrypted in the text file aida_keyverdi.txt is in Italian, which has letter
     * frequencies E	11.49%, A	10.85%, I	10.18% (http://www.sttmedia.com/characterfrequency-italian ). You could
     * also experiment with determining the maximum allowable key length.
     *
     * Use a different set of data. Try different length texts and different languages. This can also help you identify
     * edge cases.
     *
     * Explore different statistics. This program assumes one character is the most frequent. Are there other ways to
     * determine the key to a cipher? One possibility to address certain edge cases, such as breaking decryption of
     * Italian-language texts could be to find multiple possible keys of each length and try all of them (tryKeyLength
     * might return a 2D array—if you find every key length n where there are two possibilities for the most frequent
     * letter, there would be 2^n possible keys).
     *
     * Adapt your program to a new problem. This project focused on the Vigenère cipher. What other ciphers could you
     * write algorithms for? How is cryptography different from steganography?
     *
     * Whatever you do to extend your program and solve new problems, share it with us and your peers in the forums!
     * Happy programming!
     */
}
