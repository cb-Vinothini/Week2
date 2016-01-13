
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Arrays;

public class CSVOperations{
    public static Set<String> headers = new HashSet<String>();
    
    public static List<String> OUTPUT_FILE_HEADER = new ArrayList<String>();
    
    public static List<CSVRecord> readCSVFile(String fileLocation){
        CSVParser csvFileParser = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();
        List<CSVRecord> csvRecords = null;
        
        try{
            csvFileParser = new CSVParser(new FileReader(fileLocation), csvFileFormat);
            headers = csvFileParser.getHeaderMap().keySet();
            csvRecords = csvFileParser.getRecords();
            
        }catch(Exception e){
            System.out.println(e);
        }finally{
            try{
                if(csvFileParser != null)
                    csvFileParser.close();
            }
            catch(IOException ioExc){
                System.out.println(ioExc);
            }
        }
        return csvRecords;
    }
    
    public static void writeCSVFile(String fileLocation, List<String> csvRecords){
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormatOut = CSVFormat.DEFAULT.withHeader(OUTPUT_FILE_HEADER.toArray(new String[OUTPUT_FILE_HEADER.size()]));
        try{
            csvFilePrinter = new CSVPrinter(new FileWriter(fileLocation), csvFileFormatOut);
            for(String record: csvRecords){
                String[] rows = record.split("ESC");
                csvFilePrinter.printRecord(Arrays.asList(rows));
            }
        }catch(Exception e){
            System.out.println(e);
        }finally{
            try{
                csvFilePrinter.close();
            }catch(IOException ioExc){
                System.out.println(ioExc);
            }
        }

    }
}