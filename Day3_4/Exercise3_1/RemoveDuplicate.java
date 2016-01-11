import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class RemoveDuplicate{
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    public static void main(String[] args) throws IOException{
        Set<String> set = null;
        Reader in = null;
        try{
            set = new HashSet<String>();
            String name = "csvFile.csv";
            in = new FileReader(name);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                StringBuilder line = new StringBuilder();
                for (String field : record) {
                    line.append(field+",");
                }
                set.add(line.toString());
            }
            System.out.println(set);
        }
        catch(IOException ioexc){
            System.out.println(ioexc);
        }
        finally{
            in.close();
        }
        
        String fileName = "RemovedDuplicates.csv";
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        try{
            fileWriter = new FileWriter(fileName);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            for(String str: set){
                String[] temp = str.split(",");
                csvFilePrinter.printRecord(Arrays.asList(temp));
            }
        }catch(IOException ioexc){
            System.out.println(ioexc);
        }finally{
            if(fileWriter != null)
                fileWriter.close();
            if(csvFilePrinter != null)
                csvFilePrinter.close();
        }
    }
}