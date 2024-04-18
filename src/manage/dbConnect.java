package manage;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbConnect {
    private final String server_name = "quiz";
    private final String user_name = "postgres";
    private final String password = "admin";

    private Connection conn;
    public dbConnect(){
        try{
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+server_name, user_name, password);
            if(conn != null){
                System.out.println("connection established");
            }else{
                System.out.println("connection not established");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void createTableForAdmin(String table_name){
        Statement statement;
        try{
            String query = "create table if not exists " + table_name + "(name VARCHAR, password VARCHAR, a_id VARCHAR, primary key(a_id));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Admin table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTableForStudent(String table_name){
        Statement statement;
        try{
            String query = "create table if not exists " + table_name + "(stud_id VARCHAR, first_name VARCHAR, last_name VARCHAR, password VARCHAR, primary key(stud_id));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Student table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTableForQuestion(String table_name){
        Statement statement;
        try{
            String query = "create table if not exists " + table_name + "(subject VARCHAR, question VARCHAR, option_1 VARCHAR, option_2 VARCHAR, option_3 VARCHAR, option_4 VARCHAR, answer VARCHAR, primary key(question));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Question table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTableForQuiztoSubject(String table_name){
        Statement statement;
        try{
            String query = "create table if not exists " + table_name + "(q_id VARCHAR, subject VARCHAR, primary key(q_id));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Question table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void initialize(){
        createTableForAdmin(Main.admin_table);
        createTableForStudent(Main.stud_table);
        createTableForQuestion(Main.ques_table);
        createTableForQuiztoSubject(Main.quiz_table);
    }

    public Connection get_conn(){
        return this.conn;
    }

    ResultSet verify_Stud(String ID){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s where %s = '%s';", Main.stud_table, "stud_id", ID);
            statement = conn.createStatement();
            rs  = statement.executeQuery(query);
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }

    ResultSet verify_admin(String ID){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s where %s = '%s';", Main.admin_table, "a_id", ID);
            statement = conn.createStatement();
            rs  = statement.executeQuery(query);
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    boolean verify_sub(String code){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select count(*) from %s where %s = '%s';", Main.ques_table, "q_id", code);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next()) {
                int cnt = rs.getInt(1);
                return cnt > 0;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    ResultSet getAllStuds(){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s", Main.stud_table);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }
    ResultSet getAllQuestions(){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s", Main.ques_table);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }
    ResultSet getQuestion(String ques){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s where %s = '%s'", Main.ques_table, "question", ques);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }

    ResultSet getQuestionfromSubject(String subject){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s where %s = '%s'", Main.ques_table, "subject", subject);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }

    public ResultSet dispQuizCode() {
        ResultSet quiz_code;
        Statement statement;
        try {
            String query = String.format("select * from %s", Main.quiz_table);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            quiz_code = statement.executeQuery(query);
            while (quiz_code.next()) {
                System.out.println(quiz_code.getString(1) + " - " + quiz_code.getString(2));
            }
            return quiz_code;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }



}
