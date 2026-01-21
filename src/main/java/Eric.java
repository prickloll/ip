import java.util.Scanner;

public class Eric {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        //For receiving inputs from the user
        Scanner scanner = new Scanner(System.in);

        //Main Execution: Echo the user input or exit
        while (true) {
            String user_input = scanner.nextLine();
            if (user_input.equals("bye")) {
                System.out.println();
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println();
                break;
            } else {
                System.out.println();
                System.out.println(" " + user_input);
                System.out.println();
            }
        }


    }
}
