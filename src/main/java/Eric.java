import java.util.Scanner;

public class Eric {
    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {

        //Initial greeting logic
        linebreak();
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        linebreak();

        //For receiving inputs from the user
        Scanner scanner = new Scanner(System.in);


        //Main execution loop
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                linebreak();
                System.out.println("  Bye. Hope to see you again soon!");
                linebreak();
                break;
            }  else if (userInput.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
                setMarked(taskIndex);
            } else if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
                setUnmarked(taskIndex);
            } else if (userInput.equals("list")) {
                listTask();
            }
            else {
                addTask(userInput);
            }
        }


    }

    /** Adds new task to tasks array */
    public static void addTask(String task_description) {
        tasks[taskCount] = new Task(task_description);
        taskCount++;
        linebreak();
        System.out.println("  added: " + task_description);
        linebreak();

    }

    /** list tasks */
    public static void listTask() {
        linebreak();
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i+1) + ". " + tasks[i].toString());
        }
        linebreak();
    }

    /** Print line for design */
    public static void linebreak() {
        String line = "---------------------------------";
        System.out.println(line);
    }

    /** Marks a task as done and prints a confirmation message */
    public static void setMarked(int index) {
        tasks[index].markDone();
        linebreak();
        System.out.println("  Nice I've marked this task as done:");
        System.out.println("    " + tasks[index].toString() + "\n");
        linebreak();
    }

    /** Marks a task as undone and prints a confirmation message */
    public static void setUnmarked(int index) {
        tasks[index].markUndone();
        linebreak();
        System.out.println("  OK, I've marked this task as not done yet:");
        System.out.println("    " + tasks[index].toString() + "/n");
        linebreak();
    }

}



