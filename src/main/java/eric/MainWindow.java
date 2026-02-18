package eric;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Eric eric;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image ericImage = new Image(this.getClass().getResourceAsStream("/images/DaBaby.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Eric chatbot instance into this controller.
     * Called from Main.java to provide the chatbot logic to the GUI.
     *
     * @param e The Eric chatbot instance.
     */
    public void setEric(Eric e) {
        eric = e;
        dialogContainer.getChildren().addAll(DialogBox.getEricDialog(eric.getGreeting(), ericImage));
    }

    /**
     * Creates two dialog boxes, for user input and Eric's reply.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            dialogContainer.getChildren().add(
                    DialogBox.getEricDialog("Please enter a command.", ericImage)
            );
            userInput.clear();
            return;
        }
        String response = eric.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getEricDialog(response, ericImage)
        );
        userInput.clear();

        // Close the window if exit command was executed
        if (eric.shouldExit()) {
            Platform.exit();
        }
    }
}
