import java.util.Scanner;

public class Eric {
    private static String[] tasks = new String[100];
    private static int task_count = 0;
    private static String line = "---------------------------------";

    public static void main(String[] args) {
        System.out.println(line);
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        System.out.println(line);

        //For receiving inputs from the user
        Scanner scanner = new Scanner(System.in);


        //Main Execution loop
        while (true) {
            String user_input = scanner.nextLine();
            if (user_input.equals("bye")) {
                System.out.println(line);
                System.out.println("  Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else {
                System.out.println(line);
                addTask(user_input);
                System.out.println(line);
            }
        }


    }

    /** Adds new task to tasks array */
    public static void addTask(String task) {
        tasks[task_count] = task;
        task_count++;
        System.out.println("  added: " + task);

    }
}
