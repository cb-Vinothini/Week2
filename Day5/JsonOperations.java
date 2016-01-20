
import org.apache.commons.csv.CSVRecord;
import org.json.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.lang.StringBuilder;
import java.text.DecimalFormat;

public class JsonOperations{
    
    private static List<String> outputHeader = new ArrayList<String>();
    
    private static final String OUTPUT_HEADER = "Output Header";

    private static final String INVOICE_DATE = "Invoice Date";
    private static final String START_DATE = "Start Date";
    private static final String AMOUNT = "Amount";
    private static final String REFUNDED_AMOUNT = "Refunded Amount";
    private static final String TAX_TOTAL = "Tax Total";
    private static final String FROM = "From";
    private static final String TO = "To";
    private static final String NEW_HEADER = "New Header";
    private static final String TYPE = "Type";
    private static final String VALUE = "Value";
    private static final String DECIMAL_PLACE_PATTERN = "Decimal Place Pattern";
    private static final String CUST_FIRST_NAME = "Customer First Name";
    private static final String CUST_LAST_NAME = "Customer Last Name";
    private static final String CUST_EMAIL = "Customer Email";
    private static final String CUST_COMPANY = "Customer Company";
    private static final String CONCATENATE = "Concatenate";
    
    /*private static final String CUST_ID = "Customer Id";
    private static final String SUBS_ID = "Subscription Id";
    private static final String INVOICE_ID = "Invoice Id";
    private static final String STATUS = "Status";
    private static final String PAID_ON = "Paid On";
    private static final String NEXT_RETRY = "Next Retry";
    private static final String RECURRING = "Recurring";
    private static final String FIRST_INVOICE = "First Invoice";
    private static final String CUST_DETAILS = "Customer Details";
    
    private static final String INVOICE_NO = "Invoice Number";
*/

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static public List<String> formatRecords(String fileLocation, List<CSVRecord> records){
        //List<String> headerToChange = new LinkedList<String>();
        List<String> outputFormatted = new ArrayList<String>();
        try{
            String jsonData = readFile(fileLocation);
            JSONObject jsonobj = new JSONObject(jsonData);
            
            JSONArray array = jsonobj.getJSONArray(OUTPUT_HEADER);
            for(int i=0;i < array.length(); i++){
                outputHeader.add(array.getString(i));
            }
            CSVOperations.OUTPUT_FILE_HEADER = outputHeader;

            Set<String> headerSet = CSVOperations.headers;
            for(CSVRecord record : records){
                StringBuilder custDetails = new StringBuilder();
                String[] modifiedRow = new String[outputHeader.size()];
                for(String header : headerSet){
                    String csvValue = record.get(header);
                    String newHeader = null;
                    switch(header){
                        case INVOICE_DATE :
                        case START_DATE:
                            JSONObject jsonDate = jsonobj.getJSONObject(header);
                            newHeader = jsonDate.getString(NEW_HEADER);
                            if(csvValue != null){
                                String fromDateFormat = jsonDate.getString(FROM);
                                String toDateFormat = jsonDate.getString(TO);
                                
                                DateFormat originalFormat = new SimpleDateFormat(fromDateFormat);
                                DateFormat targetFormat = new SimpleDateFormat(toDateFormat);
                                Date date = originalFormat.parse(csvValue);
                                csvValue = targetFormat.format(date);
                            }
                            break;
                        case AMOUNT:
                        case REFUNDED_AMOUNT:
                        case TAX_TOTAL:
                            JSONObject jsonCalculation = jsonobj.getJSONObject(header);
                            newHeader = (String)jsonCalculation.getString(NEW_HEADER);
                            if(csvValue != null){
                                String type = jsonCalculation.getString(TYPE);
                                Double value = jsonCalculation.getDouble(VALUE);
                                String decimalPlacePattern = jsonCalculation.getString(DECIMAL_PLACE_PATTERN);
                                Double tempCsvValue = Double.parseDouble(csvValue);
                                if(type.equals("Multiplication")){
                                    tempCsvValue *= value;
                                }
                                else if(type.equals("Division")){
                                    tempCsvValue /= value;
                                }
                                DecimalFormat df = new DecimalFormat(decimalPlacePattern);
                                csvValue = String.valueOf(df.format(tempCsvValue));
                            }
                            break;
                        case CUST_FIRST_NAME:
                        case CUST_LAST_NAME:
                        case CUST_EMAIL:
                        case CUST_COMPANY:
                            JSONObject jsonCustomer = jsonobj.getJSONObject(header);
                            newHeader = (String)jsonCustomer.getString(NEW_HEADER);
                            if(header.equals(CUST_COMPANY)){
                                custDetails.append(jsonCustomer.getString(CONCATENATE)+csvValue+"\"}");
                                csvValue = custDetails.toString();
                            }else{
                                custDetails.append(jsonCustomer.getString(CONCATENATE)+csvValue);
                            }
                            break;
                        default:
                            try{
                                newHeader = (jsonobj.getJSONObject(header)).getString(NEW_HEADER);
                            }
                            catch(Exception e){
                                newHeader = header;
                            }
                    }
                    int index = outputHeader.indexOf(newHeader);
                    if(index != 0){
                        modifiedRow[index] = new String("ESC"+csvValue);
                    }
                    else{
                        modifiedRow[index] = new String(csvValue);
                    }
                }
                StringBuilder fullRow = new StringBuilder();
                for(int i=0; i<modifiedRow.length; i++){
                    fullRow.append(modifiedRow[i].toString());
                }
                outputFormatted.add(fullRow.toString());
            }
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(outputFormatted.get(0));
        return outputFormatted;
    }
    
    
}