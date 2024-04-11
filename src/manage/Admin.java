package manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Admin extends person{

    dbConnect db = new dbConnect();
    modifyDB mod_db = new modifyDB();
    Connection conn = db.connect_to_db("quiz", "postgres", "admin");
    private String a_ID;
    private String a_Pass;
    private String a_Name;

    Scanner sc = new Scanner(System.in);
    public boolean login(){
        Main.clr();
        System.out.println("=========================================");
        System.out.println("Welcome to Admin login, please enter your credentials");
        System.out.println("**If you forgot your ID/Password, please contact Help Desk");
        System.out.println();
        System.out.print("ID: ");
        this.a_ID = sc.nextLine();
        System.out.print("Password: ");
        this.a_Pass = sc.nextLine();

        if(verify(a_ID, a_Pass)){
            while(menuAdmin());
            String temp = sc.next();
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
                operation = dispOptions("Question");
                System.out.println("Question jova em ne");
                break;
            case "2":
                operation = dispOptions("Student");
                menuStud(operation);
                return true;
            case "3":
                return false;
            default:
                System.out.println("Invalid option");
                Main.sleep(1500);
                return false;
        }
        return false;
    }

    private boolean verify(String a_ID, String a_Pass){
        ResultSet admin_log = db.verify_admin(conn, a_ID);
        try {
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

    private void menuStud(String operation){
            if(operation.equals("1")){
                Main.clr();
                System.out.println("=========================================");
                System.out.println("Enter the ID and password for Student");
                System.out.println("Make sure to remember the password");
                System.out.print("ID of student:");
                String s_ID = sc.next();
                ResultSet stud_cred = db.verify_Stud(conn, s_ID);
                if(is_stud_present(stud_cred)){
                    System.out.println("Student already exists.");
                    return;
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
                ResultSet stud_cred = db.verify_Stud(conn, s_ID);
                if(!is_stud_present(stud_cred)) {
                    System.out.println("Student with ID " + s_ID + " doesn't exists");
                    return;
                }else{
                    mod_db.del_Stud(conn, s_ID);
                }
            }
    }

}