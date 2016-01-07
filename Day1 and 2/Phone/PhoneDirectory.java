package Phone;

import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneDirectory{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String[] no1 = {"1234", "2345", "1345"};
        String[] no2 = {"4672", "4612", "9375"};
        String[] no3 = {"7582", null, null};
        String[] no4 = {"3452", "7842", null};

        Contact[] contact = {new Contact("person1", "address1", no1), new Contact("person2", "address2", no2), new Contact("person1", "address3", no3), new Contact("human2", "address4", no4)};

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
        String[] phoneNos = contact.getPhoneNos();
        
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
    private String[] phoneNos = new String[3];
    
    @Override
    public String toString(){
        String output = String.format("name : %s, address : %s, Phonenos : ",getName(), getAddress());
        for(String no : getPhoneNos()){
            output = output.concat(no+", ");
        }
        return output;
    }
    
    Contact(String name, String address, String[] phoneNos){
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
    
    public String[] getPhoneNos(){
        return phoneNos;
    }
    
}