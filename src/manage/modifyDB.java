package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class modifyDB {
    Scanner sc = new Scanner(System.in);
    void add_Stud(Connection conn, String ID){
        System.out.print("Password for student: ");
        String s_Pass = sc.next();
        System.out.print("Student's first name: ");
        String s_Fname= sc.next();
        System.out.print("Student's last name: ");
        String s_Lname= sc.next();

        Statement statement;
        try{
            String query = String.format("insert into %s values ('%s', '%s', '%s', '%s');", Main.stud_table, ID, s_Fname, s_Lname, s_Pass);
            statement = conn.createStatement();
            statement.execute(query);
            System.out.println("Student " + s_Fname + " " + s_Lname + " with " + ID + " has been added to the Database");
            System.out.println("Press any key to continue...");
            System.in.read();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    void del_Stud(Connection conn, String ID){

        Statement statement;
        try{
            String query = String.format("delete from %s where %s = '%s';", Main.stud_table, "stud_id", ID);
            statement = conn.createStatement();
            statement.execute(query);
            System.out.println("Student with ID = " + ID + " has been deleted from the Database");
            System.out.println("Press any key to continue...");
            System.in.read();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    ResultSet disp_Stud(Connection conn){
    return null;
    }

}
