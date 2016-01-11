
import java.util.Map;
import java.util.TreeMap;
import java.io.*;
import java.util.regex.Pattern;

public class Occurrences{
    static String fileName = "TextFile.txt";
    static String ListWords = "ListOfWords.txt";
    
    public static void main(String[] args) throws IOException{
        BufferedReader reader = null;
        BufferedWriter writer = null;
        Map<String, Integer> map = null ;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            Pattern pureWord = Pattern.compile("[a-z,A-Z]*");
            map = new TreeMap<String, Integer>();
            String line;
            while((line = reader.readLine()) != null){
                line = line.toLowerCase();
                String[] words = line.split(" ");
                for(String word: words){
                    if(pureWord.matcher(word).matches()){
                        Integer val = map.get(word);
                        if(val == null){
                            map.put(word, 1);
                        }
                        else{
                            map.put(word, map.get(word)+1);
                        }
                    }
                }
            }
            System.out.println(map);
        }
        catch(Exception e){
            System.out.println("error"+e);
        }
        finally{
            if(reader != null){
                reader.close();
            }
        }
        try{
            writer = new BufferedWriter(new FileWriter(ListWords));
            for(Map.Entry<String, Integer> e : map.entrySet()){
                String output = String.format("[%s] : [%d]\n",e.getKey(),e.getValue());
                writer.write(output);
            }
        }catch(Exception e){
            System.out.println("error"+e);
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }
}