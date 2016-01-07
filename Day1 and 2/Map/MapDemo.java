package Map;

import java.util.*;

public class MapDemo{
    public static void main(String[] args){
        Map<Integer, List<String>> m= new HashMap<Integer, List<String>>();
        for(String word : args){
            int len = word.length();
            List<String> listOfWords = m.get(len);
            if(listOfWords == null){
                m.put(len, listOfWords = new ArrayList<String>());
            }
            listOfWords.add(word);
        }
        MapDemo.printMap(m);
        //System.out.println(m);
    }
    
    static < X, Y > void printMap(Map< X ,Y > m){
        for(Map.Entry< X, Y > e : m.entrySet()){
            System.out.println("Key : " + e.getKey() + " and values : " + e.getValue());
        }
    }
}