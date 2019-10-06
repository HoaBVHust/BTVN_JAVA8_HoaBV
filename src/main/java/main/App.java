package main;

import com.mysql.jdbc.PreparedStatement;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "education";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String dbpassword = "";
        List<School> schools = new ArrayList<>();
        List<ClassOfSchool> classOfSchools = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<StudentSubjectRegister> studentSubjectRegisters = new ArrayList<>();
        try {
            Class.forName(driver);
            // Connection set up with database named as user
            Connection conn = DriverManager.getConnection(url+dbName, userName, dbpassword);
            // Creating statement for the connection to use sql queries
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from school");
            Load.loadList(rs,schools,School.class);
            rs = st.executeQuery("select * from classofschool");
            Load.loadList(rs,classOfSchools,ClassOfSchool.class);
            rs = st.executeQuery("select * from student");
            Load.loadList(rs,students,Student.class);
            rs = st.executeQuery("select * from subject");
            Load.loadList(rs,subjects,Subject.class);
            rs = st.executeQuery("select * from studentsubjectregister");
            Load.loadList(rs,studentSubjectRegisters,StudentSubjectRegister.class);
            rs.close();
            st.close();
            conn.close();

//            PreparedStatement prs = (PreparedStatement) conn.prepareStatement("insert into student (studentid, studentname, studentmobile, classid) VALUES (?,?,?,?)");
//            for (int i = 57; i <126 ; i++) {
//                prs.setInt(1,i);
//                prs.setString(2,"Nguyen Van Hoa"+i);
//                prs.setString(3,"0953432332"+i);
//                prs.setInt(4,(i-56)%3);
//                prs.execute();
//            }
//            Random ran = new Random();
//            PreparedStatement prs = (PreparedStatement) conn.prepareStatement("insert into studentsubjectregister (studentid, subjectid, score) VALUES (?,?,?)");
//            for (int i = 1; i <1001 ; i++) {
//                prs.setInt(1,i%8);
//                prs.setInt(2,i%125);
//                prs.setDouble(3,0.0 + (10.0 - 0.0) * ran.nextDouble());
//                prs.execute();
//            }
//            prs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*
         * 1. Sum of student of a school
         */
        schools.forEach(school->{
            int count = classOfSchools.stream()
                    .filter(c-> c.getSchoolId() == school.getSchoolId())
                    .mapToInt(c->(int)students.stream()
                        .filter(student -> student.getClassId()==c.getClassId())
                        .count()).sum();
            System.out.println("-----------------------------");
            System.out.println(school.getSchoolName()+"- Sum student: "+count);
        });

    }
}
