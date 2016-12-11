package week2GladLibs;

import edu.duke.FileResource;

import java.util.ArrayList;

/**
 * Created by alex on 10.12.16.
 * <p>
 * Assignment 1: Most Frequent Word
 * You will write a program to determine the word that occurs the most often in a file. If more than one word occurs as
 * the most often, then return the first such word found. You should make all words lowercase before counting them.
 * Thus, “This” and “this” will both be counted as the lowercase version of “this”. You should not consider punctuation,
 * so “end” and “end,” will be considered different words. Use the WordFrequencies program in the lesson as a starting
 * point.
 */

public class WordFrequencies {
    /*
    * Create two private variables. One is called myWords and should be an ArrayList of type String to store unique
    * words from a file, and one is called myFreqs and should be an ArrayList of type Integer. The kth position in
    * myFreqs should represent the number of times the kth word in myWords occurs in the file.
    */
    private ArrayList<Integer> myFreqs;
    private ArrayList<String> myWords;

    /*
    * Write a constructor WordFrequencies, and initialize the private variables.
    */
    public WordFrequencies() {
        this.myFreqs = new ArrayList<>();
        this.myWords = new ArrayList<>();
    }

    /*
    * Write a void method findUnique that has no parameters. This method should first clear both myWords and myFreqs,
    * using the .clear() method. Then it selects a file and then iterates over every word in the file, putting the
    * unique words found into myWords. For each word in the kth position of myWords, it puts the count of how many times
    * that word occurs from the selected file into the kth position of myFreqs, as was demonstrated in the lesson.
    */

    private void findUnique() {
        this.myFreqs.clear();
        this.myWords.clear();

        FileResource fr = new FileResource();
        for (String s : fr.words()) {
            if (!this.myWords.contains(s.toLowerCase())) {
                this.myWords.add(s.toLowerCase());
                this.myFreqs.add(1);
            } else {
                this.myFreqs.set(this.myWords.indexOf(s.toLowerCase()), this.myFreqs.get(this.myWords.indexOf(s.toLowerCase())) + 1);
            }
        }
    }

    /*
    * Write a void tester method that has no parameters. This method should call findUnique. Then print out the number
    * of unique words, and for each unique word, print the frequency of each word and the word, as was demonstrated in
    * the lesson.
    */

    public void tester() {
        this.findUnique();
        System.out.println("unique: " + this.myWords.size());
        System.out.println(
                "index of max(from 0): " + this.findIndexOfMax() +
                        " word is \"" + this.myWords.get(this.findIndexOfMax()) +
                        "\" with counts " + this.myFreqs.get(this.findIndexOfMax())
        );
        System.out.println(this.myFreqs + "\n" + this.myWords);
    }

    /*
    * Write the method findIndexOfMax that has no parameters. This method returns an int that is the index location of
    * the largest value in myFreqs. If there is a tie, then return the first such value.
    */

    private int findIndexOfMax() {
        int max = this.myFreqs.stream().max(Integer::compareTo).orElse(-1);
        return this.myFreqs.indexOf(max);
    }

}
