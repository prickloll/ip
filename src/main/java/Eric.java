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
            }
            try {
                command(userInput);
            } catch (EricException e) {
                linebreak();
                System.out.println(e.getMessage());
                linebreak();
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

        String line = "----------------------------------------------------";
        System.out.println(line);
    }

    /** Marks and unmarks a task and prints a confirmation message */
    public static void setMarkUnmarked(String input) throws EricException{
        try {
            String[] inputs = input.split(" ");
            if (inputs.length < 2) {
                throw new EricException("Task number not specified!");
            }
            int index = Integer.parseInt(inputs[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new EricException("Task number specified not in range of tasks available!");
            }
            if (inputs[0].equals("mark")) {
                tasks[index].markDone();
                linebreak();
                System.out.println("  Nice I've marked this task as done:");
                System.out.println("    " + tasks[index].toString());
                linebreak();

            } else {
                tasks[index].markUndone();
                linebreak();
                System.out.println("  OK, I've marked this task as not done yet:");
                System.out.println("    " + tasks[index].toString());
                linebreak();
            }

        } catch (IllegalArgumentException e) {
            throw new EricException("Enter a valid task number!");


        }
    }

    /** Specialised message printing for specific task types */
    public static void taskMsg() {
        linebreak();
        System.out.println(" Got it. I've added this task:");
        System.out.println("    " + tasks[taskCount-1]);
        System.out.println("  Now you have " + (taskCount) + " tasks in the list.");
        linebreak();
    }


    /** Adds new todo task to tasks array */
    public static void addTodo(String userInput) throws EricException{
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("The todo's description cannot be empty!");
        }
        tasks[taskCount] = new Todo(descriptions[1]);
        taskCount++;
    }

    /** Adds new deadline task to tasks array */
    public static void addDeadline(String userInput) throws EricException {
        if (!userInput.contains("/by")) {
            throw new EricException("Missing /by after declaring a deadline task!");
        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /by");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Missing deadline after /by!");
        }
        tasks[taskCount] = new Deadline(descriptions[0], descriptions[1]);
        taskCount++;
    }

    /** Adds new event task to tasks array */
    public static void addEvent(String task_description, String from, String to) {
        tasks[taskCount] = new Event(task_description, from, to);
        taskCount++;
    }

    /** Command interface logic */
    public static void command(String userInput) throws EricException{
        if (userInput.startsWith("todo")){
            //String description = userInput.split(" ", 2)[1];
            addTodo(userInput);
            taskMsg();
        } else if (userInput.startsWith("deadline")){
            addDeadline(userInput);
            taskMsg();
        } else if (userInput.startsWith("event ")){
            int firstSpace = userInput.indexOf(" ");
            String[] descriptions = userInput.substring(firstSpace + 1).split(" /");
            addEvent(descriptions[0], descriptions[1], descriptions[2]);
            taskMsg();
        } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
            setMarkUnmarked(userInput);
        } else if (userInput.equals("list")) {
            listTask();
        }
        else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
    }

}



