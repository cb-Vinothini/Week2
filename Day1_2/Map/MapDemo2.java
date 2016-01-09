package Map;

import java.util.*;

public class MapDemo2{
    public static void main(String[] args){
        Map<String, Set<String>> m = new TreeMap<String, Set<String>>();
        for(String word : args){
            if(word.length() >= 3 ){
                String prefix = word.substring(0, 3);
                Set<String> listOfWords = m.get(prefix);
                if(listOfWords == null){
                    m.put(prefix, listOfWords = new TreeSet<String>());
                }
                listOfWords.add(word);
            }
        }
        System.out.println(m);
    }
}