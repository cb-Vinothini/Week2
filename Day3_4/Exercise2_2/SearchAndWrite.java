
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.*;


public class SearchAndWrite{
    static String fileName = null;
    static String word = null;
    public static void main(String[] args) throws IOException{
        StringBuilder output = new StringBuilder();
        System.out.print("Enter file name : ");
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.next();
        System.out.print("Enter the word you want to search : ");
        word = scanner.next();
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            int lineCount = 0;
            while((line = reader.readLine()) != null){
                lineCount++;
                int charCount = 0;
                boolean firstCheck = true;
                String[] words = line.split(" ");
                for(int i = 0; i < words.length; i++){
                    if(word.compareTo(words[i]) == 0){
                        if(firstCheck){
                            firstCheck = false;
                            output.append(lineCount);
                            output.append(":");
                            output.append(charCount);
                        }
                        else{
                            output.append(",");
                            output.append(charCount);
                        }
                    }
                    else{
                        charCount += words[i].length();
                        charCount++;
                    }
                }
                output.append("\n");
            }
        }catch(Exception e){
            System.out.println(e);
        }finally{
            if(reader != null){
                reader.close();
            }
        }
        try{
            writer = new BufferedWriter(new FileWriter(word+".txt"));
            writer.write(output.toString());
        }catch(Exception e){
            System.out.println("error"+e);
        }finally{
            if(writer != null){
                writer.close();
            }
        }
        
    }
}