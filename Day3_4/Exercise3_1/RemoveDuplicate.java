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
        Reader in = null;
        Map<TestHashCode, String[]> map = null;
//        TestHashCode obj1 = new TestHashCode(2);
//        obj1.values[0] = "val1";
//        obj1.values[1] = "val2";
//        
//        TestHashCode obj2 = new TestHashCode(2);
//        obj2.values[0] = "val1";
//        obj2.values[1] = "val2";
//        
//        System.out.println(obj1.equals(obj2));
//        System.out.println(obj1.hashCode());
//        System.out.println(obj2.hashCode());
        
        try{
            map =  new HashMap<TestHashCode, String[]>();
            String name = "csvFile.csv";
            in = new FileReader(name);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                int size = record.size();
                TestHashCode tempObject = new TestHashCode(size);
                int index = 0;
                for (String field : record) {
                    tempObject.values[index++] = field;
                }
                map.put(tempObject, tempObject.values);
            }
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
            for(String[] str: map.values()){
                csvFilePrinter.printRecord(Arrays.asList(str));
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

class TestHashCode{
    public String[] values;
    
    TestHashCode(int size){
        values = new String[size];
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        TestHashCode castedObj = (TestHashCode)obj;
        int i = 0;
        for(String str : castedObj.values){
            if((values[i++].hashCode()) != (str.hashCode())){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode(){
        int hashValue = 0;
        for(String str : values){
            hashValue += str.hashCode();
        }
        return hashValue;
    }
    
}

