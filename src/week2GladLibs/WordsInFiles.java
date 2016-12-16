package week2GladLibs;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alex on 11.12.16.
 * <p>
 * Assignment 2: Words in Files
 * <p>
 * Write a program to determine which words occur in the greatest number of files, and for each word, which files they
 * occur in.
 * <p>
 * For example, consider you are given the four files: brief1.txt, brief2.txt, brief3.txt, and brief4.txt.
 * <p>
 * brief1.txt is: cats are funny and cute
 * brief2.txt is: dogs are silly
 * brief3.txt is: love animals cats and dogs
 * brief4.txt is: love birds and cats
 * <p>
 * The greatest number of files a word appears in is three, and there are two such words: “cats” and “and”.
 * “cats” appears in the files: brief1.txt, brief3.txt, brief4.txt
 * “and” appears in the files: brief1.txt, brief3.txt, brief4.txt
 * <p>
 * To solve this problem, you will create a map of words to the names of files they are in. That is, you will map a
 * String to an ArrayList of Strings. Then you can determine which ArrayList value is the largest
 * (has the most filenames) and its key is thus, a word that is in the most number of files.
 */
public class WordsInFiles {
    //private HashMap<String, HashMap<String, Integer> > myMap;

    //Create a private variable to store a HashMap that maps a word to an ArrayList of filenames.
    private HashMap<String, ArrayList<String>> myMap;

    //Write a constructor to initialize the HashMap variable.
    public WordsInFiles() {
        this.myMap = new HashMap<>();
    }

    /*
    * Write a private void method named addWordsFromFile that has one parameter f of type File. This method should add
    * all the words from f into the map. If a word is not in the map, then you must create a new ArrayList of type
    * String with this word, and have the word map to this ArrayList. If a word is already in the map, then add the
    * current filename to its ArrayList, unless the filename is already in the ArrayList. You can use the File method
    * getName to get the filename of a file.
    */
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {
            if (!this.myMap.containsKey(word)) {
                ArrayList<String> filename = new ArrayList<>();
                filename.add(f.getName());
                this.myMap.put(word, filename);
            } else if (!this.myMap.get(word).contains(f.getName())) {
                ArrayList<String> filename = this.myMap.get(word);
                filename.add(f.getName());
                this.myMap.put(word, filename);
            }
        }
    }

    /*
    * Write a void method named buildWordFileMap that has no parameters. This method first clears the map, and then uses
    * a DirectoryResource to select a group of files. For each file, it puts all of its words into the map by calling
    * the method addWordsFromFile. The remaining methods to write all assume that the HashMap has been built.
    */
    private void buildWordFileMap() {
        this.myMap.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            this.addWordsFromFile(f);
        }
    }

    /*
    * Write the method maxNumber that has no parameters. This method returns the maximum number of files any word
    * appears in, considering all words from a group of files. In the example above, there are four files considered.
    * No word appears in all four files. Two words appear in three of the files, so maxNumber on those four files would
    * return 3. This method assumes that the HashMap has already been constructed.
    */
    private int maxNumber() {
        int maxNUmOfFiles = 0;
        for (String s : this.myMap.keySet()) {
            maxNUmOfFiles = (maxNUmOfFiles < this.myMap.get(s).size()) ? this.myMap.get(s).size() : maxNUmOfFiles;
        }
        return maxNUmOfFiles;
    }

    /*
    * Write the method wordsInNumFiles that has one integer parameter called number. This method returns an ArrayList of
    * words that appear in exactly number files. In the example above, the call wordsInNumFiles(3) would return an
    * ArrayList with the words “cats” and “and”, and the call wordsInNumFiles(2) would return an ArrayList with the
    * words “love”, “are”, and “dogs”, all the words that appear in exactly two files.
    */
    private ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> words = new ArrayList<>();
        for (String word : this.myMap.keySet()) {
            if (number == this.myMap.get(word).size()) {
                words.add(word);
            }
        }
        return words;
    }

    /*
    * Write the void method printFilesIn that has one String parameter named word. This method prints the names of the
    * files this word appears in, one filename per line. For example, in the example above, the call
    * printFilesIn(“cats”) would print the three filenames: brief1.txt, brief3.txt, and brief4.txt, each on a separate
    * line.
    */
    private void printFilesIn(String word) {
        //this.myMap.get(word).stream().peek(a -> System.out.println(a + " hi") ).close();
        System.out.println(this.myMap.get(word));
    }

    /*
    * Write the void method tester that has no parameters. This method should call buildWordFileMap to select a group of
    * files and build a HashMap of words, with each word mapped to an ArrayList of the filenames this word appears in,
    * determine the maximum number of files any word is in, considering all words, and determine all the words that are
    * in the maximum number of files and for each such word, print the filenames of the files it is in.
    * (optional) If the map is not too big, then you might want to print out the complete map, all the keys, and for
    * each key its ArrayList. This might be helpful to make sure the map was built correctly.
    */
    public void tester() {
        //call buildWordFileMap
        this.buildWordFileMap();

        System.out.println("total words: " + this.myMap.size());
        System.out.println("maxNumber: " + this.maxNumber());
        System.out.println("words in 1 files " + this.wordsInNumFiles(1).size());
        System.out.println("words in 2 files " + this.wordsInNumFiles(2).size());
        System.out.println("words in 3 files " + this.wordsInNumFiles(3).size());
        System.out.println("words in 4 files " + this.wordsInNumFiles(4).size());
        System.out.println("words in 7 files " + this.wordsInNumFiles(7).size());

        this.printFilesIn("tree");

        //System.out.println(this.myMap.toString());
    }


}
