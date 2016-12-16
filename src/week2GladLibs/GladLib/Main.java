package week2GladLibs.GladLib;

/**
 * Created by alex on 11.12.16.
 */
public class Main {
    public static void main(String[] args) {
        //GladLib gl = new GladLib();
        //gl.makeStory();
        GladLibMap glm = new GladLibMap();
        glm.makeStory();
        System.out.println("total words in arrays: " + glm.totalWordsInMap());
        System.out.println("total words considered: " + glm.totalWordsConsidered());
    }
}
