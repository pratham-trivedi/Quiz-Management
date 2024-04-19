package manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Quiz extends person{
    private final Scanner sc;
    private final dbConnect db;
    private final String quiz_subject;

    public Quiz(String q_id,dbConnect db) {
        quiz_subject = q_id;
        this.db = db;
        this.sc = new Scanner(System.in);
    }

    public void start(String subject) {
        try {
            ResultSet questions = db.fetchQuestionsforQuiz(subject);
            int score = 0;
            int ques_number = 1;
            if(does_Element_exist(questions)) {
                while (questions.next()) {
                    if(ques_number == 11){break;}
                    while (true) {
                        String question = questions.getString(2);
                        String option1 = questions.getString(3);
                        String option2 = questions.getString(4);
                        String option3 = questions.getString(5);
                        String option4 = questions.getString(6);
                        String answer = questions.getString(7);

                        System.out.println("=========================================");
                        System.out.println("Question " + (ques_number++) + " : " + question);
                        System.out.println("Options:");
                        System.out.println("1. " + option1);
                        System.out.println("2. " + option2);
                        System.out.println("3. " + option3);
                        System.out.println("4. " + option4);
                        System.out.print("Your answer(a, b, c, d): ");
                        String userAnswer = sc.nextLine();

                        if (userAnswer.equalsIgnoreCase("a") || userAnswer.equalsIgnoreCase("b")
                                || userAnswer.equalsIgnoreCase("c") || userAnswer.equalsIgnoreCase("d")) {
                            answer = answer.trim();
                            if (userAnswer.equalsIgnoreCase(answer)) {
                                score++;
                            }
                            System.out.println("Response Submitted");
                            Main.sleep(2000);
                            break;
                        } else {
                            System.out.println("Invaid input");
                            ques_number--;
                            Main.sleep(2000);
                        }

                    }

                }

                System.out.println("=========================================");
                System.out.println("Quiz ended!");
                System.out.println("Your score: " + score + " out of 10");
                Main.pressEnter();
            }else{
                System.out.println("There are no question of these subjects, please consult an Admin");
                Main.pressEnter();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}