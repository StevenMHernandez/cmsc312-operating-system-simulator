import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;


public class Gui extends Application {

    Stage window;
    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Simulator");
        button = new Button();
        button.setText("test");
        button.setOnAction(e -> displayBox.display("displaybox", "hi does this work"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.show();

    }
}