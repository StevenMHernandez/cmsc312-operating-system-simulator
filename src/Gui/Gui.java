package Gui;

import java.util.ArrayList;
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

        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.addAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        readyTable = new TableView();
        waitingTable = new TableView();

        readyTable.setItems(this.readyProcessList);
        readyTable.getColumns().addAll(nameCol, arrivalCol, statusCol);
        waitingTable.setItems(this.waitingProcessList);
        waitingTable.getColumns().addAll(nameCol, arrivalCol, statusCol);



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
        scheduler = new Scheduler();
        interruptProcessor = new InterruptProcessor();

        // TODO: remove this test process
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("CALCULATE");
        commands.add("33");
        commands.add("CALCULATE");
        commands.add("33");
        commands.add("IO");
        commands.add("CALCULATE");
        commands.add("33");

        /*
        scheduler.insertPCB(new Process(commands, 128));
        scheduler.insertPCB(new Process(commands, 96));
        scheduler.insertPCB(new Process(commands, 24));
        scheduler.insertPCB(new Process(commands, 64));
        scheduler.insertPCB(new Process(commands, 128));
        scheduler.insertPCB(new Process(commands, 32));
        */



        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.addAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

        final long[] prevTime = {0};


        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                if (currentNanoTime > prevTime[0] + 1000000000) {
                    System.out.println(currentNanoTime);
                    System.out.println(prevTime[0]);

                    try {
                        loop();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    prevTime[0] = currentNanoTime + 1000000000;
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

        scheduler.execute();

//        // check for events
//        if (cpu.detectPreemption()) {
//            Event event = EventQueue.deQueue();
//
//            switch(event.type) {
//                case "IO":
//                    cpu.setCurrentPCB(event.process);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        if (null == cpu.getCurrentPCB()) {
//            cpu.setCurrentPCB(scheduler.getNextPCB());
//        }



//        // there may not be any more readyProcessList from the scheduler
//        if (null != cpu.getCurrentPCB()) {
//            Process currentProcess = cpu.getCurrentPCB();
//
//            cpu.setState(scheduler, ProcessState.RUN);
//
//            if (currentProcess.getQueue().isEmpty()) {
//                cpu.setState(scheduler, ProcessState.EXIT);
//
//                interruptProcessor.signalInterrupt();
//            } else {
//                // read next line from our process's queue. (dequeue)
//                String nextCommand = currentProcess.getQueue().remove(0);
//
//                // check if the command has parameters we should read (remove from the queue)
//                switch (nextCommand) {
//                    case "CALCULATE":
//                    case "OUT":
//                        currentProcess.getQueue().remove(0);
//                        break;
//                    default:
//                        break;
//                }
//
//                // check if the command needs us to do anything specific
//                switch (nextCommand) {
//                    case "IO":
//                        cpu.setState(scheduler, ProcessState.WAIT);
//
//                        IOScheduler.scheduleIO(currentProcess);
//
//                        interruptProcessor.signalInterrupt();
//                    default:
//                        break;
//                }
//
//                // add time to Process CPU_Time
//                cpu.addCPUTime(1);
//
//                if (cpu.getCPUTime(currentProcess) > cpu.MAX_CPU_TIME) {
//                    cpu.setState(scheduler, ProcessState.READY);
//
//                    interruptProcessor.signalInterrupt();
//                }
//            }
//        }

        // GUI
        this.readyProcessList.setAll(Scheduler.getReadyQueue().stream().collect(Collectors.toList()));
        this.waitingProcessList.addAll(Scheduler.getWaitingQueue().stream().collect(Collectors.toList()));

    }
}