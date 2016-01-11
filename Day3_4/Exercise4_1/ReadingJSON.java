import java.io.FileReader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class ReadingJSON{
    static final String STUDENT = "Student";
    static final String DOJ = "Date Of Joining";
    static final String ID = "ID";
    static final String MARKS = "Marks";
    static final String MARK = "Mark";
    static final String SUBJECT = "Subject";
    static final String NAME = "Name";
    static final String STD = "Std";
    static final String TEACHER = "Teacher";
    static final String CLASSES_TAKING_CARE_OF = "Classes Taking Care Of";
    static final String SALARY = "Salary";

    public static void main(String[] args){
        JSONParser parser = new JSONParser();
        Student student = new Student();
        Teacher teacher = new Teacher();
        List<Mark> marks = new ArrayList<Mark>();
        try{
            Object obj = parser.parse(new FileReader("students-teachers.json"));
            JSONObject jsonobj = (JSONObject) obj;
            JSONObject jsonStudent = (JSONObject) jsonobj.get(STUDENT);
            student.id = (String)jsonStudent.get(ID);
            student.name = (String)jsonStudent.get(NAME);
            student.std = (String)jsonStudent.get(STD);
            student.doj = (String)jsonStudent.get(DOJ);
            JSONArray markArray = (JSONArray) jsonStudent.get(MARKS);
            Iterator<JSONObject> iterator = (Iterator<JSONObject>)markArray.iterator();
            while(iterator.hasNext()){
                JSONObject jsonMark = (JSONObject) iterator.next();
                marks.add(new Mark((Long)jsonMark.get(MARK),(String)jsonMark.get(SUBJECT)));
            }
            student.marks = marks.toArray(new Mark[marks.size()]);
            System.out.println(student);
            
            JSONObject jsonTeacher = (JSONObject) jsonobj.get(TEACHER);
            teacher.doj = (String) jsonTeacher.get(DOJ);
            teacher.id = (String) jsonTeacher.get(ID);
            teacher.name = (String) jsonTeacher.get(NAME);
            teacher.sal = (Long) jsonTeacher.get(SALARY);
            JSONArray jsonClassTakingCareOf = (JSONArray) jsonTeacher.get(CLASSES_TAKING_CARE_OF);
            Iterator<String> iterator1 = (Iterator<String>)jsonClassTakingCareOf.iterator();
            List<String> temp = new ArrayList<String>();
            while(iterator1.hasNext()){
                temp.add((String) iterator1.next());
            }
            teacher.classTakingCareOf = temp.toArray(new String[temp.size()]);
            System.out.println(teacher);
            
        }catch(Exception e){
            System.out.println("error "+e);
        }
    }
}


class Student{
    String doj;
    String id;
    String name;
    String std;
    Mark[] marks = new Mark[5];
    
    @Override
    public String toString(){
        String output = String.format("Student name : %s,\n id: %s, \nstd : %s,\n doj : %s, \nmarks :%s", name, id, std, doj, getMarks());
        return output;
    }
    
    public String getMarks(){
        StringBuilder output = new StringBuilder();
        for(int i=0; i<marks.length; i++){
            output.append(marks[i]+" ");
        }
        return output.toString();
    }
    
}

class Teacher{
    String[] classTakingCareOf;
    String doj;
    String name;
    Long sal;
    String id;
    
    public String toString(){
        return String.format("Teacher name : %s \nClasses Taking Care of : %s, \ndoj : %s, \nsal : %d", name, getClassTakingCareOf(), doj, sal);
    }
    
    public String getClassTakingCareOf(){
        StringBuilder output = new StringBuilder();
        for(int i=0; i<classTakingCareOf.length; i++){
            output.append(classTakingCareOf[i] + " ");
        }
        return output.toString();
    }
}

class Mark{
    private Long mark;
    private String subject;
    
    Mark(Long mark, String subject){
        this.mark = mark;
        this.subject = subject;
    }
    
    public void setMark(Long mark){
        this.mark = mark;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    public Long getMark(){
        return mark;
    }
    
    public String getSubject(){
        return subject;
    }
    
    @Override
    public String toString(){
        return String.format("Subject : %s, Mark : %d",subject,mark);
    }
}