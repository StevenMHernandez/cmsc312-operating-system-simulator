package Gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;


public class Gui extends Application {

    Stage window;
    BorderPane layout;
    HBox lowerBox;
    TextField textInput;
    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Simulator");

        button = new Button();
        button.setText("test");
        button.setOnAction(e -> displayBox.display("displaybox", "hi does this work"));

        textInput = new TextField("temporarily out of order");
        textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println(textInput.getText());
                }
            }
        });

        layout = new Borderpane();
        lowerBox = new HBox();
        lowerbox.setPadding(new Insets(10, 10, 10, 10));
        lowerbox.setSpacing(10);
        lowerbox.getChildren().addAll(textInput, button);
        layout.setBottom(lowerBox);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();

    }
}