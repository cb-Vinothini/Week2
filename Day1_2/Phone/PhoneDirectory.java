import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneDirectory{
    
    public static Contact[] setData(){
        List<String> no1 = new ArrayList<String>();
        no1.add("1234");
        no1.add("2345");
        no1.add("1345");
        List<String> no2 = new ArrayList<String>();
        no2.add("4672");
        no2.add("4612");
        no2.add("9375");
        List<String> no3 = new ArrayList<String>();
        no3.add("7582");
        no3.add(null);
        no3.add(null);
        List<String> no4 = new ArrayList<String>();
        no3.add("3452");
        no3.add("7842");
        no3.add(null);
        
        Contact[] contact = {new Contact("person1", "address1", no1), new Contact("person2", "address2", no2), new Contact("person1", "address3", no3), new Contact("human2", "address4", no4)};
        return contact;
    }
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Contact[] contact = setData();

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