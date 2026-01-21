import java.util.Scanner;

public class Eric {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        //For receiving inputs from the user
        Scanner scanner = new Scanner(System.in);

        //Main Execution: Exit code when user input equals "bye"
        while (true) {
            String user_input = scanner.nextLine();
            if (user_input.equals("bye")) {
                break;
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
