package week2GladLibs;

import edu.duke.FileResource;

import java.util.ArrayList;

/**
 * Created by alex on 10.12.16.
 * <p>
 * Assignment 2: Character Names
 * Write a program to determine the characters in one of Shakespeare’s plays that have the most speaking parts. Consider
 * the play “The Tragedy of Macbeth” in the file macbeth.txt. Here are a few lines from the file put into a much smaller
 * file called macbethSmall.txt
 * <p>
 * Note that each speaking part is at the beginning of the line (there may be some blanks before it) and has a period
 * immediately following it. Shakespeare used this format in many of his plays. Sometimes the name of the person to
 * speak was all capitalized and sometimes it was not.
 * <p>
 * Write a program to print out the main characters in one of Shakespeare’s plays, those with the most speaking parts.
 * You should identify a speaking part by reading the file line-by-line and finding the location of the first period on
 * the line. Then you will assume that everything up to the first period is the name of a character and count how many
 * times that occurs in the file. You will only print those characters that appear more often than others. Notice our
 * method is somewhat error prone. For example, a period is also used to indicate the end of a sentence. By printing out
 * only those characters that appear a lot, we will get rid of most of the errors. Periods that indicate the end of a
 * sentence will likely be a unique phrase so you won’t print that as it would just occur once or maybe twice.
 * <p>
 * In processing the complete play in macbeth.txt you should not print out every count—you would have too much output,
 * so instead print every count that is greater than or equal to some number (you decide what that number is).
 */
public class CharactersInPlay {
    /*
    * You will need to create two private ArrayLists. One to store the the names of the characters you find and one to
    * store the corresponding counts for each character.
    */
    private ArrayList<String> names;
    private ArrayList<Integer> counts;

    public CharactersInPlay() {
        this.names = new ArrayList<>();
        this.counts = new ArrayList<>();
    }

    /*
    * Write a void method named update that has one String parameter named person. This method should update the two
    * ArrayLists, adding the character’s name if it is not already there, and counting this line as one speaking part
    * for this person.
    */
    private void update(String person) {
        if (!this.names.contains(person)) {
            this.names.add(person);
            this.counts.add(1);
        } else {
            this.counts.set(this.names.indexOf(person), this.counts.get(this.names.indexOf(person)) + 1);
        }
    }

    /*
    * Write a void method called findAllCharacters that opens a file, and reads the file line-by-line. For each line,
    * if there is a period on the line, extract the possible name of the speaking part, and call update to count it as
    * an occurrence for this person. Make sure you clear the appropriate instance variables before each new file.
    */

    private void findAllCharacters() {
        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            int indexOfFirstPeriod = line.indexOf(".");
            if (indexOfFirstPeriod != -1) {
                this.update(line.substring(0, indexOfFirstPeriod));
            }
        }
    }

    /*
    * Write a void method called tester that has no parameters. This method should call findAllCharacters, and then for
    * each main character, print out the main character, followed by the number of speaking parts that character has.
    * A main character is one who has more speaking parts than most people. You’ll have to estimate what that number
    * should be. Test your method on the file macbethSmall.txt. and then macbeth.txt.
    */
    public void tester() {
        this.findAllCharacters();
        /*for (String character : this.names) {
            System.out.println(character + "\t" + this.counts.get(this.names.indexOf(character)));
        }*/
        this.charactersWithNumParts(10, 1000);
    }

    /*
    * Write a void method called charactersWithNumParts that has two int parameters named num1 and num2, where you can
    * assume num1 should be less than or equal to num2. This method should print out the names of all those characters
    * that have exactly number speaking parts, where number is greater than or equal to num1 and less than or equal to
    * num2. Add code in tester to test this method out.
    */
    private void charactersWithNumParts(int num1, int num2) {
        if (num1 <= num2) {
            for (String character : this.names) {
                if (this.counts.get(this.names.indexOf(character)) >= num1 &&
                        this.counts.get(this.names.indexOf(character)) <= num2) {
                    System.out.println(character + "\t" + this.counts.get(this.names.indexOf(character)));
                }
            }
        }
    }
}
