package nabmak;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private final Nabmak nabmak = new Nabmak();
    private final TextArea chatArea = new TextArea();
    
    @Override
    public void start(Stage stage) {
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.appendText("Yo im Nabmak.\nWhatchu wanna do?\n\n");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter a command...");

        Button sendButton = new Button("Send");

        Runnable sendCommand = () -> {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                return;
            }

            String response = nabmak.processCommand(input);
            chatArea.appendText("> " + input + "\n");
            chatArea.appendText(response + "\n\n");
            inputField.clear();

            if (input.equals("bye")) {
                stage.close();
            }
        };

        sendButton.setOnAction(event -> sendCommand.run());
        inputField.setOnAction(event -> sendCommand.run());

        HBox inputArea = new HBox(10, inputField, sendButton);
        inputArea.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(inputArea);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Nabmak");
        stage.setScene(scene);
        stage.show();
    }
}
