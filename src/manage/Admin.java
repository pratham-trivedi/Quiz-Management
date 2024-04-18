package manage;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends person{

    private dbConnect db;
    modifyDB mod_db = new modifyDB();
    //Connection conn = db.connect_to_db("quiz", "postgres", "admin");
    Connection conn;
    private String a_ID;
    private String a_Pass;
    private String a_Name;

    Scanner sc = new Scanner(System.in);

    public Admin(dbConnect db){

        this.db = db;
        this.conn = this.db.get_conn();
    }
    public boolean login(){
        Main.clr();
        System.out.println("=========================================");
        System.out.println("Welcome to Admin login, please enter your credentials(type exit to quit)");
        System.out.println("**If you forgot your ID/Password, please contact Help Desk");
        System.out.println();
        System.out.print("ID: ");
        this.a_ID = sc.nextLine();
        if(a_ID.equalsIgnoreCase("exit")){
            return false;
        }
        System.out.print("Password: ");
        this.a_Pass = sc.nextLine();

        if(verify(a_ID, a_Pass)){
            while(menuAdmin());
            return false;
        }else{
            System.out.println("Your ID/pass combination is incorrect, try again");
            Main.sleep(2000);
            return true;
        }


    }



    private String dispOptions(String menu){
        while(true) {
            Main.clr();
            System.out.println("=========================================");
            System.out.println("Hello " + a_Name + ", you are in the " + menu + " menu.");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Display all");
            System.out.println("4. Return");
            String input = sc.next();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) return input;
            else{
                System.out.println("Invalid input, try again");
                Main.sleep(1500);
            }

        }
    }
    private boolean menuAdmin(){
        Main.clr();
        System.out.println("=========================================");
        System.out.println("Hello " + a_Name + ", what would you like to do?");
        System.out.println("1. Question");
        System.out.println("2. Student");
        System.out.println("3. Logout");

        String opt = sc.next();
        String operation;
        switch(opt){

            case "1":
                while(true) {
                    operation = dispOptions("Question");
                    if(!menuQuestion(operation)){
                        break;
                    }
                }
                return true;
            case "2":
                while(true) {
                    operation = dispOptions("Student");
                    if(!menuStud(operation)){
                        break;
                    };
                }
                return true;
            case "3":
                return false;
            default:
                System.out.println("Invalid option");
                Main.sleep(1500);
                return false;
        }
    }

    private boolean verify(String a_ID, String a_Pass){
        try {
            ResultSet admin_log = db.verify_admin(a_ID);
            if (!admin_log.isBeforeFirst() ) {
                return false;
            }else{
                admin_log.next();
                if(!a_Pass.equals(admin_log.getString(2)))
                    return false;
            }
            this.a_Name = admin_log.getString(1);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean menuStud(String operation){
            if(operation.equals("1")){
                Main.clr();
                System.out.println("=========================================");
                System.out.println("Enter the ID and password for Student");
                System.out.println("Make sure to remember the password");
                System.out.print("ID of student:");
                String s_ID = sc.next();
                ResultSet stud_cred = db.verify_Stud(s_ID);
                if(does_Element_exist(stud_cred)){
                    System.out.println("Student already exists.");
                    Main.sleep(2000);
                }else{
                    mod_db.add_Stud(conn, s_ID);
                }
            }else if(operation.equals("2")){
                Main.clr();
                System.out.println("=========================================");
                System.out.println("Enter the ID and password for Student to be deleted");
                System.out.println("Note: This operation is Not reversible");
                System.out.print("ID of student:");
                String s_ID = sc.next();
                ResultSet stud_cred = db.verify_Stud(s_ID);
                if(!does_Element_exist(stud_cred)) {
                    System.out.println("Student with ID " + s_ID + " doesn't exists");
                    Main.sleep(2000);
                }else{
                    mod_db.del_Stud(conn, s_ID);
                }
            }else if(operation.equals("3")){
                ResultSet all_Stud = db.getAllStuds();
                Main.clr();
                if(does_Element_exist(all_Stud)){
                    System.out.println("Here's a list of all the student");
                    int stud_count = 1;
                    try {
                    while(all_Stud.next()){

                        String output = String.format("%d. Name - %s %s, ID - %s , Password - %s", stud_count++, all_Stud.getString(2), all_Stud.getString(3), all_Stud.getString(1), all_Stud.getString(4));
                        System.out.println(output);

                    }
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Main.pressEnter();
                }else{
                    System.out.println("No student entry in database");
                    Main.sleep(2000);
                }
            }else{
                return false;
            }

            return true;
    }

    private boolean menuQuestion(String operation){
        if(operation.equals("1")){
            Main.clr();
            ResultSet quiz_code = db.dispQuizCode();
            System.out.println("=========================================");
            System.out.println("Enter the required Details for the Question");
            System.out.print("Enter Subject code(refer the table above):");
            String q_ID = sc.next();
            q_ID = q_ID.toLowerCase();
            String subject = verify_Qid(quiz_code, q_ID);
            inputQues(subject);
        }else if(operation.equals("2")){
            Main.clr();
            ResultSet quiz_code = db.dispQuizCode();
            System.out.println("=========================================");
            System.out.println("Enter the Code for the Subject (refer the table above)");
            System.out.print("code => ");
            String q_ID = sc.next();
            q_ID = q_ID.toLowerCase();
            String question = verify_Qid(quiz_code, q_ID);
            delQuestion(question);
        }else if(operation.equals("3")){
            ResultSet all_Questions = db.getAllQuestions();
            Main.clr();
            if(does_Element_exist(all_Questions)){
                System.out.println("Here's a list of all the Questions");
                int ques_count = 1;
                try {
                    while(all_Questions.next()){
                        System.out.println("=========================================");
                        System.out.println();
                        System.out.println("Question " + ques_count++);
                        System.out.println("Subject - " + all_Questions.getString(1));
                        System.out.println(all_Questions.getString(2));
                        System.out.println("A. "  + all_Questions.getString(3));
                        System.out.println("B. "  + all_Questions.getString(4));
                        System.out.println("C. "  + all_Questions.getString(5));
                        System.out.println("D. "  + all_Questions.getString(6));
                        System.out.println("Correct answer - "  + all_Questions.getString(7));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Main.pressEnter();
            }else{
                System.out.println("There are no questions in the Database");
                Main.sleep(2000);
            }
        }else{
            return false;
        }

        return true;
    }

    public void inputQues(String subject){

        if(subject.equals("error")){
            System.out.println("This code doesnt exist in database, try again");
            Main.sleep(2000);
            return;
        }

        String question;
        while(true){
            Main.clr();
            System.out.println("=========================================");
            System.out.println("Enter the Question, please ensure correct grammar and spellings.");
            System.out.println( "NOTE that incorrect spellings or spaces can lead to duplicate questions in database");
            sc.nextLine(); //flush the buffer
            question = sc.nextLine();
            System.out.println("=========================================");
            System.out.println("The Question you have entered is : ");
            System.out.println(question);
            System.out.println("Do You want to Edit the question? (\"y\" for yes, any other input for no) : ");
            String edit = sc.next();
            if(edit.equals("y") || edit.equals("Y"))continue;
            else break;
        }

        ResultSet rs = db.getQuestion(question);

        if(does_Element_exist(rs)){
            System.out.println("This question is already in database");
            Main.sleep(2000);
        }else{
            mod_db.add_Question(conn, subject, question);
        }
    }

    public void delQuestion(String subject){
        if(subject.equals("error")){
            System.out.println("This code doesnt exist in database, try again");
            Main.sleep(2000);
            return;
        }

        ResultSet question = db.getQuestionfromSubject(subject);

        if(does_Element_exist(question)){
            try {
                while (true) {
                    question.beforeFirst();
                    ArrayList<String> ques = new ArrayList<String>();
                    int ques_number = 1;
                    while (question.next()) {
                        System.out.println(ques_number++ + ". " + question.getString(2));
                        ques.add(question.getString(2));
                    }
                    System.out.println("which question would you like to delete: ");
                    int del_ques = sc.nextInt();
                    if (del_ques >= ques_number || del_ques < 1) {
                        System.out.println("Invalid option, select again");
                        continue;
                    } else {
                        mod_db.del_Question(conn, ques.get(del_ques-1));
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }else{
            System.out.println();
            System.out.println("There are no questions of this Subject");
            Main.sleep(2000);
            return;
        }
    }

}
