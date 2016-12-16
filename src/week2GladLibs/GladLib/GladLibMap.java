package week2GladLibs.GladLib;

import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Assignment 3: Maps Version of GladLibs
 * <p>
 * Start with your GladLibs program you completed earlier in this lesson. Make a copy of it and call it GladLibMap.java.
 * Now modify this program to use one HashMap that maps word types to ArrayList of possible words to select.
 * Your program should still work for the additional categories verbs and fruits and should not use duplicate words from
 * a category.
 * <p>
 * Specifically, you should make the following adjustments to this program:
 * <p>
 * [x]	Replace the ArrayLists for adjectiveList, nounList, colorList, countryList, nameList, animalList, timeList,
 * verbList, and fruitList with one HashMap myMap that maps a String representing a category to an ArrayList of
 * words in that category. Caution: Don’t replace the ArrayList representing the words that have already been used!
 * <p>
 * [x]	Create the new HashMap in the constructors.
 * <p>
 * [x]	Modify the method initializeFromSource to create an Array of categories and then iterate over this Array.
 * For each category, read in the words from the associated file, create an ArrayList of the words
 * (using the method readIt), and put the category and ArrayList into the HashMap.
 * <p>
 * [x]	Modify the method getSubstitute to replace all the if statements that use category labels with one call to
 * randomFrom that passes the appropriate ArrayList from myMap.
 * <p>
 * [x]	Run your program to make sure it works.
 * <p>
 * [x]	Write a new method named totalWordsInMap with no parameters. This method returns the total number of words in
 * all the ArrayLists in the HashMap. After printing the GladLib, call this method and print out the total number
 * of words that were possible to pick from.
 * <p>
 * [x]	Write a new method named totalWordsConsidered with no parameters. This method returns the total number of words
 * in the ArrayLists of the categories that were used for a particular GladLib. If only noun, color, and adjective
 * were the categories used in a GladLib, then only calculate the sum of all the words in those three ArrayLists.
 * Hint: You will need to keep track of the categories used in solving the GladLib, then compute this total.
 */

public class GladLibMap {
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWordsList;
    private Random myRandom;

    public GladLibMap() {
        this.myMap = new HashMap<>();
        initializeFromSource(dataSourceDirectory);
        this.usedWordsList = new ArrayList<>();
        myRandom = new Random();
    }

    public GladLibMap(String source) {
        initializeFromSource(source);
        this.usedWordsList = new ArrayList<>();
        myRandom = new Random();
    }

    private void initializeFromSource(String source) {
        String[] category = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        for (String s : category) {
            this.myMap.put(s, readIt(source + "/" + s + ".txt"));
        }
    }

    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if (label.equals("number")) {
            return "" + myRandom.nextInt(50) + 5;
        }
        return randomFrom(this.myMap.get(label));
    }

    private String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if (first == -1 || last == -1) {
            return w;
        }
        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String sub = getSubstitute(w.substring(first + 1, last));
        if (this.usedWordsList.contains(sub)) {
            sub = processWord(sub);
        }
        this.usedWordsList.add(sub);
        return prefix + sub + suffix;
    }

    private void printOut(String s, int lineWidth) {
        int charsWritten = 0;
        for (String w : s.split("\\s+")) {
            if (charsWritten + w.length() > lineWidth) {
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
        System.out.println("\nWords replaced: " + this.usedWordsList.size());
    }

    private String fromTemplate(String source) {
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String source) {
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        }
        return list;
    }

    public void makeStory() {
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        this.usedWordsList.clear();
    }

    /*
    * Write a new method named totalWordsInMap with no parameters. This method returns the total number of words in all
    * the ArrayLists in the HashMap. After printing the GladLib, call this method and print out the total number of
    * words that were possible to pick from.
    */
    public int totalWordsInMap() {
        /*int totalWords = 0;
		for (String s : this.myMap.keySet()) {
			totalWords += this.myMap.get(s).size();
		}
		return totalWords;*/
        int sum = 0;
        for (String category : myMap.keySet()) {
            ArrayList<String> words = myMap.get(category);
            sum += words.size();
        }
        return sum;
    }

    /*
    * Write a new method named totalWordsConsidered with no parameters. This method returns the total number of words
    * in the ArrayLists of the categories that were used for a particular GladLib. If only noun, color, and adjective
    * were the categories used in a GladLib, then only calculate the sum of all the words in those three ArrayLists.
    * Hint: You will need to keep track of the categories used in solving the GladLib, then compute this total.
    */
    public int totalWordsConsidered() {
        ArrayList<String> usedCategories = new ArrayList<>();
        for (String word : this.usedWordsList) {
            for (String category : this.myMap.keySet()) {
                if (!usedCategories.contains(this.myMap.get(category).contains(word))) {
                    usedCategories.add(word);
                }
            }
        }
        int totalWordsConsidered = 0;
        for (String category : this.myMap.keySet()) {
            totalWordsConsidered += this.myMap.get(category).size();
        }
        return totalWordsConsidered;
    }


}
