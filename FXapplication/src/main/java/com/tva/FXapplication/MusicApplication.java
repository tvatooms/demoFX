package com.tva.FXapplication;

import javafx.scene.control.ProgressIndicator;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.image.Image;
import javafx.scene.shape.*;
import java.io.File;
import javafx.concurrent.*;

import org.springframework.aop.interceptor.ConcurrencyThrottleInterceptor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MusicApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private final PartitionClientController controller;
    private GridPane mainPane;
    private StackPane overlayPane;

    public MusicApplication() {
        super();
        this.controller = new PartitionClientController("http://localhost:8080");

    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
        this.controller.ping();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

    private GridPane mainPane(Pane parent) {
        GridPane pane = new GridPane();
        ColumnConstraints colThird = new ColumnConstraints();
        colThird.setPercentWidth(100.0 / 3.0);
        RowConstraints rowThird = new RowConstraints();
        rowThird.setPercentHeight(100.0 / 3.0);
        pane.getColumnConstraints().addAll(colThird, colThird, colThird);
        pane.getRowConstraints().addAll(rowThird, rowThird, rowThird);
        // pane.setGridLinesVisible(true);
        String url = getClass().getResource("/add.png").toString();
        ImageView imageView = new ImageView(url);
        // imageView.getStyleClass().add("dropped");
        pane.getChildren().add(imageView);
        pane.setConstraints(imageView, 1, 1);
        imageView.fitWidthProperty().bind(pane.widthProperty().multiply(0.5));
        imageView.fitHeightProperty().bind(pane.heightProperty().multiply(0.5));
        imageView.setPreserveRatio(true);
        pane.minWidthProperty().bind(parent.widthProperty());
        pane.minHeightProperty().bind(parent.heightProperty());
        /******************** */
        pane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();

            boolean success = false;
            if (db.hasFiles()) {
                final File file = db.getFiles().get(0);
                controller.sendFile(file.getAbsolutePath());
                try {

                    processPartition();
                    System.out.println("WAIT");
                    /*
                     * var a=new PlayWait(); //
                     * a.onRunningProperty(()->System.out.println("cvoucou")) ; var thread=new
                     * Thread(a); progress.progressProperty().bind(a.progressProperty());
                     * thread.start(); Thread.sleep(5000); //future.join(); a.doStop();
                     * thread.join();
                     */
                } catch (Exception e) {
                    System.out.println(("BUG" + e.getMessage()));
                }
                success = true;
            }

            event.setDropCompleted(success);

            event.consume();

        });
        pane.setOnDragExited((DragEvent event) -> {
            imageView.getStyleClass().clear();
            System.out.println("TEST");
            event.consume();
        });
        pane.setOnDragOver((DragEvent event) -> {

            imageView.getStyleClass().add("dropped");
            // System.out.println("ICI "+event);
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        /************************/
        return pane;
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        this.mainPane = mainPane(root);
        this.overlayPane = new OverlayWaitPane();
        root.getChildren().addAll(overlayPane,mainPane);
        toggleOverlay(false);

        Scene scene = new Scene(root, 800, 600, true);
      /*  scene.setOnKeyPressed((KeyEvent event) -> {
            System.out.println("Key Pressed");
            clickedButton();
        });*/

        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partition simplifier");

        primaryStage.show();       

    }

    private void toggleOverlay(boolean show) {
      
        this.overlayPane.setVisible(show);
        this.mainPane.setOpacity(show ? 0.2:1.0);
        this.mainPane.setDisable(show);

    }

    private void processPartition() {

        toggleOverlay(true);
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                // Charger l'image ici.
                try {
                    Thread.sleep(15000);
                } catch (Exception e) {
                    System.out.println(("LEBUG" + e.getMessage()));
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("succeeded - sent from the succeeded method of the Task");
            }

        };
        // a.onRunningProperty(()->System.out.println("cvoucou")) ;
        // var thread = new Thread(a);
        var threadB = new Thread(task);
        // progress.progressProperty().bind(a.progressProperty());
        // thread.setDaemon(true);
        threadB.setDaemon(true);
        // thread.start();

        try {
            threadB.start();
            // threadB.join();
            System.out.println("END");
            // a.cancel();
            System.out.println("BB");
            // thread.join();

        } catch (Exception e) {
            System.out.println(("BUG" + e.getMessage()));
        }
        task.setOnSucceeded(e -> {
            toggleOverlay(false);
            // pane.getChildren().remove(progress);
            // progress = null;

        });
    }

}
