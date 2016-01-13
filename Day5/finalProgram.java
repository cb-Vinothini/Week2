
import org.apache.commons.csv.CSVRecord;
import java.util.List;

public class FinalProgram{
    public static void main(String[] args){
        List<CSVRecord> records = CSVOperations.readCSVFile("Data.csv");
        List<String> output = JsonOperations.formatRecords("Config.json", records);
        CSVOperations.writeCSVFile("FormattedData.csv", output);
    }
}
