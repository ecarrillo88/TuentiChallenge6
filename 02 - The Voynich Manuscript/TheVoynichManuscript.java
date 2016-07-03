/**
 * 
 * Tuenti Challenge 6
 * Challenge 2 - The Voynich Manuscript
 * @author Enrique Carrillo (@ecarrillo88)
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class TheVoynichManuscript {
    
    private static String[] corpusToArray(Scanner file) {
        String corpus[] = new String[35001];
        int index = 1;
        while (file.hasNext()) {
            corpus[index++] = file.next();
        }
        return corpus;
    }
    
    private static Map<String, Integer> getWordMapByAppearances(String[] corpus, int firstWord, int lastWord) {
        Map<String, Integer> wordMap = new HashMap<String, Integer>();
        for (int i = firstWord; i <= lastWord; i++) {
            String word = corpus[i];
            if (wordMap.containsKey(word)) {
                wordMap.put(word, wordMap.get(word) + 1);
            } else {
                wordMap.put(word, 1);
            }
        }
        return wordMap;
    }
    
    private static List<Entry <String, Integer>> getWordListSortedByAppearances(Map<String, Integer> mapWords) {
        List<Entry <String, Integer>> list = new ArrayList<Entry<String, Integer>>(mapWords.entrySet());
        Collections.sort(list, new Comparator<Object>(){
            @SuppressWarnings("unchecked")
            public int compare(Object word1, Object word2){
                return ((Map.Entry<String, Integer>)word1).getValue().compareTo(((Map.Entry<String, Integer>)word2).getValue()); 
            }
        });
        return list;
    }
    
    public static void main(String[] args) {
        Scanner file = null;
        try {
            file = new Scanner(new File("corpus.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] corpus = corpusToArray(file);
        
        Scanner scanner = new Scanner(System.in);
        int fragments = scanner.nextInt();
        
        for (int i = 1; i <= fragments; i++) {
            int firstWord = scanner.nextInt();
            int lastWord = scanner.nextInt();
            Map <String, Integer> wordMap = getWordMapByAppearances(corpus, firstWord, lastWord);
            List<Entry<String, Integer>> wordList = getWordListSortedByAppearances(wordMap);
            int size = wordList.size();
            Entry<String, Integer> word1 = wordList.get(size-1);
            Entry<String, Integer> word2 = wordList.get(size-2);
            Entry<String, Integer> word3 = wordList.get(size-3);
            System.out.printf ("Case #%d: %s %s,%s %s,%s %s\n",
                                i,
                                word1.getKey(), word1.getValue(),
                                word2.getKey(), word2.getValue(),
                                word3.getKey(), word3.getValue());
        }
    }
    
}
