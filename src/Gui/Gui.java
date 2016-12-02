package Gui;

import java.util.stream.Collectors;

import Components.*;
import Components.Process;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;


public class Gui extends Application {

    private Stage window;
    private BorderPane layout;
    private HBox upperBox;
    private VBox lowerBox;
    private HBox controls;
    private TextField textInput;
    private Button button, testbutton;
    private TableView readyTable;
    private TableView waitingTable;
    private Label label;

    static protected TextArea textArea;

    private final ObservableList<Process> readyProcessList = FXCollections.observableArrayList();
    private final ObservableList<Process> waitingProcessList = FXCollections.observableArrayList();

    OS os = new OS();

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Simulator");

        button = new Button();
        button.setText("enter");
        button.setOnAction(e -> {
            boolean valid = false;
            if(!textInput.getText().trim().equals(""))
                valid = CommandInterface.temporaryName(textInput.getText());
            textInput.clear();
            if(!valid) {
                displayBox.display("Error", "Invalid Command!");
            }
        });

        TableView<Process> processTable = new TableView<>();
        ObservableList<Process> processList = FXCollections.observableArrayList();
        processTable.setItems(processList);

        // Ready Processes
        TableColumn nameCol = new TableColumn("Process");
        nameCol.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        TableColumn sizeCol = new TableColumn("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<Process, String>("size"));
        TableColumn arrivalCol = new TableColumn("Arrival Time");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<Process, String>("arrival"));
        TableColumn runTimeCol = new TableColumn("Run Time");
        runTimeCol.setCellValueFactory(new PropertyValueFactory<Process, String>("runTime"));
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<Process, String>("status"));
        TableColumn commandCol = new TableColumn("Last Command");
        commandCol.setCellValueFactory(new PropertyValueFactory<Process, String>("lastCommand"));
        commandCol.setPrefWidth(140);

        // Waiting Processes
        TableColumn nameCol2 = new TableColumn("Process");
        nameCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        TableColumn sizeCol2 = new TableColumn("Size");
        sizeCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("size"));
        TableColumn arrivalCol2 = new TableColumn("Arrival Time");
        arrivalCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("arrival"));
        TableColumn statusCol2 = new TableColumn("Status");
        statusCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("status"));

        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.setAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        readyTable = new TableView();
        waitingTable = new TableView();

        readyTable.setItems(this.readyProcessList);
        readyTable.getColumns().addAll(nameCol, sizeCol, arrivalCol, runTimeCol, statusCol, commandCol);
        waitingTable.setItems(this.waitingProcessList);
        waitingTable.getColumns().addAll(nameCol2, sizeCol2, arrivalCol2, statusCol2);

        textInput = new TextField();
        textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    button.fire();
                }
            }
        });

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setPrefRowCount(3);
        textArea.setPrefColumnCount(50);
        textArea.autosize();

        controls = new HBox();
        controls.setSpacing(10);
        controls.getChildren().addAll(textInput, button);

        layout = new BorderPane();

        VBox waitingBox = new VBox();
        waitingBox.setSpacing(10);
        Text waitingTitle = new Text("Waiting Queue");
        waitingTitle.setStyle("-fx-font-size: 18px");
        waitingBox.getChildren().addAll(waitingTitle, waitingTable);

        VBox readyBox = new VBox();
        readyBox.setSpacing(10);
        Text readyTitle = new Text("Ready Queue");
        readyTitle.setStyle("-fx-font-size: 18px");
        readyBox.getChildren().addAll(readyTitle, readyTable);

        upperBox = new HBox();
        upperBox.setSpacing(10);
        upperBox.setPadding(new Insets(10, 10, 10, 10));
        upperBox.getChildren().addAll(waitingBox, readyBox);

        lowerBox = new VBox();
        lowerBox.setSpacing(10);
        lowerBox.setPadding(new Insets(10, 10, 10, 10));
        lowerBox.getChildren().addAll(textArea, controls);

        layout.setTop(upperBox);
        layout.setBottom(lowerBox);

        Scene scene = new Scene(layout, 900, 600);
        window.setScene(scene);

        window.show();

        startup();
    }

    public void startup() throws InterruptedException {
        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.setAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        final long[] prevTime = {0};

        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                if (currentNanoTime > prevTime[0] + 90000000) {
                    try {
                        loop();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    prevTime[0] = currentNanoTime + 90000000;
                }
            }
        }.start();
    }

    public void loop() throws InterruptedException {
        // Render GUI
        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.setAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        // Run Simulator
        if (!Simulator.exeContinuously && Simulator.exeSteps == 0) {
            return;
        } else {
            Simulator.exeSteps--;
        }

        os.execute();
    }
}