import java.util.Scanner;
import java.util.ArrayList;

public class Eric {
    private static final ArrayList<Task> tasks = new ArrayList<>();

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


    /** list tasks */
    public static void listTask() {
        linebreak();
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i+1) + ". " + tasks.get(i));
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
            if (index < 0 || index >= tasks.size()) {
                throw new EricException("Task number specified not in range of tasks available!");
            }
            if (inputs[0].equals("mark")) {
                tasks.get(index).markDone();
                linebreak();
                System.out.println("  Nice I've marked this task as done:");
                System.out.println("    " + tasks.get(index));
                linebreak();

            } else {
                tasks.get(index).markUndone();
                linebreak();
                System.out.println("  OK, I've marked this task as not done yet:");
                System.out.println("    " + tasks.get(index));
                linebreak();
            }

        } catch (IllegalArgumentException e) {
            throw new EricException("Enter a valid task number!");


        }
    }

    /** Specialised message printing for specific task types */
    public static void taskMsg(Task t) {
        linebreak();
        System.out.println(" Got it. I've added this task:");
        System.out.println("    " + t);
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        linebreak();
    }


    /** Adds new todo task to tasks array */
    public static Task addTodo(String userInput) throws EricException{
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("The todo's description cannot be empty!");
        }
        Task t = new Todo(descriptions[1]);
        tasks.add(t);
        return t;
    }

    /** Adds new deadline task to tasks array */
    public static Task addDeadline(String userInput) throws EricException {
        if (!userInput.contains("/by")) {
            throw new EricException("Missing /by after declaring a deadline task!");
        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /by");
        if (descriptions.length < 2 || descriptions[1].trim().isBlank()) {
            throw new EricException("Missing deadline after /by!");
        }
        Task t = new Deadline(descriptions[0], descriptions[1]);
        tasks.add(t);
        return t;
    }

    /** Adds new event task to tasks array */
    public static Task addEvent(String userInput) throws EricException{
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new EricException("Event must have /from and /to identifiers!");

        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /from", -1);
        if (descriptions[0].trim().isEmpty()) {
            throw new EricException("Event description is empty!");
        }
        String[] dateParts = descriptions[1].split(" /to", -1);
        if (dateParts.length < 2 || dateParts[0].trim().isBlank() || dateParts[1].trim().isBlank()) {
            throw new EricException("Event missing values after /from and /to!");
        }
        Task t = new Event(descriptions[0], dateParts[0].trim(), dateParts[1].trim());
        tasks.add(t);
        return t;
    }

    /** Command interface logic */
    public static void command(String userInput) throws EricException{
        if (userInput.startsWith("todo")){
            Task t = addTodo(userInput);
            taskMsg(t);
        } else if (userInput.startsWith("deadline")){
            Task t = addDeadline(userInput);
            taskMsg(t);
        } else if (userInput.startsWith("event")){
            Task t = addEvent(userInput);
            taskMsg(t);
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



