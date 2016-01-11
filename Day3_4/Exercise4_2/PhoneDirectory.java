import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Iterator;

public class PhoneDirectory{
    private static final String [] FILE_HEADER_MAPPING = {"name","address","mobile","home","work"};

    private static final String PERSON = "Person";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String MOBILE_PH = "mobile";
    private static final String HOME_PH = "home";
    private static final String WORK_PH = "work";
    
    public static Contact[] setData(String fileName){

        JSONParser parser = new JSONParser();
        List<Contact> contacts = new ArrayList<Contact>();

        try{
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonobj = (JSONObject) obj;
            JSONArray personArray = (JSONArray) jsonobj.get(PERSON);
            Iterator<JSONObject> individualPerson = (Iterator<JSONObject>)personArray.iterator();
            while(individualPerson.hasNext()){
                JSONObject temp = individualPerson.next();
                List<String> phoneNos = new ArrayList<String>();
                phoneNos.add((String)temp.get(MOBILE_PH));
                phoneNos.add((String)temp.get(HOME_PH));
                phoneNos.add((String)temp.get(WORK_PH));
                Contact contact = new Contact((String)temp.get(NAME), (String)temp.get(ADDRESS), phoneNos);
                contacts.add(contact);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return contacts.toArray((new Contact[contacts.size()]));
    }
    
    public static void main(String[] args){
        String fileName = "jsonFile.json";
        Scanner scanner = new Scanner(System.in);

        Contact[] contact = setData(fileName);

        TwoKeyMap map = new TwoKeyMap();
        map.add(contact[0]);
        map.add(contact[1]);
        map.add(contact[2]);
        map.add(contact[3]);
        char choice;
        String name;
        do{
            System.out.print("Enter 1. Retrieve by name \n2. Partial retrieve by name\n3. Retrieve by phoneNos : ");
            int caseChoice = scanner.nextInt();
            switch(caseChoice){
                case 1:
                    System.out.print("Enter the name : ");
                    name = scanner.next();
                    System.out.println(map.getByName(name));
                    break;
                case 2:
                    System.out.print("Enter the prefix : ");
                    name = scanner.next();
                    System.out.println(map.partialGetByName(name));
                    break;
                case 3:
                    System.out.print("Enter the phone no : ");
                    name = scanner.next();
                    System.out.println(map.getByPhNo(name));
                    break;
                default:
                    System.out.println("Wrong choice");
            }
            System.out.print("Do you want to continue : ");
            choice = scanner.next().charAt(0);
        }while(choice == 'y');
    }
}

class TwoKeyMap{
    SortedMap<String, List<Contact>> nameMap = new TreeMap<String, List<Contact>>();
    TreeMap<String, Contact> phoneMap = new TreeMap<String, Contact>();
    
    public void add(Contact contact){
        String name = contact.getName();
        List<String> phoneNos = contact.getPhoneNos();
        
        //  Map with the name
        List<Contact> allContacts = nameMap.get(name);
        if(allContacts == null){
            nameMap.put(name, allContacts = new ArrayList<Contact>());
        }
        allContacts.add(contact);
        
        // Map with phone number
        for(String no : phoneNos){
            if(no != null)
                phoneMap.put(no, contact);
        }
    }
    
    public List<Contact> getByName(String name){
        return nameMap.get(name);
    }
    
    public Map<String, List<Contact>> partialGetByName(String prefix){
        char end =  (char)(prefix.charAt(prefix.length() - 1 ) + 1);
        String endPrefix = prefix.substring(0, prefix.length()-1) + end;
        
        return nameMap.subMap(prefix, endPrefix);
    }
    
    public Contact getByPhNo(String phoneNo){
        return phoneMap.get(phoneNo);
    }
    
}

class Contact{
    private String name, address;
    private List<String> phoneNos = new ArrayList<String>();
    
    @Override
    public String toString(){
        String output = String.format("name : %s, address : %s, Phonenos : ",getName(), getAddress());
        for(String no : getPhoneNos()){
            output = output.concat(no+", ");
        }
        return output;
    }
    
    Contact(String name, String address, List<String> phoneNos){
        this.name = name;
        this.address = address;
        this.phoneNos = phoneNos;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAddress(){
        return address;
    }
    
    public List<String> getPhoneNos(){
        return phoneNos;
    }
    
}