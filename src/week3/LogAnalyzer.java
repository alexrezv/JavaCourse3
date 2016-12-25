package week3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 17.12.16.
 */
public class LogAnalyzer {
    private LogFile logFile;
    private ArrayList<String> uniqueIPVisitsOnDay;
    private ArrayList<String> uniqueIpAddresses;
    private HashMap<String, Integer> countsPerIp;
    private HashMap<String, ArrayList<String>> ipsForDays;

    public LogAnalyzer(LogFile logFile) {
        this.logFile = logFile;
        this.uniqueIPVisitsOnDay = new ArrayList<>();
        this.uniqueIpAddresses = new ArrayList<>();
        setUniqueIpAddresses();
        this.countsPerIp = new HashMap<>();
        setCountsPerIp();
        this.ipsForDays = new HashMap<>();
        setIPsForDays();
    }

    public void printAll() {
        System.out.println("entire log-file:");
        for (LogEntry logEntry : logFile.getLogEntries()) {
            System.out.println(logEntry);
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }

    private void setUniqueIpAddresses() {
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            if (!uniqueIpAddresses.contains(logEntry.getIpAddress())) {
                uniqueIpAddresses.add(logEntry.getIpAddress());
            }
        }
    }

    public void printUniqueIpAddresses() {
        System.out.println("Unique IP-addresses are:");
        for (String ipAddress : uniqueIpAddresses) {
            System.out.println(ipAddress);
        }
        System.out.println("total count: " + uniqueIpAddresses.size());
    }

    /*
    * In the LogAnalyzer class, write the void method printAllHigherThanNum that has one integer parameter num.
    * This method should examine all the web log entries in records and print those LogEntrys that have a status code
    * greater than num. Be sure to add code in the Tester class to test out this method with the file short-test_log.
    */
    public void printAllHigherThanNum(int num) {
        System.out.println("Entries where status is greater than " + num + " :");
        for (LogEntry logEntry : logFile.getLogEntries()) {
            if (logEntry.getStatusCode() > num) {
                System.out.println(logEntry);
            }
        }
    }

    /*
    * In the LogAnalyzer class, write the method uniqueIPVisitsOnDay that has one String parameter named someday in the
    * format “MMM DD” where MMM is the first three characters of the month name with the first letter capitalized and
    * the others in lowercase, and DD is the day in two digits (examples are “Dec 05” and “Apr 22”). This method
    * accesses the web logs in records and returns an ArrayList of Strings of unique IP addresses that had access on the
    * given day. (Note that the dates in LogEntrys are stored as a Date object, but using toString will allow you to
    * access the characters in the Date. For example, consider that d is a Date. String str = d.toString(); allows you
    * to now use a String representation of the date.) Be sure to test your program with code in the Tester class.
    * Usingthe file weblog-short_log you should see that the call to uniqueIPVisitsOnDay(“Sep 14”) returns an ArrayList
    * of 2 items and uniqueIPVisitsOnDay(“Sep 30”) returns an ArrayList of 3 items.
    */
    private void setUniqueIPVisitsOnDay(String someday) {
        this.uniqueIPVisitsOnDay.clear();
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            if (logEntry.getAccessTime().toString().contains(someday)
                    && !this.uniqueIPVisitsOnDay.contains(logEntry.getIpAddress())) {
                this.uniqueIPVisitsOnDay.add(logEntry.getIpAddress());
            }
        }
    }

    public int countUniqueIPVisitsOnDay(String someday) {
        setUniqueIPVisitsOnDay(someday);
        return this.uniqueIPVisitsOnDay.size();
    }

    /*
    * In the LogAnalyzer class, write the method countUniqueIPsInRange that has two integer parameters named low and
    * high. This method returns the number of unique IP addresses in records that have a status code in the range from
    * low to high, inclusive. Be sure to test your program on several ranges. E.g. , using the file short-test_log,
    * the call countUniqueIPsInRange(200,299) returns 4, as there are four unique IP addresses that have a status code
    * from 200 to 299. The call countUniqueIPsInRange(300,399) returns 2. In this case, note that there are three
    * entries in the file that have a status code in the 300 range, but two of them have the same IP address.*/
    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<String> uniqIPsInRange = new ArrayList<>();
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            if (logEntry.getStatusCode() >= low &&
                    logEntry.getStatusCode() <= high &&
                    !uniqIPsInRange.contains(logEntry.getIpAddress())) {
                uniqIPsInRange.add(logEntry.getIpAddress());
            }
        }
        return uniqIPsInRange.size();
    }

    /**
     * Counting Web-Site Visits
     */
    private void setCountsPerIp() {
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            if (!countsPerIp.containsKey(logEntry.getIpAddress())) {
                this.countsPerIp.put(logEntry.getIpAddress(), 1);
            } else {
                this.countsPerIp.put(logEntry.getIpAddress(), this.countsPerIp.get(logEntry.getIpAddress()) + 1);
            }
        }
    }

    /*
    * In the LogAnalyzer class, write the method countVisitsPerIP, which has no parameters. This method returns a
    * HashMap<String, Integer> that maps an IP address to the number of times that IP address appears in records,
    * meaning the number of times this IP address visited the website. Recall that records stores LogEntrys from a file
    * of web logs. For help, refer to the video in this lesson on translating to code. Be sure to test this method on
    * sample files.
    */
    public HashMap<String, Integer> countVisitsPerIP() {
        return this.countsPerIp;
    }

    /*
    * In the LogAnalyzer class, write the method mostNumberVisitsByIP, which has one parameter, a
    * HashMap<String, Integer> that maps an IP address to the number of times that IP address appears in the web log
    * file. This method returns the maximum number of visits to this website by a single IP address. For example, the
    * call mostNumberVisitsByIP on a HashMap formed using the file weblog3-short_log returns 3.
    */
    public int mostNumberVisitsByIP() {
        return this.countsPerIp.entrySet().stream().map(Map.Entry::getValue).max(Integer::compareTo).orElse(null);
    }

    /*
    * In the LogAnalyzer class, write the method iPsMostVisits, which has one parameter, a HashMap<String, Integer> that
    * maps an IP address to the number of times that IP address appears in the web log file. This method returns an
    * ArrayList of Strings of IP addresses that all have the maximum number of visits to this website. For example, the
    * call iPsMostVisits on a HashMap formed using the file weblog3-short_log returns the ArrayList with these two IP
    * addresses, 61.15.121.171 and 84.133.195.161. Both of them visited the site three times, which is the maximum
    * number of times any IP address visited the site.
    */
    public ArrayList<String> getiPsMostVisits() {
        int maxVisits = mostNumberVisitsByIP();
        ArrayList<String> ipsWithMaxVisits = new ArrayList<>();
        for (Map.Entry e : this.countsPerIp.entrySet()) {
            if (e.getValue().equals(maxVisits)) {
                ipsWithMaxVisits.add(e.getKey().toString());
            }
        }
        return ipsWithMaxVisits;
    }

    /*
    * In the LogAnalyzer class, write the method iPsForDays, which has no parameters. This method returns a
    * HashMap<String, ArrayList<String>> that uses records and maps days from web logs to an ArrayList of IP addresses
    * that occurred on that day (including repeated IP addresses). A day is in the format “MMM DD” where MMM is the
    * first three characters of the month name with the first letter capital and the others in lowercase, and DD is the
    * day in two digits (examples are “Dec 05” and “Apr 22”). For example, for the file weblog3-short_log, after
    * building this HashMap, if you print it out, you will see that Sep 14 maps to one IP address, Sep 21 maps to four
    * IP addresses, and Sep 30 maps to five IP addresses.
    */
    private void setIPsForDays() {
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            ArrayList<String> ips = new ArrayList<>();
            String splittedDate[] = logEntry.getAccessTime().toString().split(" ");
            String day = splittedDate[1] + " " + splittedDate[2];
            for (LogEntry logEntry2 : this.logFile.getLogEntries()) {
                if (logEntry2.getAccessTime().toString().contains(day)) {
                    ips.add(logEntry2.getIpAddress());
                }
            }
            if (!this.ipsForDays.containsKey(day)) {
                this.ipsForDays.put(day, ips);
            }
        }
    }

    public HashMap<String, ArrayList<String>> getIPsForDays() {
        return this.ipsForDays;
    }

    /*
    * In the LogAnalyzer class, write the method dayWithMostIPVisits, which has one parameter that is a
    * HashMap<String, ArrayList<String>> that uses records and maps days from web logs to an ArrayList of IP addresses
    * that occurred on that day. This method returns the day that has the most IP address visits. If there is a tie,
    * then return any such day. For example, if you use the file weblog3-short_log, then this method should return the
    * day most visited as Sep 30.
    */
    public String dayWithMostIPVisits() {
        int maxIPs = this.ipsForDays.entrySet().stream().mapToInt(a -> a.getValue().size()).max().orElse(-1);
        for (String day : this.ipsForDays.keySet()) {
            if (this.ipsForDays.get(day).size() == maxIPs) {
                return day;
            }
        }
        return "NONE";
    }

    /*
    * In the LogAnalyzer class, write the method iPsWithMostVisitsOnDay, which has two parameters—the first one is a
    * HashMap<String, ArrayList<String>> that uses records and maps days from web logs to an ArrayList of IP addresses
    * that occurred on that day, and the second parameter is a String representing a day in the format “MMM DD”
    * described above. This method returns an ArrayList<String> of IP addresses that had the most accesses on the given
    * day. For example, if you use the file weblog3-short_log, and the parameter for the day is “Sep 30”, then there are
    * two IP addresses in the ArrayList returned: 61.15.121.171 and 177.4.40.87. Hint: This method should call another
    * method you have written.
    */
    public ArrayList<String> iPsWithMostVisitsOnDay(String day) {
        ArrayList<String> ipsOnDay = new ArrayList<>();
        for (LogEntry logEntry : this.logFile.getLogEntries()) {
            if (logEntry.toString().contains(day)) {
                ipsOnDay.add(logEntry.getIpAddress());
            }
        }
        HashMap<String, Integer> ipsToTimes = new HashMap<>();
        for (String address : ipsOnDay) {
            if (!ipsToTimes.containsKey(address)) {
                ipsToTimes.put(address, 1);
            }
            if (ipsToTimes.containsKey(address)) {
                ipsToTimes.put(address, ipsToTimes.get(address) + 1);
            }
        }
        int maxTimes = ipsToTimes.entrySet().stream().mapToInt(a -> a.getValue()).max().orElse(-1);
        ArrayList<String> ipsMOstVisits = new ArrayList<>();
        for (String address : ipsToTimes.keySet()) {
            if (ipsToTimes.get(address).equals(maxTimes)) {
                ipsMOstVisits.add(address);
            }
        }

        return ipsMOstVisits;
    }

}
