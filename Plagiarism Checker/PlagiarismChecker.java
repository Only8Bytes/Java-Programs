package plagiarismchecker;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PlagiarismChecker {

    public static void main(String[] args) {
        //create map for file 1 and file 2, populate maps by reading each file then send to the result function
        Map<String, Integer> firstFileMap = new HashMap<>();
        Map<String, Integer> secondFileMap = new HashMap<>();
        ReadFile(firstFileMap, args[0]);
        ReadFile(secondFileMap, args[1]);
        displayResults(firstFileMap, secondFileMap, args[2]);
    }
    
    public static void ReadFile(Map<String, Integer> map, String fileName){
        //read through the file of the name specified to get each word, put into the map if new or increment the count if it already exists
        try(Scanner input = new Scanner(Paths.get(fileName))){
            input.useDelimiter("\\s+|(\\.|,|-|;|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)*(\\s)*(\\.|,|-|;|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)+(\\s)*");
            while (input.hasNext()){
               String word = input.next().toLowerCase();
               if (map.containsKey(word)){
                   map.put(word, map.get(word) + 1);
               } else {
                   map.put(word, 1);
               }
            }
        }
        catch (IOException | NoSuchElementException | IllegalStateException e){
            e.printStackTrace();
        }
    }
    
    public static double compareMaps(Map<String, Integer> map1, Map<String, Integer> map2){
        //get the total number of keys shared between the two maps, get the total unique keys between the two, divide the shared by the unique to get the jaccard index
        TreeSet<String> commonKeys = new TreeSet<>(map1.keySet());
        commonKeys.retainAll(map2.keySet());
        
        TreeSet<String> totalKeys = new TreeSet<>(map1.keySet());
        totalKeys.addAll(map2.keySet());
        
        float common = commonKeys.size();
        float total = totalKeys.size();
        
        return common/total;
    }
    
    public static void displayResults(Map<String, Integer> map1, Map<String, Integer> map2, String thresholdArg){
        //get a set of all shared keys between the two maps
        TreeSet<String> commonKeys = new TreeSet<>(map1.keySet());
        commonKeys.retainAll(map2.keySet());
        
        //call compareMaps to calculate the jaccard index and then convert the threshold argument to a float
        double jaccard = compareMaps(map1, map2);
        float threshold;
        try {
           threshold = Float.parseFloat(thresholdArg);
        }
        catch (NumberFormatException e)
        {
           threshold = 0;
        }
        
        //display the results in the output as expected, showing all shared words and the count in each file as well as stating if this is plagiarism or not
        System.out.printf("Number of unique words in document 1: %d%n", map1.keySet().size());
        System.out.printf("Number of unique words in document 2: %d%n", map2.keySet().size());
        System.out.printf("They have %d words in common%n%n", commonKeys.size());
        
        System.out.printf("%nThe common words and their counts in Document 1 and document 2, respectively.%nWord\t\t\tCount1\t\tCount2%n");
        for (String key : commonKeys) {
            System.out.printf("%-15s%15s%15s%n", key, map1.get(key), map2.get(key));
        }
        System.out.println();
        if (jaccard >= threshold){
            System.out.printf("This is plagiarism. Similarity score = %f >= threshold %f", jaccard, threshold);
        } else {
            System.out.printf("This is not plagiarism. Similarity score = %f < threshold %f", jaccard, threshold);
        }
        System.out.println();
    }
}