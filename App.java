
package edu.sdsu.cs;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Paths;
import java.lang.String;
import java.util.Collections;
import java.io.PrintWriter;
import java.io.File;

/**
 * Alyssa Gonzales & Neel Kumar Section M/W 5:30pm
 */
public class App {
    private static List<String> mostTokens = new ArrayList<>();
    private static ArrayList<String> MASTER = new ArrayList<>();

    private static ArrayList<File> toReturn = new ArrayList<File>();
    private static File startFolder;

    private App() {
        startFolder =
                new File("/Users/neelkumar/Desktop/practiceFolder");
    }

    private App(String[] args) {

        if (args.length == 1) {
            startFolder = new File(args[0]);
        }
    }

    private ArrayList<File> findFiles(File root) throws NullPointerException {
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if ((file.getName().endsWith(".txt")) ||
                        (file.getName().endsWith(".java"))) {
                    toReturn.add(file);
                }
            } else if (file.isDirectory()) {
                toReturn.addAll(findFiles(file));
            }
        }
        return toReturn;
    }

    public static void main(String[] args) throws IOException {
        List<String> MASTER = new ArrayList<>();

        //-----READING IN FILES----

        App app = new App();
        ArrayList<File> listOfFiles = app.findFiles(startFolder);

        //-------------------------

        //-----WRITE TO FILE------
        for (int i = 0; i < listOfFiles.size(); i++) {

                 mostTokens.clear();//setting list of most tokens to null

            File file = new File(listOfFiles.get(i).getAbsolutePath());
            String name = listOfFiles.get(i).getName();
            PrintWriter output = new PrintWriter(name + ".stats");
            Path filePath = Paths.get(listOfFiles.get(i).getAbsolutePath());
            List<String> lines = Files.readAllLines
                    (filePath, Charset.defaultCharset());
            MASTER = mostFrequentToken(lines);

            //---------------------

            String l1 = String.format("%20s:%03d", "#1. Longest line length",
                    app.longestLineLength(lines));
            String l2 = String.format("%20s:%03d", "#2. Average line length",
                    app.avgLineLength(lines));
            String l3 = String.format("%20s:%03d", "#3 number of unique" +
                    " space-delineated tokens (case-sensitive)",
                    app.caseSensitiveList(lines));
            String l4 = String.format("%20s:%03d", "#4. number of " +
                    "unique space-delineated tokens " +
                    "(case-insensitive)", app.caseInsensitiveList(lines));
            String l5 = String.format("%20s:%03d", "#5. Number of all " +
                    "unique space-delineated " +
                    "tokens in file", app.spaceDelineated(MASTER));
            output.println(name + ".stats");
            output.println(l1);
            output.println(l2);
            output.println(l3);
            output.println(l4);
            output.println(l5);
            output.println("#6 Most Frequent token(s): "+ mostTokens);
            List<String> printMostFreq = app.tenMostFreq(MASTER);
            output.println("#7. 10 most frequent tokens (case-insensitive) and their count");
            output.println("---------------");
            for (int m = 0; m < 10; m++) {
                output.println("Token " + (m + 1) + " : " +
                        printMostFreq.get(m));
            }
            output.println("---------------");
            List<String> printLeastFreq = app.tenLeastFreq(MASTER);
            output.println("#8. 10 Least frequent tokens (case-insensitive) and their count");
            for (int m = 0; m < 10; m++) {
                output.println("Token " + (m + 1) + " : " +
                        printLeastFreq.get(m));
            }
            output.close();

        }
    }


    // #1 length of longest line in file
    private int longestLineLength(List<String> line) {
        int longestLine = 0;
        for (int i = 0; i < line.size(); i++) {
            String l = line.get(i);
            if (longestLine < l.length())
                longestLine = l.length();
        }
        return longestLine;
    }

    // #2 average length in file
    private int avgLineLength(List<String> line) {
        int avg = 0;
        for (int i = 0; i < line.size(); i++) {
            String l = line.get(i);
            avg = avg + l.length();
        }
        return (avg) / (line.size());
    }

    // #3 number of of unique space-delineated tokens (case-sensitive)
    private int caseSensitiveList(List<String> line) {
        List<String> masterList = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            String l = line.get(i);
            String[] sentence = l.split(" ");
            for (int j = 0; j < sentence.length; j++) {
                if (!masterList.contains(sentence[j]))
                    masterList.add(sentence[j]);
            }
        }

        return masterList.size();
    }

    // #4 number of unique space-delineated tokens (case-insensitive)
    private int caseInsensitiveList(List<String> line) {
        List<String> masterList = new ArrayList<>();
        for (int k = 0; k < line.size(); k++) {
            String l = line.get(k);
            String[] sentence = l.split(" ");
            for (int j = 0; j < sentence.length; j++) {
                sentence[j] = sentence[j].toLowerCase();
            }
            for (int i = 0; i < sentence.length; i++) {
                if (!masterList.contains(sentence[i]))
                    masterList.add(sentence[i]);
            }
        }
        return masterList.size();
    }

    // #5 number of all space-delineated tokens in file
    private int spaceDelineated(List<String> line) {
        int tokens = 0;
        for (int i = 0; i < line.size(); i++) {
            String l = line.get(i);
            String[] tokenCount = l.split(" ");
            tokens = tokens + tokenCount.length;
        }
        return tokens;
    }

    //#6 Most frequently occurring token

    private static List<String> mostFrequentToken(List<String> line) {
        int highestCount;
        int max = 0;
        String popWord = "";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            String l = line.get(i);
            StringTokenizer tokenizer = new StringTokenizer(l);
            while (tokenizer.hasMoreTokens()) {
                list.add(tokenizer.nextToken());
            }
            for (int j = 0; j < list.size(); j++) {
                if (!list.get(j).equals(" ")) {
                    highestCount = (Collections.frequency(list, list.get(j)));
                    if (highestCount > max) {
                        max = highestCount;
                        popWord = list.get(j);
                    }
                }
            }
            for (int k = 0; k < list.size(); k++) {
                MASTER.add(list.get(k));
            }
        }
        for(int m = 0; m < list.size();m++){
            highestCount = (Collections.frequency(list, list.get(m)));
            if(max == highestCount && !mostTokens.contains(list.get(m))){
                mostTokens.add(list.get(m));
            }
        }
        return list;
    }


        // private String getString(String p)
        //#7 Count of most frequently occuring token (case insensitive)
        private int countOfToken (List < String > line) {
            int highestCount = 0;
            String popWord = "";
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < line.size(); i++) {
                String l = line.get(i);
                StringTokenizer tokenizer = new StringTokenizer(l);
                while (tokenizer.hasMoreTokens()) {
                    list.add(tokenizer.nextToken().toLowerCase());
                }
            }


            int max = 0;
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals(" ")) {
                    highestCount = (Collections.frequency(list, list.get(i)));
                    if (highestCount > max) {
                        max = highestCount;
                        popWord = list.get(i);
                    }
                }

            }
            return max;

        }

        private List<String> tenMostFreq (List<String> MASTER) {
            for (int i = 0; i < MASTER.size(); i++) {
                MASTER.get(i).toLowerCase();
            }
            List<String> MC = new ArrayList<>(); //master copy
            //frequency count array
            List<Integer> count = new ArrayList<>(MASTER.size());
            //count copy
            List<Integer> countC = new ArrayList<>(MASTER.size());
            //adding to final copy with words in order
            ArrayList<String> finalList = new ArrayList<>();
            //list of topTen results
            ArrayList<String> topTenList = new ArrayList<>();
            List<String> finalReturn = new ArrayList<>();


            int index = 0;
            int indexOfFreq = 0;
            int printFinal = 0;
            for (String token : MASTER) {
                if (!MC.contains(token)) {
                    MC.add(token);
                }
            }
            for (String t : MC) {
                count.add(Collections.frequency(MASTER, t));
            }

            //edit MC, take out duplicates, turn everything .toLowercase();

            for (int i = 0; i < count.size(); i++) {
                countC.add(count.get(i));
            }
            Collections.sort(countC);
            //listing the array in order for freq
            for (int k = countC.size() - 1; k >= 0; k--) {
                index = countC.get(k);

                indexOfFreq = count.indexOf(index);
                finalList.add(MC.get(indexOfFreq));

                count.remove(indexOfFreq);
                MC.remove(indexOfFreq);
            }

            for (String topTen : finalList) {
                if (printFinal < 10) {
                    topTenList.add(topTen + " " +
                            Collections.frequency(MASTER, topTen));
                }
                printFinal++;
            }
            return topTenList;
        }

        private List<String> tenLeastFreq (List < String > MASTER) {
            for (int i = 0; i < MASTER.size(); i++) {
                MASTER.get(i).toLowerCase();
            }
            List<String> MC = new ArrayList<>();
            List<Integer> count = new ArrayList<>(MASTER.size());
            List<Integer> countC = new ArrayList<>(MASTER.size());
            List<String> finalList = new ArrayList<>(); 
            List<String> finalReturn = new ArrayList<>();


            int index = 0;
            int indexOfFreq = 0;
            int printFinal = 0;
            for (String token : MASTER) {
                if (!MC.contains(token)) {
                    MC.add(token);
                }
            }
            for (String t : MC) {
                count.add(Collections.frequency(MASTER, t));
            }

            for (int i = 0; i < count.size(); i++) {
                countC.add(count.get(i));
            }
            Collections.sort(countC);
            //listing the array in order for freq
            for (int k = 0; k < countC.size(); k++) {
                index = countC.get(k);

                indexOfFreq = count.indexOf(index);
                finalList.add(MC.get(indexOfFreq));

                count.remove(indexOfFreq);
                MC.remove(indexOfFreq);
            }

            for (String topTen : finalList) {
                if (printFinal < 10) {
                    finalReturn.add(topTen + " " +
                            Collections.frequency(MASTER, topTen));
                }
                printFinal++;
            }
            return finalReturn;
        }


    }
