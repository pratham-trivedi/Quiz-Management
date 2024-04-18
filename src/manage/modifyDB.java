package manage;

import java.sql.*;
import java.util.ArrayList;
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

    void add_Question(Connection conn, String q_ID, String ques){
        Main.clr();
        ArrayList<String> q = new ArrayList<String>(7);
        q.add(q_ID);
        q.add(ques);
        System.out.println("Question: " + ques);
        System.out.println("Option A: ");
        q.add(sc.nextLine());
        System.out.println("Option B: ");
        q.add(sc.nextLine());
        System.out.println("Option C: ");
        q.add(sc.nextLine());
        System.out.println("Option D: ");
        q.add(sc.nextLine());
        String ans;
        while(true) {
            System.out.println("Answer(A, B, C or D): ");
            ans = sc.nextLine().toLowerCase();
            if (!(ans.equals("a") || ans.equals("b") || ans.equals("c") || ans.equals("d"))) {
                System.out.println("Invalid choice for Answer");
            }else{
                break;
            }
        }
        q.add(ans);

        Statement statement;
        try{
            String query = String.format("insert into %s values " +
                    "('%s', '%s', '%s', " + "'%s', '%s', '%s', '%s');", Main.ques_table, q.get(0), q.get(1),q.get(2),q.get(3),q.get(4),q.get(5),q.get(6));
            statement = conn.createStatement();
            statement.execute(query);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("Question is Successfully added in the Database");
        Main.pressEnter();
    }

    void del_Question(Connection conn, String question){
        Statement statement;
        try{
            String query = String.format("delete from %s where %s = '%s';", Main.ques_table, "question", question);
            statement = conn.createStatement();
            statement.execute(query);
            System.out.println("The question has been deleted");
            System.out.println("Press any key to continue...");
            System.in.read();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
