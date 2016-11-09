package Gui;

import java.util.ArrayList;
import java.util.stream.Collectors;

import Components.CPU;
import Components.Clock;
import Components.InterruptProcessor;
import Components.Process;
import Components.ProcessState;
import Components.Scheduler;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    TableView table;

    private final ObservableList<Process> processList = FXCollections.observableArrayList();
    private Scheduler scheduler;
    private InterruptProcessor interruptProcessor;
    private boolean exeContinuously = true;
    private int exeSteps = -1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Simulator");

        button = new Button();
        button.setText("test");
        button.setOnAction(e -> displayBox.display("displaybox", "hi does this work"));

        TableView<Process> processTable = new TableView<>();
        ObservableList<Process> processList = FXCollections.observableArrayList();
        processTable.setItems(processList);


        TableColumn nameCol = new TableColumn("Process");
        nameCol.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        TableColumn arrivalCol = new TableColumn("Arrival Time");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<Process, String>("arrival"));
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<Process, String>("status"));

        this.processList.setAll(Scheduler.getQueue().stream().collect(Collectors.toList()));

        table = new TableView();

        table.setItems(this.processList);
        table.getColumns().addAll(nameCol, arrivalCol, statusCol);


        textInput = new TextField("temporarily out of order");
        textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println(textInput.getText());
                }
            }
        });

        layout = new BorderPane();
        lowerBox = new HBox();
        lowerBox.setPadding(new Insets(10, 10, 10, 10));
        lowerBox.setSpacing(10);
        lowerBox.getChildren().addAll(textInput, button);
        layout.setBottom(lowerBox);
        layout.setRight(table);

        Scene scene = new Scene(layout, 600, 600);
        window.setScene(scene);

        window.show();

        startup();
    }

    public void startup() throws InterruptedException {
        scheduler = new Scheduler();
        interruptProcessor = new InterruptProcessor();

        // TODO: remove this test process
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("CALCULATE");
        commands.add("33");
        commands.add("CALCULATE");
        commands.add("33");
        commands.add("CALCULATE");
        commands.add("33");

        scheduler.insertPCB(new Process(commands));
        scheduler.insertPCB(new Process(commands));


        this.processList.setAll(Scheduler.getQueue().stream().collect(Collectors.toList()));


        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                try {
                    loop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

        if (null == scheduler.getCurrentPCB()) {
            scheduler.getNextPCB();
        }

        // there may not be any more processList from the scheduler
        if (null != scheduler.getCurrentPCB()) {
            Process currentProcess = scheduler.getCurrentPCB();

            scheduler.setState(currentProcess, ProcessState.RUN);

            if (currentProcess.getQueue().isEmpty()) {
                scheduler.setState(currentProcess, ProcessState.EXIT);

                interruptProcessor.signalInterrupt();
            } else {
                // read next line from our process's queue. (dequeue)
                String nextCommand = currentProcess.getQueue().remove(0);

                // check if the command has parameters we should read (remove from the queue)
                switch (nextCommand) {
                    case "CALCULATE":
                    case "OUT":
                        currentProcess.getQueue().remove(0);
                        break;
                    default:
                        break;
                }

                // check if the command needs us to do anything specific
                switch (nextCommand) {
                    case "IO":
                        scheduler.setState(currentProcess, ProcessState.WAIT);

                        // RequestRandomIO(currentProcess)

                        interruptProcessor.signalInterrupt();
                    default:
                        break;
                }

                // add time to Process CPU_Time
                scheduler.addCPUTime(1);

                if (scheduler.getCPUTime(currentProcess) > Scheduler.MAX_CPU_TIME) {
                    scheduler.setState(currentProcess, ProcessState.READY);

                    interruptProcessor.signalInterrupt();
                }
            }
        }

        CPU.advanceClock();
        Thread.sleep(1000);

        // GUI
        this.processList.setAll(Scheduler.getQueue().stream().collect(Collectors.toList()));
    }
}