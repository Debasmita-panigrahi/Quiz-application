import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {

    private static final int TIME_LIMIT = 10; 
    private static Scanner scanner = new Scanner(System.in);
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static boolean answered = false;

    private static String[][] questions = {
            {"What is the capital of France?", "1. Berlin", "2. Madrid", "3. Paris", "4. Rome", "3"},
            {"What is the largest planet in our solar system?", "1. Earth", "2. Jupiter", "3. Mars", "4. Venus", "2"},
            {"Who wrote 'To Kill a Mockingbird'?", "1. Harper Lee", "2. Mark Twain", "3. J.K. Rowling", "4. Ernest Hemingway", "1"}
    };

    public static void main(String[] args) {
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            answered = false;
            displayQuestion(currentQuestionIndex);

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int timeLeft = TIME_LIMIT;

                public void run() {
                    if (timeLeft > 0 && !answered) {
                        System.out.println("Time left: " + timeLeft + " seconds");
                        timeLeft--;
                    } else {
                        timer.cancel();
                        if (!answered) {
                            System.out.println("Time's up! Moving to the next question.");
                        }
                    }
                }
            };

            timer.scheduleAtFixedRate(task, 0, 1000);

            while (!answered && timer.purge() != 1) {
                
            }

            timer.cancel();
        }

        displayResults();
        scanner.close();
    }

    private static void displayQuestion(int index) {
        System.out.println("Question " + (index + 1) + ": " + questions[index][0]);
        for (int i = 1; i <= 4; i++) {
            System.out.println(questions[index][i]);
        }
        System.out.print("Enter your choice (1-4): ");

        new Thread(() -> {
            while (!answered) {
                if (scanner.hasNextInt()) {
                    int answer = scanner.nextInt();
                    if (answer >= 1 && answer <= 4) {
                        checkAnswer(answer, index);
                        answered = true;
                    } else {
                        System.out.print("Invalid choice. Enter your choice (1-4): ");
                    }
                }
            }
        }).start();
    }

    private static void checkAnswer(int answer, int index) {
        if (String.valueOf(answer).equals(questions[index][5])) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect! The correct answer was " + questions[index][Integer.parseInt(questions[index][5])] + ".");
        }
    }

    private static void displayResults() {
        System.out.println("\nQuiz Completed!");
        System.out.println("Your final score is: " + score + "/" + questions.length);
        System.out.println("Summary of your answers:");

        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i][0]);
            System.out.println("Your answer: " + (String.valueOf(score).equals(questions[i][5]) ? "Correct" : "Incorrect"));
        }
    }
}

    

