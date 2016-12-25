package week3;

import edu.duke.URLResource;

/**
 * Created by alex on 16.12.16.
 */
public class Main {
    public static void main(String[] args) {
        /*FileResource fileResource = new FileResource(); // "./weblog-short_log"
        LogFile logFile = new LogFile(fileResource);*/

        URLResource urlResource = new URLResource("http://www.dukelearntoprogram.com/java/weblog2_log");
        LogFile logFile = new LogFile(urlResource);

        LogAnalyzer logAnalyzer = new LogAnalyzer(logFile);
        logAnalyzer.printAll();
        logAnalyzer.printUniqueIpAddresses();
        logAnalyzer.printAllHigherThanNum(400);

        String someday = "Sep 24";
        System.out.println("Unique visits on " + someday + ": " + logAnalyzer.countUniqueIPVisitsOnDay(someday));

        int low = 400;
        int high = 499;
        System.out.println("Unique IPs within the range " + low + "-" + high + ": "
                + logAnalyzer.countUniqueIPsInRange(low, high));

        /** Counting Web-Site Visits */
        System.out.println("/**** Counting Web-Site Visits ****/");
        System.out.println("Most visits by ip: " + logAnalyzer.mostNumberVisitsByIP());
        System.out.println("IPs with most visits: " + logAnalyzer.getiPsMostVisits());
        System.out.println("Day with most visits: " + logAnalyzer.dayWithMostIPVisits());
        String day = "Sep 29";
        System.out.println("IPs with most visits on " + day + ": " + logAnalyzer.iPsWithMostVisitsOnDay(day));
    }
}