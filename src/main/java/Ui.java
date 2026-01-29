import java.util.Scanner;

/** Handles all user interaction */
public class Ui {
    private final Scanner scanner;

    /**
     * Initialises the Ui object with a Scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input.
     *
     * @return The line that was read.
     */
    public String readInput() {
        return scanner.nextLine();
    }

    public void linebreak() {
        String line = "----------------------------------------------------";
        System.out.println(line);
    }

    /**
     * Displays the greeting message.
     */
    public void greeting() {
        linebreak();
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        linebreak();
    }

    /**
     * Displays the goodbye message.
     */
    public void bye() {
        linebreak();
        System.out.println("  Bye. Hope to see you again soon!");
        linebreak();
    }

    /**
     * Shows the error message.
     *
     * @param message The error message to display.
     */
    public void errorMsg(String message) {
        System.out.println(message);
    }

    /**
     * Shows the message.
     *
     * @param message The message to display.
     */
    public void showMsg(String message) {
        System.out.println(message);
    }



}
