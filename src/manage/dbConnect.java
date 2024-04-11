package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbConnect {
    public Connection connect_to_db(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pass);
            if(conn != null){
                System.out.println("connection established");
            }else{
                System.out.println("connection not established");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }

    public void createTableForAdmin(Connection conn, String table_name){
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

    public void createTableForStudent(Connection conn, String table_name){
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

    public void createTableForQuestion(Connection conn, String table_name){
        Statement statement;
        try{
            String query = "create table if not exists " + table_name + "(q_number INTEGER, q_id VARCHAR, question VARCHAR, option_1 VARCHAR, option_2 VARCHAR, option_3 VARCHAR, option_4 VARCHAR, answer VARCHAR, primary key(q_id));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Question table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void initialize(Connection conn){
        createTableForAdmin(conn, Main.admin_table);
        createTableForStudent(conn, Main.stud_table);
        createTableForQuestion(conn, Main.ques_table);
    }

    ResultSet verify_Stud(Connection conn, String ID){
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

    ResultSet verify_admin(Connection conn, String ID){
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
    boolean verify_sub(Connection conn, String code){
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


}
