package Exercise1_2;

import java.nio.file.*;
import static java.nio.file.FileVisitResult.*;
import java.io.*;
import java.nio.file.attribute.*;

public class MovingDirectories extends SimpleFileVisitor<Path>{
    String targetPath = "/Users/cb-vinothini/Documents/Training/Week2/Day3_4/Exercise1_2/moved_files";
    public static void main(String[] agrs){
        Path sourcePath = Paths.get("/Users/cb-vinothini/Documents/Training/Week2/Day3_4/Exercise1_2/files");
        try{
            MovingDirectories move = new MovingDirectories();
            Files.walkFileTree(sourcePath, move);
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException{
        //Path source = Paths.get(attr.toString());
        String pattern = file.getFileName().toString();
        String fileName = pattern.substring(0,pattern.lastIndexOf("."));
        String extension = pattern.substring(pattern.lastIndexOf("."));
        Path target = Paths.get(targetPath +"/"+ pattern);
        try{
            Files.move(file, target);
        }catch(FileAlreadyExistsException exc){
            System.out.println("File already exists");

            pattern = "{"+fileName+"-*"+extension+"}";
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:"+pattern);
            File folder = new File(targetPath.toString());
            File[] listOfFiles = folder.listFiles();
            int count = 1;
            for(int i=0; i<listOfFiles.length ;i++){
                if(matcher.matches(listOfFiles[i].toPath())){
                    count++;
                }
            }
            String newTarget = targetPath+"/"+fileName+"-"+Integer.toString(count)+extension;
            Files.move(file, Paths.get(newTarget));
            
        }catch(Exception e){
            System.out.println("Error msg");
        }
        return CONTINUE;
    }
}

