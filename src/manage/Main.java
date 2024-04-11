package manage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;


public class Main {

    public static final String stud_table = "student";
    public static final String admin_table = "admin";
    public static final String ques_table = "questions";
    public static void mainDisp(){
        System.out.println("=========================================");
        System.out.println("Welcome to ATPF Quiz Interface");
        System.out.println("=========================================");
        System.out.println("1. Admin Login");
        System.out.println("2. Student Login");
        System.out.println("3. Exit Application");
        System.out.print("--> : ");
    }

    public static void sleep(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clr() {
        for(int i = 0; i<50; i++) System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        dbConnect db = new dbConnect();

        Connection conn = db.connect_to_db("quiz", "postgres", "admin");
        db.initialize(conn);

        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        boolean menu = true;
        while(menu){
            clr();
            mainDisp();
            String chosen = sc.next();

            switch(chosen){
                case "1":
                    Admin admin = new Admin();
                    while(admin.login());
                    break;
                case "2":
                    Student stud = new Student();

                    while(stud.login());
                    break;
                case "3":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    sleep(1500);
                    break;
            }

        }

    }
}