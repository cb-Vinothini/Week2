package Exercise1_1;

import java.nio.file.*;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class PrintAllFiles extends SimpleFileVisitor<Path>{
    Map<String, Integer> map = new HashMap<String, Integer>();
    public static void main(String[] args){
        try{
            Path path = Paths.get("/Users/cb-vinothini/Documents/Training/Week1/Day1");
            PrintAllFiles visitor = new PrintAllFiles();
            Files.walkFileTree(path, visitor);
            System.out.println(visitor.map);
        }
        catch(Exception e){
            System.out.print("Error");
        }
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr)throws IOException{
        String name = file.getFileName().toString();
        name = name.substring(name.lastIndexOf("."));
        Integer value = map.get(name);
        if(value == null){
            map.put(name, 0);
        }
        map.put(name, map.get(name)+1);
        return CONTINUE;
    }
}
