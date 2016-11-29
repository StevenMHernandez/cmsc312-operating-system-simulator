package Gui;

import java.util.ArrayList;
import java.util.stream.Collectors;

import Components.Clock;
import Components.CommandInterface;
import Components.InterruptProcessor;
import Components.OS;
import Components.Process;
import Components.Scheduler;
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
import javafx.stage.Stage;
import javafx.application.Application;


public class Gui extends Application {

    Stage window;
    BorderPane layout;
    HBox lowerBox;
    TextField textInput;
    TextArea textArea;
    Button button;
    TableView readyTable;
    TableView waitingTable;

    private final ObservableList<Process> readyProcessList = FXCollections.observableArrayList();
    private final ObservableList<Process> waitingProcessList = FXCollections.observableArrayList();
    protected static Scheduler scheduler;
    private InterruptProcessor interruptProcessor;
    private boolean exeContinuously = true;
    private int exeSteps = -1;

    OS os = new OS();

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Simulator");

        button = new Button();
        button.setText("test");
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

        TableColumn nameCol = new TableColumn("Process");
        nameCol.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        TableColumn arrivalCol = new TableColumn("Arrival Time");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<Process, String>("arrival"));
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<Process, String>("status"));

        TableColumn nameCol2 = new TableColumn("Process");
        nameCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        TableColumn arrivalCol2 = new TableColumn("Arrival Time");
        arrivalCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("arrival"));
        TableColumn statusCol2 = new TableColumn("Status");
        statusCol2.setCellValueFactory(new PropertyValueFactory<Process, String>("status"));

        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.setAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        readyTable = new TableView();
        waitingTable = new TableView();

        readyTable.setItems(this.readyProcessList);
        readyTable.getColumns().addAll(nameCol, arrivalCol, statusCol);
        waitingTable.setItems(this.waitingProcessList);
        waitingTable.getColumns().addAll(nameCol2, arrivalCol2, statusCol2);



        textInput = new TextField("temporarily out of order");
        textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    button.fire();
                }
            }
        });

        layout = new BorderPane();
        lowerBox = new HBox();
        lowerBox.setPadding(new Insets(10, 10, 10, 10));
        lowerBox.setSpacing(10);
        lowerBox.getChildren().addAll(textInput, button);
        layout.setBottom(lowerBox);
        layout.setRight(readyTable);
        layout.setLeft(waitingTable);

        Scene scene = new Scene(layout, 600, 600);
        window.setScene(scene);

        window.show();

        startup();
    }

    public void startup() throws InterruptedException {
        // TODO: remove these test process
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("CALCULATE");
        commands.add("2");
        commands.add("CALCULATE");
        commands.add("33");
        commands.add("OUT");
        commands.add("\"WORDS\"");
        commands.add("IO");
        commands.add("CALCULATE");
        commands.add("33");

        Scheduler.insertPCB(new Process("webbrowser", new ArrayList<>(commands), 128));
        Scheduler.insertPCB(new Process("textedit", new ArrayList<>(commands), 96));
        Scheduler.insertPCB(new Process("dj-touchbar", new ArrayList<>(commands), 24));
        Scheduler.insertPCB(new Process("GUI", new ArrayList<>(commands), 64));
        Scheduler.insertPCB(new Process("paint", new ArrayList<>(commands), 128));
        Scheduler.insertPCB(new Process("sound", new ArrayList<>(commands), 32));


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
        if (!exeContinuously && exeSteps == 0) {
            return;
        } else {
            exeSteps--;
        }

        os.execute();

        // Render GUI
        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.setAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));
    }
}