import java.io.FileReader;
import java.util.Iterator;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

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
    
    public static void main(String[] args){
        Student student = new Student();
        Teacher teacher = new Teacher();
        List<Mark> marks = new ArrayList<Mark>();
        try{
            String jsonData = readFile("students-teachers.json");
            JSONObject jsonobj = new JSONObject(jsonData);
            
            JSONObject jsonStudent = jsonobj.getJSONObject(STUDENT);
            student.id = jsonStudent.getString(ID);
            student.name = jsonStudent.getString(NAME);
            student.std = jsonStudent.getString(STD);
            student.doj = jsonStudent.getString(DOJ);
            JSONArray markArray = jsonStudent.getJSONArray(MARKS);
            for(int i=0;i < markArray.length(); i++){
                JSONObject jsonMark = markArray.getJSONObject(i);
                marks.add(new Mark(jsonMark.getLong(MARK), jsonMark.getString(SUBJECT)));
            }
            student.marks = marks.toArray(new Mark[marks.size()]);
            System.out.println(student);
            
            JSONObject jsonTeacher = jsonobj.getJSONObject(TEACHER);
            teacher.doj = jsonTeacher.getString(DOJ);
            teacher.id = jsonTeacher.getString(ID);
            teacher.name = jsonTeacher.getString(NAME);
            teacher.sal = jsonTeacher.getLong(SALARY);
            JSONArray jsonClassTakingCareOf = jsonTeacher.getJSONArray(CLASSES_TAKING_CARE_OF);
            List<String> temp = new ArrayList<String>();
            for(int i=0;i < jsonClassTakingCareOf.length(); i++){
                temp.add(jsonClassTakingCareOf.getString(i));
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