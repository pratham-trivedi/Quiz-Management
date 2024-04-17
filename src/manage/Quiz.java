package manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Quiz {
    private final Connection conn;
    private final Scanner sc;

    public Quiz(Connection conn) {
        this.conn = conn;
        this.sc = new Scanner(System.in);
    }

    public void start(String q_id) {
        try {
            ResultSet questions = fetchQuestions(q_id);
            int score = 0;
            while (questions.next()) {
                String question = questions.getString("question");
                String option1 = questions.getString("option_1");
                String option2 = questions.getString("option_2");
                String option3 = questions.getString("option_3");
                String option4 = questions.getString("option_4");
                String answer = questions.getString("answer");

                System.out.println("=========================================");
                System.out.println("Question: " + question);
                System.out.println("Options:");
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);
                System.out.print("Your answer: ");
                String userAnswer = sc.nextLine();

                if (userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect! The correct answer is: " + answer);
                }
                Main.sleep(2000);
            }

            System.out.println("=========================================");
            System.out.println("Quiz ended!");
            System.out.println("Your score: " + score + " out of 10");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet fetchQuestions(String q_id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s where %s = '%s';", Main.ques_table, "q_id", q_id);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return rs;
        }
    }
}