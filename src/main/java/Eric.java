import java.util.Scanner;

public class Eric {
    private static final String[] tasks = new String[100];
    private static int task_count = 0;

    public static void main(String[] args) {

        //Initial greeting logic
        linebreak();
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        linebreak();

        //For receiving inputs from the user
        Scanner scanner = new Scanner(System.in);


        //Main execution loop
        while (true) {
            String user_input = scanner.nextLine();
            if (user_input.equals("bye")) {
                linebreak();
                System.out.println("  Bye. Hope to see you again soon!");
                linebreak();
                break;
            }  else if (user_input.equals("list")) {
                listTask();
            }
            else {
                addTask(user_input);
            }
        }


    }

    /** Adds new task to tasks array */
    public static void addTask(String task) {
        tasks[task_count] = task;
        task_count++;
        linebreak();
        System.out.println("  added: " + task);
        linebreak();

    }

    /** list tasks  */
    public static void listTask() {
        linebreak();
        for (int i = 0; i < task_count; i++) {
            System.out.println("  " + (i+1) + ". " + tasks[i]);
        }
        linebreak();
    }

    /** Print line for design */
    public static void linebreak() {
        String line = "---------------------------------";
        System.out.println(line);
    }

}
